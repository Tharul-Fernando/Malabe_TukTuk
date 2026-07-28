package com.example.malabe_tuktuk.service;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AuditLoggerTest {


    @Test
    void AL_01_testAdding() throws IOException {
        String path = "test_audit_log.txt";
        new File(path).delete();

        AuditLogger auditLogger = new AuditLogger(path);
        auditLogger.log("ADD_PART", "P010", 5);

        List<String> lines = Files.readAllLines(new File(path).toPath());
        String line = lines.get(0);

        assertTrue(line.contains("ADD_PART"));
        assertTrue(line.contains("P010"));
        assertTrue(line.contains("Qty: 5"));
    }


    @Test
    void AL_02_testCheckout() throws IOException {
        String path = "test_audit_log.txt";
        new File(path).delete();

        AuditLogger auditLogger = new AuditLogger(path);
        auditLogger.log("CHECKOUT", "P001", 2);
        auditLogger.log("CHECKOUT", "P002", 3);

        List<String> lines = Files.readAllLines(new File(path).toPath());

        assertEquals(2, lines.size());
        assertTrue(lines.get(0).contains("CHECKOUT"));
        assertTrue(lines.get(0).contains("P001"));
        assertTrue(lines.get(1).contains("CHECKOUT"));
        assertTrue(lines.get(1).contains("P002"));
    }
}