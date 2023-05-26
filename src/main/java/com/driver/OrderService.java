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

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String t, String partnerId) throws NotFoundException {
        Optional<DeliveryPartner> partner = dao.getPartner(partnerId);
        if(partner.isEmpty())throw new NotFoundException("the requested entity is not found");
       int time1 = time.getTime(t);
       List<String> list = dao.getOrdersByPartnerId(partnerId);
       int count = 0;
       for(String str : list){
           Optional<Order> e = dao.getOrder(str);
           if(!e.isEmpty() && e.get().getDeliveryTime() > time1 )count++;
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
                int currTime = curr.get().getDeliveryTime();
                max = Math.max(max,currTime);

            }

        }
        return max==0?"0":time.getTime(max);


    }

    public void deletePartnerById(String partnerId) throws NotFoundException{
        List<String> orders = this.getOrdersByPartnerId(partnerId);
        for(String order : orders){
            dao.removeFromCollections2(order);
        }
        dao.removeFromCollections1(partnerId);


    }

    public void deleteOrderById(String orderId) {
        Optional<Order> order = dao.getOrder(orderId);
        if(order.isEmpty())throw new NotFoundException("the requested entity is not found");

        Optional<String> partner = dao.getPartnerOfOrder(orderId);
        if(partner.isEmpty())throw new NotFoundException("the requested entity is not found");
        dao.removeFromCollections2(orderId);
        dao.removeOrderFromCollection1(partner.get(), orderId);
        Optional<DeliveryPartner> partner2 = dao.getPartner(partner.get());
        DeliveryPartner partner3 = partner2.get();
        partner3.setNumberOfOrders(partner3.getNumberOfOrders() - 1);



    }
}
