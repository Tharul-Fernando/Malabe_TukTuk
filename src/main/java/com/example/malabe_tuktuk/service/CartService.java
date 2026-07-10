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

}
