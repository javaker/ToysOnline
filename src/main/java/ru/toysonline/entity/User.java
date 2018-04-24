package ru.toysonline.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name")
    String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "basket_id")
    Basket basket;

    public User(String name) {
        this.name = name;
    }

    public User() {
    }
}
