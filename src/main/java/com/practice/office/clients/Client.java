package com.practice.office.clients;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Client {
    private UUID id;
    private String name;
    private String surname;
    private String fathername;
    private String phone;
    private String email;

    @Override
    public String toString() {
        return id.toString();
    }
}
