package ru.toysonline.entity;

import lombok.Getter;
import lombok.Setter;
import ru.toysonline.entity.Item;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "orderitem")
public class OrderItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Order order_id;

    public int getPrice(){
        return item.getPrice() * quantity;
    }
}
