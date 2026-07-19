package com.example.malabe_tuktuk.service;

import com.example.malabe_tuktuk.model.Cart;
import com.example.malabe_tuktuk.model.Part;

import java.util.ArrayList;
import java.util.List;

public class CartService {

    private List<Cart> cartItems;
    private AuditLogger auditLogger;

    public CartService(){
        this.cartItems = new ArrayList<Cart>();
        this.auditLogger = new AuditLogger("data/audit_log.txt");
    }

    public List<Cart> getCartItems(){
        return cartItems;
    }

    public String addToCart(Part part, int quantity){

        if (quantity <= 0){
            return ("Error: Quantity must be greater than 0");
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


    public double getSubtotalForItem(Cart item){
        return item.getPart().getPrice() * item.getQuantity();
    }

    public double getBulkDiscountForItem(Cart item){

        double subtotal = getSubtotalForItem(item);

        if (item.getQuantity() >= 3){
            return subtotal * 0.05;
        }
        return 0;
    }

    public double getLineTotalForItem(Cart item){
        return getSubtotalForItem(item) - getBulkDiscountForItem(item);
    }


    public double getCartSubtotal(){

        double subtotal = 0;
        for (int i = 0; i < cartItems.size(); i++){
            subtotal += getSubtotalForItem(cartItems.get(i));
        }
        return subtotal;
    }

    public double getCartBulkDiscountTotal(){

        double bulkDiscountTotal = 0;
        for (int i = 0; i < cartItems.size(); i++){
            bulkDiscountTotal += getBulkDiscountForItem(cartItems.get(i));
        }
        return bulkDiscountTotal;
    }

    private boolean hasEngineAndElectrical(){

        boolean hasEngine = false;
        boolean hasElectrical = false;

        for (int i = 0; i < cartItems.size(); i++){
            String category = cartItems.get(i).getPart().getCategory();

            if (category.equalsIgnoreCase("Engine")){
                hasEngine = true;
            }

            if (category.equalsIgnoreCase("Electrical")){
                hasElectrical = true;
            }
        }

        return hasEngine && hasElectrical;
    }

    public double getSynergyDiscountAmount(){

        if (hasEngineAndElectrical()){
            double afterBulkDiscount = getCartSubtotal() - getCartBulkDiscountTotal();
            return afterBulkDiscount * 0.10;
        }
        return 0;
    }

    public double calculateTotal(){
        return getCartSubtotal() - getCartBulkDiscountTotal() - getSynergyDiscountAmount();
    }

    public String checkout(){

        if (cartItems.isEmpty()){
            return "Error: Cart is Empty, Cannot checkout";
        }

        for (int i = 0; i < cartItems.size(); i++){
            Cart item = cartItems.get(i);
            if (item.getQuantity() > item.getPart().getQuantity()){
                return "Error: Not enough stock for "+ item.getPart().getCode() +" to complete the order.";
            }
        }

        for (int i = 0; i < cartItems.size(); i++){
            Cart item = cartItems.get(i);
            Part part = item.getPart();
            part.setQuantity(part.getQuantity() - item.getQuantity());
            auditLogger.log("CHECKOUT", item.getPart().getCode(), item.getQuantity());
        }

        double finalTotal =  calculateTotal();
        cartItems.clear();

        return "Checkout successful. Total charged: Rs. " + finalTotal;
    }
}
