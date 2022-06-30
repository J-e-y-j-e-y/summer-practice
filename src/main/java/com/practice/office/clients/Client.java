package com.practice.office.clients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Client {
    private int id;
    private String name;
    private String surname;
    private String fathername;
    private String phone;
    private String email;
}
