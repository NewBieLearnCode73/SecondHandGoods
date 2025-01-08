package com.dinhchieu.demo.service.Impl;

import com.dinhchieu.demo.dao.TransactionRepository;
import com.dinhchieu.demo.entity.Transaction;
import com.dinhchieu.demo.entity.User;
import com.dinhchieu.demo.service.TransactionService;
import com.dinhchieu.demo.utils.Status;
import com.dinhchieu.demo.utils.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void createTransaction(User user, int txnref, String bankCode, Double fluctuation, TransactionType transactionType, String createAt, Status status) throws Exception {
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setTxnref(txnref);
        transaction.setBankCode(bankCode);
        transaction.setFluctuation(fluctuation);
        transaction.setTransactionType(transactionType);
        transaction.setCreateAt(createAt);
        transaction.setStatus(status);

        transactionRepository.save(transaction);
    }

    @Override
    public void updateTransactionStatus(int txnref, Status status) throws Exception {
            Optional<Transaction> transaction = Optional.ofNullable(transactionRepository.findTransactionByTxnref(txnref));

            if(transaction.isEmpty()){
                throw new Exception("Can't find transaction with txnref " + txnref);
            }

            Transaction transactionRiel = transaction.get();

            transactionRiel.setStatus(status);
            transactionRepository.save(transactionRiel);
    }

    @Override
    public int getUserIdByTxnref(int txnref) throws Exception {
        return transactionRepository.findUserIdByTxnref(txnref);
    }


}
