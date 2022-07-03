package com.practice.office.clients;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Client {
    private UUID id;
    private String name;
    private String surname;
    private String fathername;
    private String phone;
    private String email;
}
