package com.practice.office.requests;

import com.practice.office.clients.Client;
import com.practice.office.realties.Realty;
import com.practice.office.utils.Purpose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static com.practice.office.utils.Constants.TIMESTAMP_PATTERN;

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
    private static SimpleDateFormat format = new SimpleDateFormat(TIMESTAMP_PATTERN);

    public String getStrPurpose() {
        return purpose.name();
    }

    public void setStrPurpose(String purpose) {
        this.purpose = Purpose.valueOf(purpose);
    }

    public String getStrDm() {
        return format.format(dm);
    }

    public void setStrDm(String dm) {
        this.dm = Timestamp.valueOf(dm);
    }
}
