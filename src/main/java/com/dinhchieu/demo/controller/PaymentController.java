package com.dinhchieu.demo.controller;

import com.dinhchieu.demo.dto.request.PaymentRequestDTO;
import com.dinhchieu.demo.dto.response.PaymentControllerResponseDTO;
import com.dinhchieu.demo.dto.response.PaymentResponseDTO;
import com.dinhchieu.demo.entity.User;
import com.dinhchieu.demo.service.PaymentService;
import com.dinhchieu.demo.service.TransactionService;
import com.dinhchieu.demo.service.UserService;
import com.dinhchieu.demo.utils.Status;
import com.dinhchieu.demo.utils.TransactionType;
import com.dinhchieu.demo.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/v1/payment/vn-pay")
    public ResponseEntity<?> pay(@RequestBody PaymentRequestDTO paymentRequestDTO, HttpServletRequest request){

        PaymentResponseDTO paymentResponseDTO = paymentService.createPayment(paymentRequestDTO,request);

        if(paymentResponseDTO.getCode().equals("00")){
            try {
                User user = userService.getUserEntityById(paymentRequestDTO.getUserId());
                String bankCode = paymentRequestDTO.getBankCode();
                Double fluctuation = (double) paymentRequestDTO.getAmount();
                URL url = new URL(paymentResponseDTO.getPaymentUrl());

                int txnref = Integer.parseInt(Objects.requireNonNull(Utils.extractParam(url, "vnp_TxnRef")));
                String createAt = Utils.extractParam(url, "vnp_CreateDate");

                transactionService.createTransaction(user, txnref, bankCode, fluctuation, TransactionType.DEPOSIT, createAt, Status.FAILURE);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new PaymentControllerResponseDTO("99", e.getMessage()));
            }
        }


        return ResponseEntity.status(HttpStatus.OK.value()).body(paymentResponseDTO);

    }

    @GetMapping("/api/v1/payment/vn-pay-callback")
    public ResponseEntity<?> payCallbackHandler(HttpServletRequest request){
        String status = request.getParameter("vnp_ResponseCode");
        int txnref = Integer.parseInt(request.getParameter("vnp_TxnRef"));
        long amount = Long.parseLong(request.getParameter("vnp_Amount"));

        if(status.equals("00")){
            try {
                transactionService.updateTransactionStatus(txnref, Status.SUCCESS);

                int userId = transactionService.getUserIdByTxnref(txnref);
                User user = userService.getUserEntityById(userId);

                userService.addBalance(user.getId(), amount / 100);


                return ResponseEntity.status(HttpStatus.OK.value()).body(new PaymentControllerResponseDTO("00", "Success"));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new PaymentControllerResponseDTO("99", e.getMessage()));
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new PaymentControllerResponseDTO("99", "Failed"));
        }

    }
}
