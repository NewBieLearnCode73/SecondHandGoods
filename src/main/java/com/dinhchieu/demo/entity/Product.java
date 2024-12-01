package com.dinhchieu.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "description")
    private String description;

    @Column(name = "warranty")
    private String warranty;

    @Column(name = "create_at")
    private Long createAt;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "product_type", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "type_id"))
    private List<Type> typeList;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Image> imageList;
}
