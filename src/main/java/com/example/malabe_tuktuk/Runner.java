package com.example.malabe_tuktuk;

import com.example.malabe_tuktuk.model.Dealer;
import com.example.malabe_tuktuk.model.Part;
import com.example.malabe_tuktuk.parser.DataParser;
import com.example.malabe_tuktuk.parser.DealerParser;

import java.util.List;

public class Runner {

    public static void main(String[] args) {

        List<Part> parts = DataParser.readPartsFile("data/inventory_legacy.txt");

        System.out.println("Parts.size: " + parts.size());

        for (int i = 0; i < parts.size(); i++) {
            Part p = parts.get(i);
            System.out.println(p);
        }

        System.out.println();

        List<Dealer> dealers = DealerParser.readDealerFile("data/dealers_legacy.txt");

        System.out.println("Loaded " + dealers.size() + " dealers");
        for (int i = 0; i < dealers.size(); i++) {
            Dealer d = dealers.get(i);
            System.out.println(d);
        }
    }


}
