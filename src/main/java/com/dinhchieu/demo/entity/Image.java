package com.dinhchieu.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "image")
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Lob
    @Column(name = "data", columnDefinition = "LONGTEXT")
    private String data;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
