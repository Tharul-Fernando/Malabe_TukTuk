package com.example.malabe_tuktuk.model;

public class Part {

    private String code;
    private String description;
    private String brand;
    private double price;
    private int quantity;
    private String category;
    private String dateAdded;
    private String photoFileName;


    public Part(String code, String description, String brand,
                double price, int quantity, String category,
                String dateAdded, String photoFileName) {

        this.setCode(code);
        this.setDescription(description);
        this.setBrand(brand);
        this.setPrice(price);
        this.setQuantity(quantity);
        this.setCategory(category);
        this.setDateAdded(dateAdded);
        this.setPhotoFileName(photoFileName);
    }

    public String getCode(){
        return code;
    }

    public String getDescription(){
        return description;
    }

    public String getBrand(){
        return brand;
    }

    public double getPrice(){
        return price;
    }

    public int getQuantity(){
        return quantity;
    }

    public String getCategory(){
        return category;
    }

    public String getDateAdded(){
        return dateAdded;
    }

    public String getPhotoFileName(){
        return photoFileName;
    }



    public void setCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            System.out.println("Error: Code cannot be empty.");
        } else {
            this.code = code;
        }
    }

    public void setDescription(String description) {
        if( description == null || description.trim().isEmpty()){
            System.out.println("Error: Description cannot be empty.");
        }
        else{
            this.description = description;
        }
    }

    public void setBrand(String brand){
        if(brand == null || brand.trim().isEmpty()){
            System.out.println("Error: Brand cannot be empty.");
        }
        else{
            this.brand = brand;
        }
    }

    public void setPrice(double price) {
        if(price < 0){
            System.out.println("Error: Price cannot be negative.");
        }
        else{
            this.price = price;
        }
    }

    public void setQuantity(int quantity) {
        if(quantity < 0){
            System.out.println("Error: Quantity cannot be negative.");
        }
        else{
            this.quantity = quantity;
        }
    }


    public void setCategory(String category) {
        if(category == null || category.trim().isEmpty()){
            System.out.println("Error: Category cannot be empty.");
        }
        else{
            this.category = category;
        }
    }

    public void setDateAdded(String dateAdded) {
        if(dateAdded == null || dateAdded.trim().isEmpty()){
            System.out.println("Error: Date added cannot be empty.");
        }
        else{
            this.dateAdded = dateAdded;
        }
    }

    public void setPhotoFileName(String photoFileName) {
        if(photoFileName == null || photoFileName.trim().isEmpty()){
            System.out.println("Error: Photo file name cannot be empty.");
        }
        else{
            this.photoFileName = photoFileName;
        }
    }

    @Override
    public String toString(){
        return code + "|" +  description + "|" + brand + "|" + price + "|" + "Stock: " + quantity + "|" + category + "|" + dateAdded + "|" + photoFileName;
    }
}
