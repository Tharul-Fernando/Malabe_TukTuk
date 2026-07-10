package com.example.malabe_tuktuk.service;

import com.example.malabe_tuktuk.model.Cart;
import com.example.malabe_tuktuk.model.Part;

import java.util.ArrayList;
import java.util.List;

public class CartService {

    private List<Cart> cartItems;

    public CartService(){
        this.cartItems = new ArrayList<Cart>();
    }

    public List<Cart> getCartItems(){
        return cartItems;
    }

    public String addToCart(Part part, int quantity){

        if (quantity <= 0){
            System.out.println("Error: Quantity must be greater than 0");
        }

        if (quantity > part.getQuantity()){
            return "Error: Only " + part.getQuantity() + " units of " + part.getCode() + " can be added";
        }

        for (int i = 0; i < cartItems.size(); i++){
            Cart item = cartItems.get(i);
            if (item.getPart().getCode().equals(part.getCode())){

                int newQuantity = item.getQuantity() + quantity;

                if (newQuantity > part.getQuantity()){
                    return "Error: Cannot add " + quantity +
                            " more of " +part.getCode() + ", only "
                            + part.getQuantity() +" in stock";
                }

                item.setQuantity(newQuantity);
                return part.getCode() + " quantity updates in the cart.";
            }
        }

        cartItems.add(new Cart(part,  quantity));
        return part.getCode()+ " added to cart.";
    }

    public String removeFromCart(String partCode){

        for (int i = 0; i < cartItems.size(); i++){

            if (cartItems.get(i).getPart().getCode().equalsIgnoreCase(partCode.trim())){
                cartItems.remove(i);
                return partCode + " removed from cart.";
            }
        }
        return "Error: " + partCode + " does not exist in the cart.";
    }

    public double calculateTotal(){

        double total = 0;
        boolean hasEngine = false;
        boolean hasElectrical = false;

        for (int i = 0; i < cartItems.size(); i++){
            Cart item = cartItems.get(i);
            double subtotal = item.getSubtotal();

            if (item.getQuantity() >= 3){
                subtotal = subtotal * 0.95;
            }

            total += subtotal;

            String category = item.getPart().getCategory();
            if (category.equalsIgnoreCase("Engine")){
                hasEngine = true;
            }

            if (category.equalsIgnoreCase("Electrical")){
                hasElectrical = true;
            }
        }

        if (hasElectrical &&  hasEngine){
            total = total * 0.90;
        }

        return total;
    }
}
