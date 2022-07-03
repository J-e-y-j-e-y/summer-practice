package com.practice.office.realties;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Realty {
    private UUID id;
    private String neighbourhood;
    private String address;
    private double square;
    private int roomNumber;
    private double price;
    private String cadastralNumber;


    public String getStrSquare() {
        return String.valueOf(square);
    }

    public void setStrSquare(String square) {
        this.square = Double.parseDouble(square);
    }

    public String getStrRoomNumber() {
        return String.valueOf(roomNumber);
    }

    public void setStrRoomNumber(String roomNumber) {
        this.roomNumber = Integer.parseInt(roomNumber);
    }

    public String getStrPrice() {
        return String.valueOf(price);
    }

    public void setStrPrice(String price) {
        this.price = Double.parseDouble(price);
    }
}
