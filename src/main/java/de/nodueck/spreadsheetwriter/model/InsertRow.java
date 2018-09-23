package de.nodueck.spreadsheetwriter.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsertRow {
	
	@JsonProperty("range")
	private String range;
	
	@JsonProperty("row")
	private List<Object> row;
	
	@JsonProperty("spreadsheetId")
	private String spreadsheetId;

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}
	
	public List<Object> getRow(){
		return row;
	}
	
	public void setRow(List<Object> row) {
		this.row = row;
	}

	public String getSpreadsheetId() {
		return spreadsheetId;
	}

	public void setSpreadsheetId(String spreadsheetId) {
		this.spreadsheetId = spreadsheetId;
	}
}
