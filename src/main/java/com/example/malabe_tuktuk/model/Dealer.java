package com.example.malabe_tuktuk.model;

public class Dealer {
    private String dealerCode;
    private String dealerName;
    private String phoneNumber;
    private String city;

    public Dealer(String dealerCode, String dealerName, String phoneNumber, String city) {
        this.setDealerCode(dealerCode);
        this.setDealerName(dealerName);
        this.setPhoneNumber(phoneNumber);
        this.setCity(city);
    }

    public String getDealerCode(){
        return dealerCode;
    }

    public String getDealerName(){
        return dealerName;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getCity(){
        return city;
    }



    public void setDealerCode(String dealerCode){
        if (dealerCode == null || dealerCode.trim().isEmpty()){
            System.out.println("Error: dealerCode cannot be empty");
        }
        else{
            this.dealerCode = dealerCode;
        }
    }

    public void setDealerName(String dealerName){
        if (dealerName == null || dealerName.trim().isEmpty()){
            System.out.println("Error: dealerName cannot be empty");
        }
        else{
            this.dealerName = dealerName;
        }
    }

    public void setPhoneNumber(String phoneNumber){
        if (phoneNumber == null || phoneNumber.trim().isEmpty()){
            this.phoneNumber = "Unknown";
        }
        else{
            this.phoneNumber = phoneNumber;
        }
    }

    public void setCity (String city){
        if (city == null || city.trim().isEmpty()){
            this.city = "Unknown City";
        }
        else{
            this.city = city;
        }
    }

    @Override
    public String toString(){
        return dealerCode + " | " +  dealerName + " | " +  phoneNumber + " | " + city ;
    }
}

