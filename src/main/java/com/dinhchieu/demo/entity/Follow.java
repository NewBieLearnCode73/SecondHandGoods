package com.dinhchieu.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "follow")
@Data
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "followee_id")
    private User followee;
}
