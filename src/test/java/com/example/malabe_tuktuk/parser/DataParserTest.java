package com.example.malabe_tuktuk.parser;

import com.example.malabe_tuktuk.model.Part;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataParserTest {

    @Test
    void commaParser() {
        String rawData = "P001, Brake Pad, Toyota, Rs. 1500, 20, ENGINE, 2024-01-15, brake.jpg, 5";

        List<Part> result = DataParser.loadParts(rawData, true);
        Part part = result.get(0);

        assertEquals(1, result.size());
        assertEquals("P001", part.getCode());
        assertEquals(1500.0, part.getPrice());
        assertEquals(5, part.getLowStockThreshold());
    }

    @Test
    void pipeParser() {
        String rawData = "P002|Spark Plug|NGK|Rs. 250|15|ELECTRICAL|2024-03-10|spark.jpg";

        List<Part> result = DataParser.loadParts(rawData, true);
        Part part = result.get(0);

        assertEquals("P002", part.getCode());
        assertEquals("Spark Plug", part.getDescription());
        assertEquals(250.0, part.getPrice());
        assertEquals("ELECTRICAL", part.getCategory());
    }

    @Test
    void semicolonParser() {
        String rawData = "P003;Air Filter;Bosch;Rs. 800;12;ENGINE;2024-05-01;filter.jpg";

        List<Part> result = DataParser.loadParts(rawData, true);
        Part part = result.get(0);

        assertEquals("P003", part.getCode());
        assertEquals("Bosch", part.getBrand());
        assertEquals(800.0, part.getPrice());
    }

    @Test
    void testMissingBrand() {
        String rawData = "P004, Oil Filter, , Rs. 500, 8, ENGINE, 2024-02-01, oil.jpg";

        List<Part> result = DataParser.loadParts(rawData, true);

        assertEquals("Unknown/Generic", result.get(0).getBrand());
    }

    @Test
    void testMissingThreshold() {
        String rawData = "P005, Gasket, Bosch, Rs. 300, 10, ENGINE, 2024-01-01, gasket.jpg";

        List<Part> result = DataParser.loadParts(rawData, true);

        assertEquals(10, result.get(0).getLowStockThreshold());
    }

    @Test
    void testLegacyThreshold() {
        String rawData = "P006, Belt, Bosch, Rs. 700, 6, ENGINE, 2024-01-01, belt.jpg";

        List<Part> result = DataParser.loadParts(rawData, false);

        assertEquals(10, result.get(0).getLowStockThreshold());
    }

    @Test
    void testDateFormats() {
        String rawDataOne = "P007, Filter, Bosch, Rs. 400, 5, ENGINE, 15-01-2024, filter.jpg";
        String rawDataTwo = "P008, Filter, Bosch, Rs. 400, 5, ENGINE, 2024/01/15, filter.jpg";

        Part partOne = DataParser.loadParts(rawDataOne, false).get(0);
        Part partTwo = DataParser.loadParts(rawDataTwo, false).get(0);

        assertEquals("2024-01-15", partOne.getDateAdded());
        assertEquals("2024-01-15", partTwo.getDateAdded());
    }
}