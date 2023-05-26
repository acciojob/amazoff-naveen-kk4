package com.driver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController {
    @Autowired
    OrderService service;


    @PostMapping("/add-order")
    public ResponseEntity<String> addOrder(@RequestBody Order order){
        service.addOrder(order);

        return new ResponseEntity<>("New order added successfully", HttpStatus.CREATED);
    }

    @PostMapping("/add-partner/{partnerId}")
    public ResponseEntity<String> addPartner(@PathVariable String partnerId){
        service.addPartner(partnerId);

        return new ResponseEntity<>("New delivery partner added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/add-order-partner-pair")
    public ResponseEntity<String> addOrderPartnerPair(@RequestParam String orderId, @RequestParam String partnerId){
        try{
            service.addOrderPartnerPair(partnerId,orderId);
            return new ResponseEntity<>("New order-partner pair added successfully", HttpStatus.CREATED);
        }
        catch(NotFoundException e){
            return new ResponseEntity<>("Entities not matching",HttpStatus.BAD_REQUEST);
        }

        //This is basically assigning that order to that partnerId

    }

    @GetMapping("/get-order-by-id/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId){
        try{
            Order order= service.getOrderById(orderId);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        }
        catch(NotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }



    }

    @GetMapping("/get-partner-by-id/{partnerId}")
    public ResponseEntity<DeliveryPartner> getPartnerById(@PathVariable String partnerId){
        try{
            DeliveryPartner deliveryPartner = service.getPartnerById(partnerId);
            return new ResponseEntity<>(deliveryPartner, HttpStatus.CREATED);
        }
        catch(NotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/get-order-count-by-partner-id/{partnerId}")
    public ResponseEntity<Integer> getOrderCountByPartnerId(@PathVariable String partnerId){
   try{
       Integer orderCount = service. getOrderCountByPartnerId(partnerId);
       return new ResponseEntity<>(orderCount, HttpStatus.CREATED);
   }
   catch(NotFoundException e){
       return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
   }

    }

    @GetMapping("/get-orders-by-partner-id/{partnerId}")
    public ResponseEntity<List<String>> getOrdersByPartnerId(@PathVariable String partnerId){
        try{
        List<String> orders = service.getOrdersByPartnerId(partnerId);
        return new ResponseEntity<>(orders, HttpStatus.CREATED);}
        catch(NotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<String>> getAllOrders(){
        List<String> orders = service.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/get-count-of-unassigned-orders")
    public ResponseEntity<Integer> getCountOfUnassignedOrders(){
        Integer countOfOrders = service.getCountOfUnassignedOrders();

        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }

    @GetMapping("/get-count-of-orders-left-after-given-time/{partnerId}")
    public ResponseEntity<Integer> getOrdersLeftAfterGivenTimeByPartnerId(@PathVariable String time, @PathVariable String partnerId){
       try{
           Integer countOfOrders = service.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
           return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
       }
       catch(NotFoundException e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

       }

    }

    @GetMapping("/get-last-delivery-time/{partnerId}")
    public ResponseEntity<String> getLastDeliveryTimeByPartnerId(@PathVariable String partnerId){
        try{
            String time = service.getLastDeliveryTimeByPartnerId(partnerId);
            return new ResponseEntity<>(time, HttpStatus.CREATED);
        }
        catch(NotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete-partner-by-id/{partnerId}")
    public ResponseEntity<String> deletePartnerById(@PathVariable String partnerId){

       try{
           service.deletePartnerById(partnerId);
           return new ResponseEntity<>(partnerId + " removed successfully", HttpStatus.CREATED);
       }
       catch(NotFoundException e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }


    }

    @DeleteMapping("/delete-order-by-id/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable String orderId){

      try{
          service.deleteOrderById(orderId);
          return new ResponseEntity<>(orderId + " removed successfully", HttpStatus.CREATED);
      }
      catch(NotFoundException e){
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }


    }
}
