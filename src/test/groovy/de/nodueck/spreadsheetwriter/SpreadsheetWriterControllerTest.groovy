package de.nodueck.spreadsheetwriter
import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.AppendValuesResponse
import com.google.api.services.sheets.v4.model.ValueRange
import de.nodueck.spreadsheetwriter.controller.SpreadsheetWriterController
import de.nodueck.spreadsheetwriter.service.SpreadsheetWriterService
import spock.lang.Specification
import spock.mock.DetachedMockFactory

@WebMvcTest(controllers = [SpreadsheetWriterController])
class SpreadsheetWriterControllerTest extends Specification {
	
	@Autowired
	protected MockMvc mvc                                       

	@Autowired
	SpreadsheetWriterService service
	
	@Autowired
	Sheets spreadsheetApi
	
	@Autowired
	ObjectMapper objectMapper
	
    def "should forward the call to google spreadsheet api"() {
        given:
		Map request = [
			"spreadsheetId": "1PtL3piYNw3I81iuwuXnuHGcDEnIsLxWYqaP5G8roB0Y",
			"range": "A3",
			"row": ["www.google.de", 2]
		]
		
        when:
        def result = mvc.perform(post('/insertRow').contentType(APPLICATION_JSON).content(toJson(request)))

        then:
        result.andExpect(status().isOk())
    }
	
	@TestConfiguration                                          
	static class StubConfig {
		DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

		@Bean
		SpreadsheetWriterService spreadsheetWriterService() {
			return detachedMockFactory.Stub(SpreadsheetWriterService)
		}
		
		@Bean
		Sheets sheetsStubbed() {
			return detachedMockFactory.Mock(Sheets)
		}
	}
}
