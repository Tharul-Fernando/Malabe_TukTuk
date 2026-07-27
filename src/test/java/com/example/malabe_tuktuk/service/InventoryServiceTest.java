package com.example.malabe_tuktuk.service;

import com.example.malabe_tuktuk.model.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {

    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {

        List<Part> parts = new ArrayList<>();

        parts.add(new Part("B002", "Brake Pad",
                "Toyota", 1500.0, 3, "BRAKES",
                "2024-01-01", "brake.jpg", 5));

        parts.add(new Part("B001", "Brake Disc",
                "Toyota", 3000.0, 20, "BRAKES",
                "2024-01-01", "disc.jpg", 5));

        parts.add(new Part("E001", "Spark Plug",
                "NGK", 250.0, 8, "ENGINE",
                "2024-01-01", "spark.jpg", 10));

        inventoryService = new InventoryService(parts);

    }

    @Test
    void IS_01_testSearchByKeyword(){

        List<Part> result = inventoryService.searchParts("brake",null,null,null);
        assertEquals(2,result.size());
    }

    @Test
    void IS_02_testSearchAll(){

        List<Part> result = inventoryService.searchParts("spark","ENGINE",100.0,500.0);
        assertEquals(1,result.size());
        assertEquals("E001", result.get(0).getCode());
    }

    @Test
    void IS_03_testSearchByNull(){

        List<Part> result = inventoryService.searchParts(null,null,null,null);
        assertEquals(3,result.size());

    }


    @Test
    void IS_04_testSortByCategory_Code(){

        inventoryService.sortByCate_Code();
        List<Part> sorted = inventoryService.getInventory();

        assertEquals("B001", sorted.get(0).getCode());
        assertEquals("B002", sorted.get(1).getCode());
        assertEquals("E001", sorted.get(2).getCode());
    }

    @Test
    void IS_05_testLowStock(){

        List<Part> lowStock = inventoryService.getLowStockParts();

        boolean found = false;
        for (Part part : lowStock) {
            if (part.getCode().equals("B002")) {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    void IS_06_testHighStock(){

        List<Part> lowStock = inventoryService.getLowStockParts();

        boolean notFound = false;
        for(Part part : lowStock){
            if (!part.getCode().equals("B001")) {
                notFound = true;
            }
        }
        assertTrue(notFound);
    }

    @Test
    void IS_07_testDuplicate() {

        Part duplicate = new Part("B001", "Another Disc",
                "Bosch", 2000.0, 5, "BRAKES",
                "2024-01-01", "x.jpg", 5);

        String result = inventoryService.addPart(duplicate);

        assertTrue(result.startsWith("Error"));
        assertEquals(3, inventoryService.getInventory().size());
    }

    @Test
    void IS_08_testDeleteNonExistent() {

        String result = inventoryService.deletePart("zzz999");

        assertTrue(result.startsWith("Error"));
        assertEquals(3, inventoryService.getInventory().size());
    }
}