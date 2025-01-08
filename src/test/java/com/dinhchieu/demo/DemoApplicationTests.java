package com.dinhchieu.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() throws MalformedURLException {

		String paymentUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html?vnp_Amount=27000000&vnp_BankCode=NCB&vnp_Command=pay&vnp_CreateDate=20250108103146&vnp_CurrCode=VND&vnp_ExpireDate=20250108104646&vnp_IpAddr=0%3A0%3A0%3A0%3A0%3A0%3A0%3A1&vnp_Locale=vn&vnp_OrderInfo=Thanh+toan+don+hang%3A54629330&vnp_OrderType=other&vnp_ReturnUrl=http%3A%2F%2Flocalhost%3A8080%2Fapi%2Fv1%2Fpayment%2Fvn-pay-callback&vnp_TmnCode=RL0G74YB&vnp_TxnRef=02745515&vnp_Version=2.1.0&vnp_SecureHash=74d213c7f98ec3214505d85c70bfc556fc3c7af53972628c436b1f416e76ca6ad3ed77ac1e162ac59116d141b97057b864ec293146e8a12020518f9a24a3a5f7";

		URL url = new URL(paymentUrl);
		String queryParams = url.getQuery();
		String[] params = queryParams.split("&");

		List<String> paramList = Arrays.asList(params);

		String txnref = paramList.stream().filter(param -> param.contains("vnp_TxnRef")).findFirst().get().split("=")[1];

		System.out.println("txnref = " + txnref);
	}

}
