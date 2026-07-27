package com.example.malabe_tuktuk.service;

import com.example.malabe_tuktuk.model.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {

    private CartService cartService;
    private Part enginePart;
    private Part electricalPart;


    @BeforeEach
    void setUp() {
        cartService = new CartService();

        enginePart = new Part("E001", "Piston",
                "Toyota", 100.0, 10, "ENGINE",
                "2024-01-01", "piston.jpg", 5);

        electricalPart = new Part("EL001", "Battery",
                "Bosch", 200.0, 10, "ELECTRICAL",
                "2024-01-01", "battery.jpg", 5);

    }

    @Test
    void CS_01_testBulkDiscount() {

        cartService.addToCart(enginePart,3);

        double expected = 100.0 * 3 * 0.05;
        double actual = cartService.getCartBulkDiscountTotal();

        assertEquals(expected, actual);
    }

    @Test
    void CS_02_testBulkDiscountBelowLimit() {

        cartService.addToCart(enginePart,2);
        double actual = cartService.getCartBulkDiscountTotal();

        assertEquals(0.0, actual);
    }

    @Test
    void CS_03_testSynergyDiscount() {

        cartService.addToCart(enginePart,1);
        cartService.addToCart(electricalPart,1);

        double synergyDis = cartService.getSynergyDiscountAmount();
        assertTrue(synergyDis > 0);
    }

    @Test
    void CS_04_testNoSynergyDiscount() {

        cartService.addToCart(enginePart,1);
        double synergyDis = cartService.getSynergyDiscountAmount();

        assertFalse(synergyDis > 0);
        assertEquals(0.0, synergyDis);
    }

    @Test
    void CS_05_testStockDeduction() {

        cartService.addToCart(enginePart,5);

        cartService.checkout();
        assertEquals(5,enginePart.getQuantity());
    }

    @Test
    void CS_06_testEmptyCart() {

        String result = cartService.checkout();

        assertTrue(result.startsWith("Error"));
    }


    @Test
    void CS_07_testStockOver() {

        String result = cartService.addToCart(enginePart,999);

        assertTrue(result.startsWith("Error"));
        assertEquals(0, cartService.getCartItems().size());
    }

    @Test
    void CS_08_testNegativeAndZero() {

        String resultZero = cartService.addToCart(enginePart,0);
        String resultNegative = cartService.addToCart(electricalPart,-2);

        assertTrue(resultZero.startsWith("Error"));
        assertTrue(resultNegative.startsWith("Error"));
        assertEquals(0, cartService.getCartItems().size());

    }
}