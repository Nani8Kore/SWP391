package com.koishop.api;

import com.koishop.entity.Payment;
import com.koishop.models.payment_model.PaymentResponse;
import com.koishop.service.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080/")
@SecurityRequirement(name = "api")
@RestController
@RequestMapping("/api/payments")
public class PaymentAPI {
    @Autowired
    private PaymentService paymentService;

    // Get all payments
    @GetMapping("list-payments")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }


    // Create new payment
    @PostMapping("add-payment")
    public ResponseEntity createPayment(@RequestBody PaymentResponse paymentResponse) {
        PaymentResponse payment = paymentService.createPayment(paymentResponse);
        return ResponseEntity.ok(payment);
    }

    // Update payment by ID
    @PutMapping("/{paymentID}")
    public ResponseEntity<Payment> updatePayment(@PathVariable int paymentID, @RequestBody Payment updatedPayment) {
        try {
            Payment payment = paymentService.updatePayment(paymentID, updatedPayment);
            return ResponseEntity.ok(payment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete payment by ID
    @DeleteMapping("/{paymentID}")
    public ResponseEntity<Void> deletePayment(@PathVariable int paymentID) {
        paymentService.deletePayment(paymentID);
        return ResponseEntity.noContent().build();
    }
}