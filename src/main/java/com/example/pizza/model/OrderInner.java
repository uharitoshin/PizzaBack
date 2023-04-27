package com.example.pizza.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table
@Entity
@FieldDefaults(level = PRIVATE)
public class OrderInner {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String productName;

    Integer count;

    BigDecimal price;

    Boolean enabled;

    // в рамках какого заказа существует этот вложенный заказ
    @ManyToOne
    @JoinColumn(name = "ORDERS_ID")
    @JsonIgnore
    Orders orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderInner that = (OrderInner) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
