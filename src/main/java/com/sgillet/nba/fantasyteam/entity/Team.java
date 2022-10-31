package com.sgillet.nba.fantasyteam.entity;

import java.util.StringJoiner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.sgillet.nba.fantasyteam.exception.FantasyTeamException;


@Entity
@Table(name = "TEAM", indexes = {
		@Index(name = "Idx_Value", columnList = "Value DESC")	
})
public class Team {
	
	private static final String DELIMITER_CSV = ";";
	
	@Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
	private Long id;

	@Column(name = "Player1", length = 64, nullable = false)
	private String player1;
	
	@Column(name = "Player2", length = 64, nullable = false)
	private String player2;
	
	@Column(name = "Player3", length = 64, nullable = false)
	private String player3;
	
	@Column(name = "Player4", length = 64, nullable = false)
	private String player4;
	
	@Column(name = "Player5", length = 64, nullable = false)
	private String player5;
	
	@Column(name = "Player6", length = 64, nullable = false)
	private String player6;
	
	@Column(name = "Player7", length = 64, nullable = false)
	private String player7;
	
	@Column(name = "Price", nullable = false)
	private Double price = 0.0;
	
	@Column(name = "Value", nullable = false)
	private Double value = 0.0;
	
	
	public void addNextPlayer(Player player) {
		
		if (StringUtils.isBlank(player1)) {
			this.player1 = player.getName();
		} else if (StringUtils.isBlank(player2)) {
			this.player2 = player.getName();
		} else if (StringUtils.isBlank(player3)) {
			this.player3 = player.getName();
		} else if (StringUtils.isBlank(player4)) {
			this.player4 = player.getName();
		} else if (StringUtils.isBlank(player5)) {
			this.player5 = player.getName();
		} else if (StringUtils.isBlank(player6)) {
			this.player6 = player.getName();
		} else if (StringUtils.isBlank(player7)) {
			this.player7 = player.getName();
		} else {
			throw new FantasyTeamException("The team is already complete: cannot add a player");
		}
		
		this.price += player.getPrice();
		this.value += player.getValueWeek();
		
	}
	
	public String getCSVFormat() {
		StringJoiner csvFormat = new StringJoiner(DELIMITER_CSV);
		csvFormat.add(this.player1).add(this.player2).add(this.player3).add(this.player4)
			.add(this.player5).add(this.player6).add(this.player7);
		csvFormat.add(String.format("%.2f", this.price));
		csvFormat.add(String.format("%.2f", this.value));
		return csvFormat.toString();
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlayer1() {
		return player1;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}

	public String getPlayer3() {
		return player3;
	}

	public void setPlayer3(String player3) {
		this.player3 = player3;
	}

	public String getPlayer4() {
		return player4;
	}

	public void setPlayer4(String player4) {
		this.player4 = player4;
	}

	public String getPlayer5() {
		return player5;
	}

	public void setPlayer5(String player5) {
		this.player5 = player5;
	}

	public String getPlayer6() {
		return player6;
	}

	public void setPlayer6(String player6) {
		this.player6 = player6;
	}

	public String getPlayer7() {
		return player7;
	}

	public void setPlayer7(String player7) {
		this.player7 = player7;
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

}
