package com.example.malabe_tuktuk.parser;

import com.example.malabe_tuktuk.model.Part;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class DataParser {

    public static List<Part> readPartsFile(String filePath, boolean hasThreshold){

        StringBuilder fileContent = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){

            String line;
            while ((line= reader.readLine()) != null){
                fileContent.append(line).append("\n");
            }

        } catch (IOException e){
            System.out.println("Error: Could not read file " + filePath + " - " + e.getMessage());
            return new ArrayList<>();
        }
        return  loadParts(fileContent.toString(), hasThreshold);
    }

    public static List<Part> loadParts(String rawData,  boolean hasThreshold) {


        String[] lines = rawData.split("\n");

        List<Part> inventory_list = new ArrayList<>();

        for (int i = 0; i < lines.length; i++) {
            String singleLine = lines[i].trim();
            if (singleLine.isEmpty()) {
                continue;
            }

            String commaSepLine = singleLine.replace("|", ",");
            commaSepLine = commaSepLine.replace(";", ",");

            String[] splitColumns = commaSepLine.split("[,]", -1);

            int itemThreshold = 10;

            if (hasThreshold && splitColumns.length > 0) {
                String lastColumn = splitColumns[splitColumns.length -1].trim();
                if (!lastColumn.isEmpty()) {
                    try{
                        itemThreshold = Integer.parseInt(lastColumn);
                        splitColumns = Arrays.copyOf(splitColumns, splitColumns.length - 1);
                    }
                    catch (NumberFormatException e){
                        itemThreshold = 10;
                    }
                }
            }

            String itemCode = splitColumns[0].trim();
            String itemDescription = splitColumns[1].trim();

            String itemBrand = splitColumns[2].trim();
            if (itemBrand.isEmpty()) {
                itemBrand = "Unknown/Generic";
            }

            String priceText = splitColumns[3].trim();
            priceText = priceText.replace("Rs.", "");
            priceText = priceText.replace("Rs ", "");
            priceText = priceText.replace("Rs", "");
            priceText = priceText.trim();
            double itemPrice = Double.parseDouble(priceText);

            int itemQty = Integer.parseInt(splitColumns[4].trim());
            String itemCategory = splitColumns[5].trim().toUpperCase();

            String itemDate = "Unknown Date";
            String itemPhoto = "default_image.jpg";

            if (splitColumns.length == 9) {
                String rawDate = splitColumns[6].trim()+ " " + splitColumns[7].trim();
                if (!rawDate.isEmpty()){
                    itemDate = DateFormat(rawDate);
                }

                String tempPhoto = splitColumns[8].trim();
                if (!tempPhoto.isEmpty()){
                    itemPhoto = tempPhoto;
                }
            }

            else {
                if (splitColumns.length > 6) {
                    String tempDate = splitColumns[6].trim();
                    if (!tempDate.isEmpty()){
                        itemDate = DateFormat(tempDate);
                    }
                }

                if (splitColumns.length > 7) {
                    String tempPhoto = splitColumns[7].trim();
                    if (!tempPhoto.isEmpty()){
                        itemPhoto = tempPhoto;
                    }
                }
            }

            Part newPart = new Part(itemCode, itemDescription,
                    itemBrand, itemPrice, itemQty, itemCategory,
                    itemDate, itemPhoto,itemThreshold);

            inventory_list.add(newPart);
        }

        return inventory_list;

    }

    public static void saveParts(List<Part> parts, String filePath){

        try{
            FileWriter writer = new FileWriter(filePath);

            for (int i = 0; i < parts.size(); i++){

                Part part = parts.get(i);

                String line = part.getCode() + ", " +
                        part.getDescription() + ", " +
                        part.getBrand() + ", " +
                        part.getPrice() + ", " +
                        part.getQuantity() + ", " +
                        part.getCategory() + ", " +
                        part.getDateAdded() + ", " +
                        part.getPhotoFileName() +", "+
                        part.getLowStockThreshold();


                writer.write(line);
                writer.write("\n");
            }
            writer.close();
        }
        catch (IOException e){
            System.out.println("Error; Could not save inventory file");
        }
    }

    public static List<Part> loadInventory(String cleanFilePath, String legacyFilePath){

        File cleanFile = new File(cleanFilePath);

        if (cleanFile.exists()) {
            return readPartsFile(cleanFilePath,true);
        }
        else{
            return readPartsFile(legacyFilePath, false);
        }
    }

    public static String DateFormat(String rawDate) {

        String[] datePatterns = {
                "yyyy-MM-dd",
                "dd-MM-yyyy",
                "MMM-dd-yyyy",
                "dd/MM/yyyy",
                "yyyy/MM/dd",
                "dd-MMM-yyyy",
                "MMM dd yyyy",
                "dd MM yyyy",
                "yyyy dd MM",
                "yyyy MM dd"
        };
        for (int i = 0; i < datePatterns.length; i++) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePatterns[i], Locale.ENGLISH);
                LocalDate parsedDate = LocalDate.parse(rawDate, formatter);

                return parsedDate.toString();

            } catch (Exception e) {

            }
        }
        return rawDate;
    }
}

