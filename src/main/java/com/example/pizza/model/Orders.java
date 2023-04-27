package com.example.pizza.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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
public class Orders {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orders")
    @ToString.Exclude
    @JsonIgnore
    List<OrderInner> orderInnerList;

    String nameForYour;

    BigDecimal allPrice;

    LocalDateTime time;

    Boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Orders orders = (Orders) o;
        return getId() != null && Objects.equals(getId(), orders.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
