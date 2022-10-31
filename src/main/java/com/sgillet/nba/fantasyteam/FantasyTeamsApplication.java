package com.sgillet.nba.fantasyteam;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sgillet.nba.fantasyteam.dao.TeamDAO;
import com.sgillet.nba.fantasyteam.entity.Team;
import com.sgillet.nba.fantasyteam.exception.FantasyTeamException;
import com.sgillet.nba.fantasyteam.service.ComputeTeamsService;
import com.sgillet.nba.fantasyteam.service.PlayerService;

@SpringBootApplication
public class FantasyTeamsApplication implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(FantasyTeamsApplication.class);
	private static final char DELIMITER_CSV = ';';
	private static final String CSV_HEADERS = "Player1;Player2;Player3;Player4;Player5;Player6;Player7;Price;Value";

	@Autowired
	PlayerService playerService;
	
	@Autowired
	ComputeTeamsService computeTeamService;
	
	@Autowired
	TeamDAO teamDao;
	
	@Value("${path.csv.players}")
    private String absPathCsv;
	
	@Value("${path.csv.results}")
    private String absPathCsvResult;
	
	@Value("${results.csv.max}")
    private Long maxResults;
	
	@Value("${team.limit.price}")
    private Double limitPrice;
	
	private boolean request = false;
	
	List<String> playersInclude = new ArrayList<>();
	
	
	public static void main(String[] args) {
		SpringApplication.run(FantasyTeamsApplication.class, args);
	}
	

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		long startTime = System.nanoTime();
		long timeExecutionSeconds;
		
		try {

			if (logger.isInfoEnabled())
				logger.info("=========================================================================================");

			// Get params
			args.getOptionNames().forEach(optionName -> {
				switch(optionName) {
					case "request" : 
						request = true;
						break;
					case "pathCsvPlayers" : 
						absPathCsv = args.getOptionValues(optionName).get(0);
						break;
					case "pathCsvResults" : 
						absPathCsvResult = args.getOptionValues(optionName).get(0);
						break;
					case "maxResultsCsv" : 
						maxResults = Long.parseLong(args.getOptionValues(optionName).get(0));
						break;
					case "teamLimitPrice" : 
						limitPrice = Double.parseDouble(args.getOptionValues(optionName).get(0));
						break;
					case "includePalyers" : 
						playersInclude = new ArrayList<>(Arrays.asList(args.getOptionValues(optionName).get(0).split(",")));
						break;
					default : 
						if (logger.isDebugEnabled())
							logger.debug("No arguments specified");
				}
	        });

			if (logger.isInfoEnabled())
				logger.info(String.format(
						"Start compute fantasy team with configuration [limitPrice = %.2f, maxResults = %d, CSV file = %s, CSV result = %s, players = %s]",
						limitPrice, maxResults, absPathCsv, absPathCsvResult, String.join("|", playersInclude)))
				;
			
			// Get teams
			List<Team> teams;
			if (request) {
				teams = requestMode();
			} else {
				teams = computeMode();
			}
			
			// Save results
			File csvOutputFile = new File(absPathCsvResult);
		    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
		    	pw.print(CSV_HEADERS);
		    	pw.println();
		    	teams.stream()
		    		.map(Team::getCSVFormat)
		    		.forEach(pw::println);
		    }
		    
		} catch (FantasyTeamException ex) {
			logger.error(String.format("Error on program : %s", ex.getMessage()));
		} finally {
			long endTime = System.nanoTime();
		    timeExecutionSeconds = (endTime - startTime) / 1000000000;
		}
	    			
		if (logger.isInfoEnabled())
			logger.info(String.format("End compute fantasy team [%d secondes], result in [%s]", timeExecutionSeconds, absPathCsvResult));
		
	}
	
	
	/**
	 * Compute team possibilities from the players provided
	 * 
	 * @return computed teams
	 */
	private List<Team> computeMode() {
		
		// Test existing file players
		if (Files.notExists(Paths.get(absPathCsv))) {
			throw new FantasyTeamException(String.format("The players file [%s] doesn't exist : can't compute teams", absPathCsv));
		}
		
		// Load players with CSV file
		playerService.loadPlayersFromCSVFile(absPathCsv, DELIMITER_CSV);
		
		// Compute teams
		computeTeamService.computeTeams(playersInclude, limitPrice);
		return teamDao.findBestTeams(maxResults);

	}
	
	
	/**
	 * Queries on already calculated teams
	 * 
	 * @return teams returned by the request
	 */
	private List<Team> requestMode() {
		
		return teamDao.findBestTeams(limitPrice, maxResults);
		
	}

}
