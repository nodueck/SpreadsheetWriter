package de.nodueck.spreadsheetwriter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.nodueck.spreadsheetwriter.service.SpreadsheetWriterService;

@RestController
public class SpreadsheetWriterController {

	@Autowired
	private SpreadsheetWriterService service;
	
    @RequestMapping("/insertRow")
    public void index(
    		@RequestParam String spreadsheetId, 
    		@RequestParam String range, 
    		@RequestParam List<Object> row) {
    	service.insertRow(spreadsheetId, range, row);
        return;
    }

}