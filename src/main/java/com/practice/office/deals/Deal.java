package com.practice.office.deals;

import com.practice.office.clients.Client;
import com.practice.office.realties.Realty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.practice.office.utils.Constants.TIMESTAMP_PATTERN;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Deal {
    private UUID id;
    private Client seller;
    private Client buyer;
    private Realty realty;
    private Timestamp dm = Timestamp.valueOf(LocalDateTime.now());
    private static SimpleDateFormat format = new SimpleDateFormat(TIMESTAMP_PATTERN);


    public String getStrDm() {
        return format.format(dm);
    }

    public void setStrDm(String dm) {
        this.dm = Timestamp.valueOf(dm);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
