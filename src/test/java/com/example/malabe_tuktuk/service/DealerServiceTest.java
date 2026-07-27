package com.example.malabe_tuktuk.service;

import com.example.malabe_tuktuk.model.Dealer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DealerServiceTest {

    @Test
    void DS_01_testSortedByCity(){

        List<Dealer> dealers = new ArrayList<>();

        dealers.add(new Dealer("D1", "Dealer One", "0711111111", "Weliweriya"));
        dealers.add(new Dealer("D2", "Dealer Two", "0722222222", "Malabe"));
        dealers.add(new Dealer("D3", "Dealer Three", "0733333333", "Koswatta"));
        dealers.add(new Dealer("D4", "Dealer Four", "0744444444", "Kaduwela"));
        dealers.add(new Dealer("D5", "Dealer Five", "0755555555", "Athurugiriya"));

        DealerService dealerService = new DealerService(dealers);
        List<Dealer> selected = dealerService.getRandomDealers();

        for (int i = 0; i < selected.size() - 1; i++) {
            String currentCity = selected.get(i).getCity();
            String nextCity = selected.get(i + 1).getCity();
            assertTrue(currentCity.compareTo(nextCity) <= 0);
        }
    }

    @Test
    void DS_02_testFourUniqueDealer(){

        List<Dealer> dealers = new ArrayList<>();

        dealers.add(new Dealer("D1", "Dealer One", "0711111111", "Malabe"));
        dealers.add(new Dealer("D2", "Dealer Two", "0722222222", "Kaduwela"));
        dealers.add(new Dealer("D3", "Dealer Three", "0733333333", "Koswatta"));
        dealers.add(new Dealer("D4", "Dealer Four", "0744444444", "Athurugiriya"));
        dealers.add(new Dealer("D5", "Dealer Five", "0755555555", "Weliweriya"));
        dealers.add(new Dealer("D6", "Dealer Six", "0766666666", "Nugegoda"));

        DealerService dealerService = new DealerService(dealers);
        List<Dealer> selected = dealerService.getRandomDealers();

        assertEquals(4, selected.size());

        for (int i = 0; i < selected.size() - 1; i++) {
            for (int j = i + 1; j < selected.size(); j++) {
                assertNotEquals(selected.get(i).getDealerCode(), selected.get(j).getDealerCode());
            }
        }
    }

    @Test
    void DS_03_testLessDealers(){

        List<Dealer> dealers = new ArrayList<>();
        dealers.add(new Dealer("D1", "Dealer One", "0711111111", "Malabe"));
        dealers.add(new Dealer("D2", "Dealer Two", "0722222222", "Kaduwela"));

        DealerService dealerService = new DealerService(dealers);
        List<Dealer> selected = dealerService.getRandomDealers();

        assertTrue(selected.isEmpty());
    }
}