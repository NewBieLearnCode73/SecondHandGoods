package com.dinhchieu.demo.entity;

import com.dinhchieu.demo.utils.Status;
import com.dinhchieu.demo.utils.TransactionType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "transaction")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "fluctuation")
    private Double fluctuation;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "create_at")
    private String createAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

}
