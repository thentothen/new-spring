package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DictionaryUse {
    private Long id;
    private String desc;
    private String attr;
    private String code;
    private String value1;
    private String value2;
    private String value3;
    private String value4;
    private String value5;
}
