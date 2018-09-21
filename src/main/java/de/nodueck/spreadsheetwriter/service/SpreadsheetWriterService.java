package de.nodueck.spreadsheetwriter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

@Service
public class SpreadsheetWriterService {
	
	@Autowired
    private Sheets sheetsApi;

	public void insertRow(final String spreadsheetId, final String range, final List<Object> row) {
		try {			  
			final ValueRange valueRange = rowToValueRange(row);
			sheetsApi.spreadsheets().values().append(spreadsheetId, range, valueRange)
			.setValueInputOption("RAW")
			.setInsertDataOption("INSERT_ROWS")
			.execute();
		}catch(Exception e) {
			System.err.println("An error occured:" + e.getMessage());
		}
	}
	
	private ValueRange rowToValueRange(List<Object> row) {
		final ValueRange valueRange = new ValueRange();
		final List<List<Object>> cols = new ArrayList<>();
		cols.add(row);
		valueRange.setValues(cols);
		return valueRange;
	}
}
