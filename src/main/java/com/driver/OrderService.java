package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository dao;

    public void addOrder(Order order) {
        dao.addOrder(order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner a = new DeliveryPartner(partnerId);
        dao.addPartner(a);
    }

    public void addOrderPartnerPair(String partnerId, String orderId) throws NotFoundException
    {
        Optional<DeliveryPartner> partner = dao.getPartner(partnerId);
        Optional<Order> order = dao.getOrder(orderId);
        if(partner.isEmpty() || order.isEmpty())throw new NotFoundException("the requested entities are not found");
        dao.addPair(partnerId,orderId);
        DeliveryPartner curr = partner.get();
        curr.setNumberOfOrders(curr.getNumberOfOrders()+1);

    }

    public Order getOrderById(String orderId) throws NotFoundException{
        Optional<Order> order = dao.getOrder(orderId);
        if(order.isEmpty())throw new NotFoundException("the requested entity is not found");
        return order.get();

    }

    public DeliveryPartner getPartnerById(String partnerId) throws NotFoundException {
        Optional<DeliveryPartner> partner = dao.getPartner(partnerId);
        if(partner.isEmpty())throw new NotFoundException("the requested entity is not found");
        return partner.get();
    }

    public Integer getOrderCountByPartnerId(String partnerId) throws NotFoundException {
        Optional<DeliveryPartner> partner = dao.getPartner(partnerId);
        if(partner.isEmpty())throw new NotFoundException("the requested entity is not found");
        return partner.get().getNumberOfOrders();
    }

    public List<String> getOrdersByPartnerId(String partnerId) throws NotFoundException {
        Optional<DeliveryPartner> partner = dao.getPartner(partnerId);
        if(partner.isEmpty())throw new NotFoundException("the requested entity is not found");
        return dao. getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return dao.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return dao.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) throws NotFoundException {
        Optional<DeliveryPartner> partner = dao.getPartner(partnerId);
        if(partner.isEmpty())throw new NotFoundException("the requested entity is not found");
        String a = "";
        String b = "";
        boolean found = false;
        for(int i = 0;i<time.length();i++){
            char ch = time.charAt(i);
            if(ch==':'){
                found = true;
                continue;
            }
            if(!found)a+=ch;
            else b+=ch;

        }
        int time1 = (Integer.valueOf(a)*60) + Integer.valueOf(b);
        int count = 0;
        List<String> orders = getOrdersByPartnerId(partnerId);
        for(String order : orders){
            Optional<Order> curr = dao.getOrder(order);
            if(curr.isPresent()){
                Order c = curr.get();
               int currTime = c.getDeliveryTime();
               if(currTime>time1)count++;

            }

        }
        return count;

    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) throws NotFoundException{
        Optional<DeliveryPartner> partner = dao.getPartner(partnerId);
        if(partner.isEmpty())throw new NotFoundException("the requested entity is not found");
        List<String> orders = getOrdersByPartnerId(partnerId);
        int max = 0;
        for(String order : orders){
            Optional<Order> curr = dao.getOrder(order);
            if(curr.isPresent()){
                Order c = curr.get();
                int currTime = c.getDeliveryTime();
                max = Math.max(max,currTime);

            }

        }
        String a = String.valueOf(max/60);
        String b = String.valueOf(max%60);
        return a+":"+b;

    }

    public void deletePartnerById(String partnerId) throws NotFoundException{
        Optional<DeliveryPartner> partner = dao.getPartner(partnerId);
        if(partner.isEmpty())throw new NotFoundException("the requested entity is not found");
        List<String> orders = getOrdersByPartnerId(partnerId);
        for(String order : orders){
            dao.removeFromCollections2(order);
        }
        dao.removeFromCollections1(partnerId);


    }

    public void deleteOrderById(String orderId) {
        Optional<Order> order = dao.getOrder(orderId);
        Optional<String> partner = dao.getPartnerOfOrder(orderId);
        if(order.isEmpty() || partner.isEmpty())throw new NotFoundException("the requested entity is not found");
        dao.removeOrderFromCollection1(partner.get(),orderId);
        dao.removeFromCollections2(orderId);
        Optional<DeliveryPartner> partner2 = dao.getPartner(partner.get());
        DeliveryPartner partner3 = partner2.get();
        partner3.setNumberOfOrders(partner3.getNumberOfOrders()-1);


    }
}
