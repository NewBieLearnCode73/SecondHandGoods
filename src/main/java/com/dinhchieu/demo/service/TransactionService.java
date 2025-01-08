package com.dinhchieu.demo.service;

import com.dinhchieu.demo.entity.Transaction;
import com.dinhchieu.demo.entity.User;
import com.dinhchieu.demo.utils.Status;
import com.dinhchieu.demo.utils.TransactionType;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface TransactionService {
    void createTransaction(User user, int txnref, String bankCode, Double fluctuation, TransactionType transactionType, String createAt, Status status) throws Exception;
    void updateTransactionStatus(int transactionId, Status status) throws Exception;
    int getUserIdByTxnref(int txnref) throws Exception;
}
