package com.dinhchieu.demo.service.Impl;

import com.dinhchieu.demo.config.VNPayConfig;
import com.dinhchieu.demo.dto.request.PaymentRequestDTO;
import com.dinhchieu.demo.dto.response.PaymentResponseDTO;
import com.dinhchieu.demo.service.PaymentService;
import com.dinhchieu.demo.service.UserService;
import com.dinhchieu.demo.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private VNPayConfig vnpayConfig;

    @Autowired
    private UserService userService;

    @Override
    public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO , HttpServletRequest request) {
        long amount = paymentRequestDTO.getAmount() * 100;
        String bankCode = paymentRequestDTO.getBankCode();
        int userId = paymentRequestDTO.getUserId();

        if (userService.getUserById(userId) == null || userId <= 0) {
            return new PaymentResponseDTO("99", "User not found!", null);
        }

        Map<String, String> vnpParamsMap = vnpayConfig.getVNPayConfig();
//        vnpParamsMap.put("vnp_UserID", String.valueOf(userId));
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnpayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnpayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return new PaymentResponseDTO("00", "Success" , paymentUrl);
    }
}
