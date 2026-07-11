package com.example.malabe_tuktuk.parser;

import com.example.malabe_tuktuk.model.Dealer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DealerParser {

    public static List<Dealer> readDealerFile(String filePath) {

        StringBuilder fileContent = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){

            String line;

            while((line = reader.readLine()) != null){
                fileContent.append(line).append("\n");
            }

        } catch (IOException e){
            System.out.println("Error: could not read file " + filePath +" - "+ e.getMessage());
            return new ArrayList<>();
        }
        return loadDealers(fileContent.toString());
    }

    public static void saveDealers(List<Dealer> dealers, String filePath) {

        try{
            FileWriter writer = new FileWriter(filePath);

            for (int i = 0; i < dealers.size(); i++){

                Dealer dealer = dealers.get(i);

                String line = dealer.getDealerCode() + ", " +
                        dealer.getDealerName() + ", " +
                        dealer.getPhoneNumber() + ", " +
                        dealer.getCity();

                writer.write(line);
                writer.write("\n");

            }

            writer.close();
        }
        catch (IOException e){
            System.out.println("Error: could not save  dealer file.");
        }
    }

    public static List<Dealer> loadDealerInventory(String cleanFilePath, String legacyFilePath){

        File cleanFile = new File(cleanFilePath);

        if (cleanFile.exists()) {
            return readDealerFile(cleanFilePath);
        }
        else{
            return readDealerFile(legacyFilePath);
        }
    }

    public static List<Dealer> loadDealers(String rawDealerData){

        String[] lines = rawDealerData.split("\n");

        ArrayList<Dealer> dealerList = new ArrayList<>();

        for (int i = 0; i< lines.length; i++){
            String line = lines[i].trim();
            if (line.isEmpty()){
                continue;
            }

            String cleanLine = line.replace("|",",");
            cleanLine = cleanLine.replace(";",",");

            String[] splitColumns = cleanLine.split(",",-1);

            String dealerCode = splitColumns[0].trim();
            String dealerName = splitColumns[1].trim();

            String dealerPhone = "Unknown";
            if (splitColumns.length > 2){
                String tempPhone = splitColumns[2].trim();
                if (!tempPhone.isEmpty()){
                    dealerPhone = tempPhone;
                }
            }

            String dealerCity = "Unknown City";
            if (splitColumns.length > 3){
                String tempCity = splitColumns[3].trim();
                if (!tempCity.isEmpty()){
                    dealerCity = tempCity;
                }
            }

            Dealer newDealer = new Dealer(dealerCode, dealerName, dealerPhone, dealerCity);
            dealerList.add(newDealer);

        }

        return  dealerList;
    }

}

