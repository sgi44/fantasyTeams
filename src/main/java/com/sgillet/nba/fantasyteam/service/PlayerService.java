package com.sgillet.nba.fantasyteam.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgillet.nba.fantasyteam.dao.PlayerDAO;
import com.sgillet.nba.fantasyteam.entity.Player;
import com.sgillet.nba.fantasyteam.exception.FantasyTeamException;

@Service
public class PlayerService {

	private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);
	
	@Autowired
	PlayerDAO playerDao;
	

	/**
	 * Load players in BDD from CSV file
	 * 
	 * @param absPathCSVFile
	 * @param delimiteur separator of CSV
	 */
	public void loadPlayersFromCSVFile(String absPathCSVFile, char delimiteur) {

		if (logger.isDebugEnabled())
			logger.debug(String.format("loadPlayersFromCSVFile(%s)", absPathCSVFile));

	
		// Delete all existing players
		playerDao.deleteAll();
		
		InputStreamReader inputStream;

		// Read CSV file
		try {
			inputStream = new InputStreamReader(new FileInputStream(Paths.get(absPathCSVFile).toFile()),
					StandardCharsets.UTF_8);
		} catch (FileNotFoundException ex) {
			throw new FantasyTeamException(String.format("CSV file [%s] not found", absPathCSVFile), ex);
		}

		// Parse CSV file and add data in database
		try (final Reader reader = new BufferedReader(inputStream)) {

			final CSVParser csvParser = new CSVParser(reader, CSVFormat.newFormat(delimiteur));
			parseCSV(csvParser);

		} catch (Exception ex) {
			throw new FantasyTeamException(String.format("Cannot load players data with CSV file [%s]", absPathCSVFile),
					ex);
		}
		
		if (logger.isInfoEnabled())
			logger.info(String.format("%d players load from CSV file [%s]", playerDao.count(), absPathCSVFile));

	}


	/**
	 * Parser CSV File and add data in database
	 * 
	 * @param csv
	 * @throws IOException
	 */
	private void parseCSV(CSVParser csv) throws IOException {

		final List<CSVRecord> data = csv.getRecords();
		if (null == data || data.isEmpty())
			return;

		data.stream()
			.skip(1) // Title line
			.filter(item -> item.size() > 1) // Does not include empty lines
			.map(Player::new)
			.forEach(item -> playerDao.save(item))
		;

	}

}
