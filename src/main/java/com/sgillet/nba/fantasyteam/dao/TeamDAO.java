package com.sgillet.nba.fantasyteam.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sgillet.nba.fantasyteam.entity.Team;

@Repository
public interface TeamDAO extends CrudRepository<Team, Long> {
	
	/*
	 * Get syntax method : 
	 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
	 */
	
	public List<Team> findByPriceLessThanEqual(Double price);
	
	public List<Team> findAll();
	
	public List<Team> findAllByOrderByValueDesc();
	
	@Query(value="select * from Team order by value desc LIMIT :maxTeams", nativeQuery = true)
	public List<Team> findBestTeams(@Param("maxTeams") Long maxTeams);
	
	@Query(value="select * from Team where price < :maxPrice order by value desc LIMIT :maxTeams", nativeQuery = true)
	public List<Team> findBestTeams(@Param("maxPrice") Double maxprice, @Param("maxTeams") Long maxTeams);

}
