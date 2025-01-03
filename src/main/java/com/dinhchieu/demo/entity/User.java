package com.dinhchieu.demo.entity;

import com.dinhchieu.demo.utils.AccountState;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "balance")
    private Float balance;

    @Column(name = "address")
    private String address;

    @Column(name = "activation")
    private Boolean activation = false;

    @Column(name = "activation_code")
    private String activationCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_state")
    private AccountState accountState;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Product> productList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "user_wishlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> wishList;

    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Follow> followers; // Danh sách người đang theo dõi user này

    @OneToMany(mappedBy = "followee", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Follow> followees; // Danh sách người user này đang theo dõi
}