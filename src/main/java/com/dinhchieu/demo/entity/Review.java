package com.dinhchieu.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "seller_id" , nullable = false)
    private int sellerId;

    @Column(name = "buyer_id", nullable = false)
    private int buyerId;

    @Column(name = "rating")
    private int rating;

    @Column(name = "commnent")
    private String commnent;

    @Column(name = "create_at")
    private Long createAt;

    @OneToOne(mappedBy = "review")
    private Product product;
}
