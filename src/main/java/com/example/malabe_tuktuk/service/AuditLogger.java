package com.example.malabe_tuktuk.service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLogger {

    private String logFilePath;

    public AuditLogger(String logFilePath){
        this.logFilePath = logFilePath;
    }

    public void log(String action, String itemCode, int quantity){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter);

        String logLine = timestamp + " | " +  action + " | " + itemCode + " | Qty: " + quantity;

        try{
            FileWriter writer = new FileWriter(logFilePath, true);
            writer.write(logLine);
            writer.write("\n");
            writer.close();
        }
        catch (IOException e) {
            System.out.println("Error: Could not write to log file.\n" +  e.getMessage() );
        }
    }
}
