package de.nodueck.spreadsheetwriter.model;

import java.util.List;

public class InsertRow {
	
	private String range;
	
	private List<Object> row;
	
	private String spreadSheetId;

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

	public String getSpreadSheetId() {
		return spreadSheetId;
	}

	public void setSpreadSheetId(String spreadSheetId) {
		this.spreadSheetId = spreadSheetId;
	}
}
