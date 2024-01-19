package com.Code.Banking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class Transaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Long accountId;
    private double creditAmount;
    private double debitAmount;
    private LocalDateTime updatedAt;
    private double balance;
}
