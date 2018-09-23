package de.nodueck.spreadsheetwriter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.nodueck.spreadsheetwriter.model.InsertRow;
import de.nodueck.spreadsheetwriter.service.SpreadsheetWriterService;

@RestController
public class SpreadsheetWriterController {

	@Autowired
	private SpreadsheetWriterService service;
	
    @RequestMapping("/insertRow")
    public void index(@RequestBody InsertRow insertRow) {
    	service.insertRow(insertRow.getSpreadsheetId(), insertRow.getRange(), insertRow.getRow());
        return;
    }

}