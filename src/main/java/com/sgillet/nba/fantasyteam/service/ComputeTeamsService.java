package com.sgillet.nba.fantasyteam.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgillet.nba.fantasyteam.dao.PlayerDAO;
import com.sgillet.nba.fantasyteam.dao.TeamDAO;
import com.sgillet.nba.fantasyteam.entity.Player;
import com.sgillet.nba.fantasyteam.entity.Team;

@Service
public class ComputeTeamsService {

	private static final Logger logger = LoggerFactory.getLogger(ComputeTeamsService.class);
	
	private static final int NB_PLAYERS_TEAM = 7;
	private static final int NB_MIN_POS_TEAM = 5;
	
	private List<Player> playersForComputeTeams;

	
	@Autowired
	TeamDAO teamDao;
	
	@Autowired
	PlayerDAO playerDao;
	
	
	/**
	 * Compute teams and save in database
	 * 
	 * @param includePlayers players to include int the team
	 * @param limitPrice max team price
	 * 
	 */
	public void computeTeams(List<String> includePlayers, Double limitPrice) {
		
		if (logger.isDebugEnabled())
			logger.debug(String.format("computeTeams(%s)", String.join("|", includePlayers)));
		
		// Get all players to compute teams
		playersForComputeTeams =  playerDao.findByExlcude(false);
		if (logger.isInfoEnabled())
			logger.info(String.format("%s players used to computed teams", playersForComputeTeams.size()));
		
		// Delete olds computed teams
		teamDao.deleteAll();
		
		// Add include players
		List<Player> playersToInclude = new ArrayList<>();
		for (String includePlayer : includePlayers) {
			Optional<Player> player = playerDao.findByName(includePlayer);
			if (player.isPresent()) {
				playersToInclude.add(player.get());
				playersForComputeTeams.removeIf(p -> p.getName().equals(includePlayer));
			} else if (logger.isWarnEnabled()) {
				logger.warn(String.format("Player(s) [%s] doesn't exist", includePlayer)); 
			}
		}
		
		// Compute teams
		List<CompletableFuture<Void>> computeTeamsAsync = new ArrayList<>();
		for (int indexPlayer = 0; indexPlayer < playersForComputeTeams.size(); indexPlayer++) {
			List<Player> playersTemp = new ArrayList<>();
			playersToInclude.forEach(playersTemp::add);
			playersTemp.add(playersForComputeTeams.get(indexPlayer));
			computeTeamsAsync.add(processTeamAsync(indexPlayer + 1, playersTemp, limitPrice));
		}
		CompletableFuture.allOf(computeTeamsAsync.toArray(new CompletableFuture[computeTeamsAsync.size()])).join();
		
		if (logger.isInfoEnabled())
			logger.info(String.format("%d teams computed with a budget of %.2f million", teamDao.count(), limitPrice));
		
	}
	
	
	/**
	 * Process compute team in async (recusive)
	 * 
	 * @param index index start to players
	 * @param playersTeam players include in team
	 * @param limitPrice max team price
	 * @return CompletableFuture
	 */
	private CompletableFuture<Void> processTeamAsync(int index, List<Player> playersTeam, Double limitPrice) {
		
		return CompletableFuture.runAsync(() -> {
			if (logger.isInfoEnabled())
				logger.info(String.format("  --> Compute teams with first players %s [%s]", 
						index, playersTeam.stream().map(Player::getName).collect(Collectors.joining("|"))))
				;
			processTeam(index, playersTeam, limitPrice);
		});
		
	}
	
	
	/**
	 * Process compute team (recusive)
	 * 
	 * @param index index start to players
	 * @param playersTeam players include in team
	 * @param limitPrice max team price
	 */
	private void processTeam(int index, List<Player> playersTeam, Double limitPrice) {
			
		for (int indexPlayer = index; indexPlayer < playersForComputeTeams.size(); indexPlayer++) {
			
			if (playersTeam.size() == NB_PLAYERS_TEAM - 1) {
				
				// Team complete : add in BDD if 
				//  - price < limit price
				//  - the computed team has at least one player per position
				Player currentPlayer = playersForComputeTeams.get(indexPlayer);
				playersTeam.add(currentPlayer);
				int nbPos = playersTeam.stream()
					.map(Player::getPos)
					.distinct()
					.mapToInt(p -> 1)
					.sum()
				;
				if (nbPos >= NB_MIN_POS_TEAM) {
					Team teamCompute = new Team();
					playersTeam.stream().forEach(teamCompute::addNextPlayer);
					if (teamCompute.getPrice() <= limitPrice)
						teamDao.save(teamCompute);	
				}
				
				// Remove the current player
				playersTeam.remove(currentPlayer);
				
			} else {
				
				// Search next player for the team
				playersTeam.add(playersForComputeTeams.get(indexPlayer));
				processTeam(indexPlayer + 1, playersTeam, limitPrice);
				
			}
		}
		
		// Remove the last player
		if (!playersTeam.isEmpty())
			playersTeam.remove(playersTeam.size() - 1);
		
	}

}
