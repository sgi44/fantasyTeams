package com.sgillet.nba.fantasyteam.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sgillet.nba.fantasyteam.entity.Player;

@Repository
public interface PlayerDAO extends CrudRepository<Player, Long> {
	
	/*
	 * Get syntax method : 
	 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
	 */
	
	public List<Player> findAll();
	
	public Optional<Player> findByName(String name);
	
	public List<Player> findByExlcude(Boolean exclude);
	
}
