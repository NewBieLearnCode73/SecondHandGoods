package com.dinhchieu.demo.dao;

import com.dinhchieu.demo.entity.Transaction;
import com.dinhchieu.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction findTransactionByTxnref(int txnref);

    @Query("SELECT t.user.id FROM Transaction t WHERE t.txnref = ?1")
    int findUserIdByTxnref(int txnref);
}
