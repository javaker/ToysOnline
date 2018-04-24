package ru.toysonline.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "items")
public class Item {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "article")
    private String article;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

//    @Column(name = "foto")
//    private String foto;

    @Column(name = "price")
    private int price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category_id;
}
