package com.practice.office.clients;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Client {
    private int id;
    private String name;
    private String surname;
    private String fathername;
    private String phone;
    private String email;
}
