package ru.toysonline.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "baskets")
public class Basket {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @OneToMany(mappedBy = "basket_id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Order> orders;
}
