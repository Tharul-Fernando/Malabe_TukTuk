package com.example.malabe_tuktuk.service;

import com.example.malabe_tuktuk.model.Part;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {

    private List<Part> inventory;
    private int lowStockThreshold;
    private AuditLogger auditLogger;

    public InventoryService(List<Part> inventory){
        this.inventory = inventory;
        this.auditLogger = new AuditLogger("data/audit_log.txt");
    }

    public List<Part> getInventory(){
        return inventory;
    }


    public int getTotalPart(){
        return inventory.size();
    }

    public int getTotalStock(){
        int totalStock = 0;
        for (int i =0; i < inventory.size(); i++){
            totalStock += inventory.get(i).getQuantity();
        }
        return totalStock;
    }

    public double getTotalPrice(){
        double totalPrice = 0;
        for (int i = 0; i < inventory.size(); i++){
            Part part = inventory.get(i);
            totalPrice += part.getPrice() * part.getQuantity();
        }
        return totalPrice;
    }

    public List<Part> getLowStockParts(){
        List<Part> lowStockList = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++){
            Part part = inventory.get(i);
            if (part.getQuantity() < part.getLowStockThreshold()){
                lowStockList.add(part);
            }
        }
        return lowStockList;
    }

    public void sortByCate_Code(){

        int size = inventory.size();
        boolean swapped;

        for (int i = 0; i < size - 1; i++){
            swapped = false;

            for (int j = 0; j < size - 1 - i; j++){
                Part current = inventory.get(j);
                Part next = inventory.get(j+1);

                int compare = current.getCategory().compareTo(next.getCategory());

                boolean needSwap = false;

                if (compare > 0){
                    needSwap = true;
                }

                else if (compare == 0){

                    int codeCompare = current.getCode().compareTo(next.getCode());
                    if (codeCompare > 0){
                        needSwap = true;
                    }
                }
                if (needSwap){
                    inventory.set(j, next);
                    inventory.set(j+1, current);
                    swapped = true;
                }
            }
            if (!swapped){
                break;
            }
        }
    }

    public List<Part> searchParts(String keyword, String category, Double minPrice, Double maxPrice){
        List<Part> results = new ArrayList<>();

        for(int i = 0; i < inventory.size(); i++){
            Part part = inventory.get(i);

            if (keyword != null && !keyword.trim().isEmpty()){
                String descriptionLower = part.getDescription().toLowerCase();
                String keywordLower = keyword.trim().toLowerCase();
                if (!descriptionLower.contains(keywordLower)){
                    continue;
                }
            }

            if (category != null && !category.trim().isEmpty()){
                if (!part.getCategory().equalsIgnoreCase(category.trim())){
                    continue;
                }
            }

            if (minPrice != null) {
                if (part.getPrice() < minPrice) {
                    continue;
                }
            }

            if (maxPrice != null) {
                if (part.getPrice() > maxPrice) {
                    continue;
                }
            }

            results.add(part);

        }
        return results;
    }

    public String addPart(Part newPart){

        if (newPart.getCode() == null || newPart.getCode().trim().isEmpty()){
            return "Error: Part code cannot be empty";
        }

        for (int i =0; i < inventory.size(); i++){
            if (inventory.get(i).getCode().equalsIgnoreCase(newPart.getCode().trim())){
                return "Error: Part code "+ newPart.getCode() +" already exists";
            }
        }

        inventory.add(newPart);
        auditLogger.log("ADD_PART", newPart.getCode(), newPart.getQuantity());
        return "Part " + newPart.getCode() + " added successfully";
    }

    public String deletePart(String code){

        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i).getCode().equalsIgnoreCase(code.trim())){
                inventory.remove(i);
                auditLogger.log("DELETE_PART", code, 0);
                return "Part " + code + " deleted successfully";
            }
        }
        return "Error: Part code " + code + " does not exist";
    }

    public String updatePart(String code, String description,
                             String brand, double price, int quantity,
                             String category, String  dateAdded, String photoFileName, int lowStockThreshold){

        for (int i = 0; i < inventory.size(); i++){

            Part part = inventory.get(i);

            if (part.getCode().equalsIgnoreCase(code.trim())){
                part.setDescription(description);
                part.setBrand(brand);
                part.setPrice(price);
                part.setQuantity(quantity);
                part.setCategory(category);
                part.setDateAdded(dateAdded);
                part.setPhotoFileName(photoFileName);
                part.setLowStockThreshold(lowStockThreshold);
                auditLogger.log("UPDATE_PART", code, quantity);
                return "part " + code + " updated successfully";
            }
        }
        return "Error: Part code " + code + " does not exist";
    }
}
