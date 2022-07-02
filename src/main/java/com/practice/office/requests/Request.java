package com.practice.office.requests;

import com.practice.office.clients.Client;
import com.practice.office.realties.Realty;
import com.practice.office.utils.Purpose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Request {
    private int id;
    private Purpose purpose;
    private Client client;
    private Realty realty;
    private Timestamp dm;
}
