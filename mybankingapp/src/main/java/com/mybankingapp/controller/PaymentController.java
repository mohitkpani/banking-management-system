package com.mybankingapp.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@RestController
@RequestMapping("/payment")
@CrossOrigin("*")
public class PaymentController {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @PostMapping("/create-order")
    public String createOrder(@RequestParam int amount) {

        try {

            RazorpayClient razorpay =
                    new RazorpayClient(keyId, keySecret);

            JSONObject orderRequest = new JSONObject();

            // Amount in paise
            orderRequest.put("amount", amount * 100);

            orderRequest.put("currency", "INR");

            orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

            // Optional
            orderRequest.put("payment_capture", 1);

            Order order = razorpay.orders.create(orderRequest);

            return order.toString();

        } catch (Exception e) {

            e.printStackTrace();

            return "Error : " + e.getMessage();
        }
    }
}