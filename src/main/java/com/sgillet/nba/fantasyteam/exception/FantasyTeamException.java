package com.sgillet.nba.fantasyteam.exception;

public class FantasyTeamException extends RuntimeException {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -3607065954448715305L;
	
	/**
	 * Instantiates a new FantasyTeam exception.
	 *
	 * @param message the message
	 */
	public FantasyTeamException(String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new FantasyTeam exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public FantasyTeamException(String message, Throwable cause) {
		super(message, cause);
	}

}
