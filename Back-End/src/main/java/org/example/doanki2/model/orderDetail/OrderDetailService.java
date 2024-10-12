package org.example.doanki2.model.orderDetail;

import org.example.doanki2.entity.*;
import org.example.doanki2.model.orders.OrderRepository;
import org.example.doanki2.model.products.ProductRepository;
import org.hibernate.type.descriptor.java.ObjectJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;


    public ResponseEntity<List<Order_Details>> getAllOrderDetail(){
        List<Order_Details> orderDetails = orderDetailRepository.findAll();
        return ResponseEntity.ok(orderDetails.stream().map(this::orderDetails).collect(Collectors.toList()));
    }

    public Order_Details orderDetails(Order_Details orderDetails){
        Order_Details orderDetails1 = new Order_Details();
        orderDetails1.setOrder_detail_id(orderDetails.getOrder_detail_id());
        orderDetails1.setOrders(orders(orderDetails.getOrders()));
        orderDetails1.setProducts(orderDetails.getProducts());
        orderDetails1.setQuantity(orderDetails.getQuantity());
        return orderDetails1;
    }
    public Orders orders (Orders orders){
        Orders orders1 = new Orders();
        orders1.setOrder_id (orders.getOrder_id());
        orders1.setUser (orders.getUser());
        orders1.setFirst_name(orders.getFirst_name());
        orders1.setLast_name(orders.getLast_name());
        orders1.setAddress (orders.getAddress());
        orders1.setPhone_number (orders.getPhone_number());
        orders1.setEmail (orders.getEmail());
        orders1.setPaymentMethods (orders.getPaymentMethods());
        orders1.setTotal_amount (orders.getTotal_amount());
        orders1.setOrder_date (orders.getOrder_date());
        orders1.setStatus (orders.getStatus());
        return orders1;
    }
    public ResponseEntity<List<Order_Details>> getById (int id){
        Optional<Order_Details> orderDetails = orderDetailRepository.findById(id);
        if (!orderDetails.isPresent()){
            throw new IllegalArgumentException("id order detail not found");
        }
        return ResponseEntity.ok().body(orderDetails.stream().map(this::orderDetails).collect(Collectors.toList()));
    }
    public ResponseEntity<Order_Details> post(Order_Details orderDetails){
        Order_Details orderDetails1 = orderDetailRepository.save(orderDetails);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderDetails1.getOrder_detail_id()).toUri();
        return ResponseEntity.created(location).body(orderDetails1);
    }
    public ResponseEntity<Order_Details> put(int id, Order_Details orderDetails){
        Optional<Order_Details> orderDetails1 = orderDetailRepository.findById(id);
        if (!orderDetails1.isPresent()){
            throw new IllegalArgumentException("id order detail not found");
        }
        orderDetails.setOrder_detail_id(orderDetails1.get().getOrder_detail_id());
        orderDetailRepository.save(orderDetails);

        return ResponseEntity.ok().body(orderDetails);
    }
    public ResponseEntity<Order_Details> delete(int id){
        Optional<Order_Details> optional = orderDetailRepository.findById(id);
        if (!optional.isPresent()){
            throw new IllegalArgumentException("id order detail not found");
        }
        orderDetailRepository.deleteById(optional.get().getOrder_detail_id());
        return ResponseEntity.noContent().build();
    }
}
