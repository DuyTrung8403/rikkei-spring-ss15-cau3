package com.rikkei.ss15.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"password"}) // Don't expose password
    private User user;

    @Column(nullable = false)
    private Date createdDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private BigDecimal totalMoney;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("order") // Prevent infinite recursion
    private List<OrderItem> orderItems;
}
