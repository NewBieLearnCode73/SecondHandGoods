package com.dinhchieu.demo.service;

import com.dinhchieu.demo.dto.request.PaymentRequestDTO;
import com.dinhchieu.demo.dto.response.PaymentResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO , HttpServletRequest request);
}
