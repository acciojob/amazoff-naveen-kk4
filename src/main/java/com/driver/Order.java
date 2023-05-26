package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {
        this.id=id;
        String a = "";
        String b = "";
        boolean found = false;
        for(int i = 0;i<deliveryTime.length();i++){
            char ch = deliveryTime.charAt(i);
            if(ch==':'){
                found = true;
                continue;
            }
            if(!found)a+=ch;
            else b+=ch;

        }
        this.deliveryTime=(Integer.valueOf(a)*60) + Integer.valueOf(b);

           }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
