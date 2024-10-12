package org.example.doanki2.model.orders;

import org.example.doanki2.entity.*;
import org.example.doanki2.entity.DTO.Order_Detail_List;
import org.example.doanki2.model.paymentMethods.PaymentMethodsRepository;
import org.example.doanki2.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentMethodsRepository paymentMethodsRepository;

    public ResponseEntity<List<Orders>> getAllOrder(){
        List<Orders> ordersList = orderRepository.findAll();
        return ResponseEntity.ok(ordersList.stream().map(this::orders).collect(Collectors.toList()));
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
            orders1.setOrderDetails ((List) orders.getOrderDetails().stream().map(this::orderDetailList).collect(Collectors.toList()));
            return orders1;
    }
    public Order_Detail_List orderDetailList (Order_Details orderDetails){
        Order_Detail_List orderDetailList = new Order_Detail_List();
        orderDetailList.setOrder_detail_id(orderDetails.getOrder_detail_id());
        orderDetailList.setOrders(orderDetails.getOrders().getOrder_id());
        orderDetailList.setProducts(orderDetails.getProducts());
        orderDetailList.setQuantity(orderDetails.getQuantity());
        return orderDetailList;
    }

    public ResponseEntity<List<Orders>> getById(int id){
        Optional<Orders> optionalOrders = orderRepository.findById(id);
        if (!optionalOrders.isPresent()){
            throw new IllegalArgumentException("id order not found");
        }
        return ResponseEntity.ok(optionalOrders.stream().map(this::orders).collect(Collectors.toList()));
    }
    public ResponseEntity<List<Orders>> getByUserId(int id){
        List<Orders> optionalOrders = orderRepository.findByUserId(id);
        if (optionalOrders.isEmpty()){
            throw new IllegalArgumentException("id user order not found");
        }
        return ResponseEntity.ok(optionalOrders.stream().map(this::orders).collect(Collectors.toList()));
    }


    public ResponseEntity<Orders> postOrder(Orders orders){
        Optional<Users> usersOptional = userRepository.findById(orders.getUser().getUser_id());
        if (!usersOptional.isPresent()){
            throw new IllegalArgumentException("id user not found");
        }
        Optional<Payment_Methods> paymentMethodsOptional = paymentMethodsRepository.findById(orders.getPaymentMethods().getPayment_method_id());
        if (!paymentMethodsOptional.isPresent()){
            throw new IllegalArgumentException("id payment not found");
        }
        orders.setUser(usersOptional.get());
        orders.setPaymentMethods(paymentMethodsOptional.get());
        Orders orderSave = orderRepository.save(orders);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderSave.getOrder_id()).toUri();

        return ResponseEntity.created(location).body(orderSave);
    }
    public ResponseEntity<Orders> putOrder(int id,Orders orders){
        Optional<Orders> optionalOrders = orderRepository.findById(id);
        if (!optionalOrders.isPresent()){
            throw new IllegalArgumentException("id order not found");
        }
        Optional<Users> usersOptional = userRepository.findById(orders.getUser().getUser_id());
        if (!usersOptional.isPresent()){
            throw new IllegalArgumentException("id user not found");
        }
        Optional<Payment_Methods> paymentMethodsOptional = paymentMethodsRepository.findById(orders.getPaymentMethods().getPayment_method_id());
        if (!paymentMethodsOptional.isPresent()){
            throw new IllegalArgumentException("id payment not found");
        }

        Orders existingOrder = optionalOrders.get();

        // Cập nhật các trường cần thiết (trừ orderDetails)
        existingOrder.setUser(usersOptional.get());
        existingOrder.setAddress(orders.getAddress());
        existingOrder.setPaymentMethods(paymentMethodsOptional.get());
        existingOrder.setTotal_amount(orders.getTotal_amount());
        existingOrder.setOrder_date(orders.getOrder_date());
        existingOrder.setStatus(orders.getStatus());

        // Giữ nguyên danh sách orderDetails hiện có
        existingOrder.setOrderDetails(existingOrder.getOrderDetails());

        // Lưu đơn hàng cập nhật
        orderRepository.save(existingOrder);

        // Trả về phản hồi
        return ResponseEntity.ok().body(orders);
    }

    public ResponseEntity<Orders> deleteOrder(int id){
        Optional<Orders> ordersOptional = orderRepository.findById(id);
        if (!ordersOptional.isPresent()){
            throw new IllegalArgumentException("id not found");
        }
        orderRepository.deleteById(ordersOptional.get().getOrder_id());

        return ResponseEntity.noContent().build();
    }
}
