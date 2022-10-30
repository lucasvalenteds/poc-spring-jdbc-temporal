package com.example.zoned;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;
import java.util.UUID;

@Table("vehicle")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Vehicle {

    @Id
    @Column("vehicle_id")
    private UUID id;

    @Column("vehicle_category")
    private String category;

    @Column("vehicle_created_at")
    private ZonedDateTime createdAt;
}
