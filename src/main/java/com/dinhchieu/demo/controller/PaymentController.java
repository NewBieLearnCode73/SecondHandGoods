package com.dinhchieu.demo.controller;

import com.dinhchieu.demo.dto.response.PaymentControllerResponseDTO;
import com.dinhchieu.demo.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Autowired
    private  PaymentService paymentService;

    @GetMapping("/api/v1/payment/vn-pay")
    public ResponseEntity<?> pay(HttpServletRequest request){
        return ResponseEntity.ok(paymentService.createPayment(request));
    }

    @GetMapping("/api/v1/payment/vn-pay-callback")
    public ResponseEntity<?> payCallbackHandler(HttpServletRequest request){
        String status = request.getParameter("vnp_ResponseCode");

        if(status.equals("00")){
            return ResponseEntity.status(HttpStatus.OK.value()).body(new PaymentControllerResponseDTO("00", "Success"));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new PaymentControllerResponseDTO("99", "Failed"));
        }

    }
}
