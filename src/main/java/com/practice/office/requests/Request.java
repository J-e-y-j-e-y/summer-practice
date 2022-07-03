package com.practice.office.requests;

import com.practice.office.clients.Client;
import com.practice.office.realties.Realty;
import com.practice.office.utils.Purpose;
import com.vaadin.ui.DateField;
import lombok.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import static com.practice.office.utils.Constants.TIMESTAMP_PATTERN;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Request {
    private UUID id;
    private Purpose purpose = Purpose.BUY;
    private Client client;
    private Realty realty;
    private Timestamp dm = Timestamp.valueOf(LocalDateTime.now());
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
