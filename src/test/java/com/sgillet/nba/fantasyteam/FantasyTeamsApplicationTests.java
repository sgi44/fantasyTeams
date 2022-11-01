package com.sgillet.nba.fantasyteam;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sgillet.nba.fantasyteam.dao.TeamDAO;
import com.sgillet.nba.fantasyteam.entity.Team;
import com.sgillet.nba.fantasyteam.service.ComputeTeamsService;
import com.sgillet.nba.fantasyteam.service.PlayerService;

@SpringBootTest
class FantasyTeamsApplicationTests {
	
	private static final char DELIMITER_CSV = ';';
	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	ComputeTeamsService computeTeamService;
	
	@Autowired
	TeamDAO teamDao;
	

	@Test
	void testLight() {
		
		List<String> players = Arrays.asList("Nikola Jokic", "Kawhi Leonard", "Tyler Herro");
		URL testFile = FantasyTeamsApplicationTests.class.getResource("/StatPlayersLight.csv");
		List<Team> test = process(testFile.getPath().replaceFirst("/", ""), players, 100D, 250L);
		Assertions.assertEquals(250, test.size());
		Assertions.assertEquals(919.4D, test.get(0).getValue());
		Assertions.assertTrue(test.get(0).getPrice() < 100D);
		
	}
	
	private List<Team> process(String absPathCsv, List<String> playersInclude, Double limitPrice, Long maxResults) {
		
		// Load players with CSV file
		playerService.loadPlayersFromCSVFile(absPathCsv, DELIMITER_CSV);
				
		// Compute teams
		computeTeamService.computeTeams(playersInclude, limitPrice);
		return teamDao.findBestTeams(maxResults);
		
	}

}
