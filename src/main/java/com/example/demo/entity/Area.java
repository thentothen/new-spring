package com.example.demo.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Area {
    private String name;
    private int id;
    private Date create_time;
    private Date updateTime;
    private String multipleNova;
    private int customers;
}
