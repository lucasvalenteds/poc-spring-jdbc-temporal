package com.example.instant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table("customer")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Customer {

    @Id
    @Column("customer_id")
    private UUID id;

    @Column("customer_name")
    private String name;

    @Column("customer_created_at")
    private Instant createdAt;
}
