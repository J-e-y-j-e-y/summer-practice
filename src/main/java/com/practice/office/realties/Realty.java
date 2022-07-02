package com.practice.office.realties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Realty {
    private int id;
    private String neighbourhood;
    private String address;
    private double square;
    private int roomNumber;
    private double price;
    private String cadastralNumber;
}
