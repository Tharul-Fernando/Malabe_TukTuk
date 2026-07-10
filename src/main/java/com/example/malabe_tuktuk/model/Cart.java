package com.example.malabe_tuktuk.model;

public class Cart {

    private Part part;
    private int quantity;

    public Cart(Part part, int quantity){
        this.part = part;
        this.setQuantity(quantity);
    }

    public Part getPart() {
        return part;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity > 0){
            this.quantity = quantity;
        }
        else{
            System.out.println("Error: Quantity cannot be negative");
        }
    }

    public double getSubtotal(){
        return part.getPrice() * quantity;
    }

    @Override
    public String toString(){
        return part.getCode()+ " | " + part.getDescription() + " | Qty: " + quantity;
    }

}
