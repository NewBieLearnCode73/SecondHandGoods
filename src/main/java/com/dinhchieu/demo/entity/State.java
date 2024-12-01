package com.dinhchieu.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.EnableMBeanExport;

import java.util.List;

@Entity
@Data
@Table(name = "state")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "state", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Product> productList;
}
