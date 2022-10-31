package com.sgillet.nba.fantasyteam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.csv.CSVRecord;

@Entity
@Table(name = "PLAYER")
public class Player {
	
	@Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
	private Long id;

	@Column(name = "Name", length = 64, nullable = false)
	private String name;
	
	@Column(name = "Team", length = 64, nullable = false)
	private String team;
	
	@Column(name = "NbMatches", nullable = false)
	private Long nbMatches;
	
	@Column(name = "Minutes", nullable = false)
	private Double minutes;
	
	@Column(name = "Rebounds", nullable = false)
	private Double rebounds;
	
	@Column(name = "Assists", nullable = false)
	private Double assists;
	
	@Column(name = "Steals", nullable = false)
	private Double steals;
	
	@Column(name = "Blocks", nullable = false)
	private Double blocks;
	
	@Column(name = "Turnovers", nullable = false)
	private Double turnovers;
	
	@Column(name = "Points", nullable = false)
	private Double points;
	
	@Column(name = "Pos", length = 5, nullable = false)
	private String pos;
	
	@Column(name = "ScoreTotal", nullable = false)
	private Double scoreTotal;
	
	@Column(name = "Price", nullable = false)
	private Double price;
	
	@Column(name = "Value", nullable = false)
	private Double value;
	
	@Column(name = "ValueWeek", nullable = false)
	private Double valueWeek;
	
	@Column(name = "ValuePrice", nullable = false)
	private Double valuePrice;
	
	@Column(name = "NbMatchesWeek", nullable = false)
	private Long nbMatchesWeek;
	
	@Column(name = "Exlcude", nullable = false)
	private Boolean exlcude;
	
	public Player() {}
	
	public Player(CSVRecord csvRecord) {
		this.name = csvRecord.get(0);
		this.team = csvRecord.get(1); 
		this.nbMatches = Long.parseLong(csvRecord.get(2)); 
		this.minutes = Double.parseDouble(csvRecord.get(3).replace(",", ".")); 
		this.rebounds = Double.parseDouble(csvRecord.get(4).replace(",", ".")); 
		this.assists = Double.parseDouble(csvRecord.get(5).replace(",", "."));  
		this.steals = Double.parseDouble(csvRecord.get(6).replace(",", "."));  
		this.blocks = Double.parseDouble(csvRecord.get(7).replace(",", "."));  
		this.turnovers = Double.parseDouble(csvRecord.get(8).replace(",", "."));
		this.points = Double.parseDouble(csvRecord.get(9).replace(",", "."));
		this.pos = csvRecord.get(10); 
		this.scoreTotal = Double.parseDouble(csvRecord.get(11).replace(",", "."));
		this.price = Double.parseDouble(csvRecord.get(12).replace(",", ".")); 
		this.value = Double.parseDouble(csvRecord.get(13).replace(",", ".")); 
		this.valueWeek = Double.parseDouble(csvRecord.get(14).replace(",", ".")); 
		this.valuePrice = Double.parseDouble(csvRecord.get(15).replace(",", ".")); 
		this.nbMatchesWeek = Long.parseLong(csvRecord.get(16));
		this.exlcude = Integer.parseInt(csvRecord.get(17)) == 1;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public Long getNbMatches() {
		return nbMatches;
	}

	public void setNbMatches(Long nbMatches) {
		this.nbMatches = nbMatches;
	}

	public Double getMinutes() {
		return minutes;
	}

	public void setMinutes(Double minutes) {
		this.minutes = minutes;
	}

	public Double getRebounds() {
		return rebounds;
	}

	public void setRebounds(Double rebounds) {
		this.rebounds = rebounds;
	}

	public Double getAssists() {
		return assists;
	}

	public void setAssists(Double assists) {
		this.assists = assists;
	}

	public Double getSteals() {
		return steals;
	}

	public void setSteals(Double steals) {
		this.steals = steals;
	}

	public Double getBlocks() {
		return blocks;
	}

	public void setBlocks(Double blocks) {
		this.blocks = blocks;
	}

	public Double getTurnovers() {
		return turnovers;
	}

	public void setTurnovers(Double turnovers) {
		this.turnovers = turnovers;
	}

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public Double getScoreTotal() {
		return scoreTotal;
	}

	public void setScoreTotal(Double scoreTotal) {
		this.scoreTotal = scoreTotal;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getValueWeek() {
		return valueWeek;
	}

	public void setValueWeek(Double valueWeek) {
		this.valueWeek = valueWeek;
	}

	public Double getValuePrice() {
		return valuePrice;
	}

	public void setValuePrice(Double valuePrice) {
		this.valuePrice = valuePrice;
	}

	public Long getNbMatchesWeek() {
		return nbMatchesWeek;
	}

	public void setNbMatchesWeek(Long nbMatchesWeek) {
		this.nbMatchesWeek = nbMatchesWeek;
	}

	public Boolean getExlcude() {
		return exlcude;
	}

	public void setExlcude(Boolean exlcude) {
		this.exlcude = exlcude;
	}

}
