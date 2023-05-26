package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {

    Map<String,Order> orders = new HashMap<>();
    Map<String,DeliveryPartner> partners = new HashMap<>();
    Map<String, List<String>> collections1 = new HashMap<>();
    Map<String,String> collections2= new HashMap<>();


    public void addOrder(Order order) {
        orders.put(order.getId(),order);

    }

    public void addPartner(DeliveryPartner a) {
        partners.put(a.getId(),a);
        collections1.put(a.getId(),new ArrayList<>());

    }

    public Optional<DeliveryPartner> getPartner(String partnerId) {
        return partners.containsKey(partnerId)?Optional.of(partners.get(partnerId)):Optional.empty();
    }

    public Optional<Order> getOrder(String orderId) {
        return orders.containsKey(orderId)?Optional.of(orders.get(orderId)):Optional.empty();
    }

    public void addPair(String partnerId, String orderId) {
        collections1.get(partnerId).add(orderId);
        collections2.put(orderId,partnerId);

    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return new ArrayList<>(collections1.get(partnerId));
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orders.keySet());
    }

    public Integer getCountOfUnassignedOrders() {
        return orders.size()-collections2.size();
    }

    public void removeFromCollections2(String order) {
        collections2.remove(order);
    }

    public void removeFromCollections1(String partnerId) {
        collections1.remove(partnerId);
        partners.remove(partnerId);
    }

    public Optional<String> getPartnerOfOrder(String orderId) {
        return collections2.containsKey(orderId)?Optional.of(collections2.get(orderId)):Optional.empty();
    }

    public void removeOrderFromCollection1(String s, String orderId) {
        collections1.get(s).remove(orderId);
    }
}
