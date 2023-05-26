package com.driver;

public class Order {

    private String id;
    private int deliveryTime1;
    private String deliveryTime2;

    public Order(String id, String deliveryTime) {
        this.id=id;
        this.deliveryTime1=time.getTime(deliveryTime);
        this.deliveryTime2=time.getTime(deliveryTime1);

           }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime1;}


}
