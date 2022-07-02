package com.practice.office.deals;

import com.practice.office.clients.Client;
import com.practice.office.realties.Realty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Deal {
    private int id;
    private Client seller;
    private Client buyer;
    private Realty realty;
    private Timestamp dm;
}
