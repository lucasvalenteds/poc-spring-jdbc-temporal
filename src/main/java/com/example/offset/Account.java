package com.example.offset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Table("account")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Account {

    @Id
    @Column("account_id")
    private UUID id;

    @Column("account_balance")
    private BigDecimal balance;

    @Column("account_created_at")
    private OffsetDateTime createdAt;
}
