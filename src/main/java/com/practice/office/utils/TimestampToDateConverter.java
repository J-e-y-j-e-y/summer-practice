package com.practice.office.utils;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

@NoArgsConstructor
public class TimestampToDateConverter implements Converter<LocalDate, Timestamp> {
    @Override
    public Result<Timestamp> convertToModel(LocalDate date, ValueContext valueContext) {
        Date d = new Date();
        if(date != null)
            d = Date.from(date.atStartOfDay(ZoneOffset.systemDefault()).toInstant());
        return Result.ok(new Timestamp(d.getTime()));
    }

    @Override
    public LocalDate convertToPresentation(Timestamp timestamp, ValueContext valueContext) {
        if (timestamp == null)
            timestamp = new Timestamp(System.currentTimeMillis());

        Date value = new Date(timestamp.getTime());
        return value.toInstant().atZone(ZoneOffset.systemDefault()).toLocalDate();
    }
}
