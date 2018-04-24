package ru.toysonline.entity;

import lombok.Getter;
import lombok.Setter;
import ru.toysonline.OrderStatus;
import ru.toysonline.Pay;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "order_id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @Column(name = "address")
    private String address;

    @Column(name = "pay")
    private String pay;

    @Column(name = "cost")
    private int cost;


    @Column(name = "status")
    private String status;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "basket_id")
    private Basket basket_id;

    public Integer getCost() {
        return orderItems.stream()
                .map(oI -> oI.getItem().getPrice())
                .mapToInt(Integer::intValue)
                .sum();
    }
}

