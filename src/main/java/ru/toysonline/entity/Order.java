package ru.toysonline.entity;

import lombok.Getter;
import lombok.Setter;

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

    /*
    If not LAZY we receive double result, because Hibernate
    do left outer join between 3 tables: baskets, orders and orderItems.
    With LAZY we have join between two tables baskets and orders.
     */
    @OneToMany(mappedBy = "order_id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

