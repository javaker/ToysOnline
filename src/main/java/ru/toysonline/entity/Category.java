package ru.toysonline.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categorys")
public class Category {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "category_id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Item> items;

    @Column(name = "name")
    private String name;

    //    private List<Category> listCat;
}