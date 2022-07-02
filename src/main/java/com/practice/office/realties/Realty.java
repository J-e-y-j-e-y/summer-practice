package com.practice.office.realties;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Realty {
    private int id;
    private String neighbourhood;
    private String address;
    private double square;
    private int roomNumber;
    private double price;
    private String cadastralNumber;
}
