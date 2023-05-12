package com.example.application.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SWE")
@NoArgsConstructor
public class SWE extends League{
}
