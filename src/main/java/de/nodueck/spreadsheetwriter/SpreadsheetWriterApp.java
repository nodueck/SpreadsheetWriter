package de.nodueck.spreadsheetwriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "de.nodueck.spreadsheetwriter")
public class SpreadsheetWriterApp {

    public static void main(String[] args) {
        SpringApplication.run(SpreadsheetWriterApp.class, args);
    }
}