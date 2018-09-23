package de.nodueck.spreadsheetwriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AbstractPromptReceiver;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

@Configuration
public class SpreadsheetWriterConfig {
	
	private static final String APPLICATION_NAME = "SpreadsheetWriter";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    
    @Value("${spreadsheetwriter.credentials.filepath}")
    private String credentialsFilePath = "/home/nikolai/Downloads/credentials.json";
    
    @Value("${spreadsheetwriter.auth.host}")
    private String host;
    
    @Value("${spreadsheetwriter.auth.port}")
    private int port;
    
    @Value("${spreadsheetwriter.user}")
    private String user = "user";
    
    @Bean
    public Sheets sheets() throws Exception {
    	NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
		.setApplicationName(APPLICATION_NAME)
		.build();
    }
	
	/**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        final FileInputStream file = new FileInputStream(new File(credentialsFilePath));
        final InputStreamReader reader = new InputStreamReader(file);
        final GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, reader);

        // Build flow and trigger user authorization request.
        final GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        
        final AbstractPromptReceiver abstractPromptReceiver = new AbstractPromptReceiver() {
			@Override
			public String getRedirectUri() throws IOException {
				return "http://" + host + ":" + port + "/Callback";
			}
		};
        return new AuthorizationCodeInstalledApp(flow, abstractPromptReceiver).authorize(user);
    }
    
    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter
          = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }

}
