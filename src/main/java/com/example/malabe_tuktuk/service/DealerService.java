package com.example.malabe_tuktuk.service;

import com.example.malabe_tuktuk.model.Dealer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DealerService {

    private List<Dealer> dealers;

    public DealerService(List<Dealer> dealers) {
        this.dealers = dealers;
    }

    public List<Dealer> getDealers() {
        return dealers;
    }

    public List<Dealer> getRandomDealers(){

        List<Dealer> selected = new ArrayList<>();

        if (dealers.size() < 4){
            System.out.println("Error: Not enough dealers to select four unique dealers.");

        }
        else {
            Random random = new Random();

            while (selected.size() < 4) {
                int index = random.nextInt(dealers.size());
                Dealer candidate = dealers.get(index);

                boolean alreadyPicked = false;
                for(int i = 0; i < selected.size(); i++){
                    if (selected.get(i).getDealerCode().equalsIgnoreCase(candidate.getDealerCode())){
                        alreadyPicked = true;
                        break;
                    }
                }

                if (!alreadyPicked){
                    selected.add(candidate);
                }
            }

        }
        return selected;
    }
}
