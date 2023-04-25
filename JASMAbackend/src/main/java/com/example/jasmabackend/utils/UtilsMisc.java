package com.example.jasmabackend.utils;

import org.ocpsoft.prettytime.PrettyTime;

import java.sql.Timestamp;
import java.util.Date;

public class UtilsMisc {
    public static String getSinceCreatedString(Timestamp createdAt) {
        // get time since course has been created in pretty format:
        PrettyTime t = new PrettyTime(new Date(System.currentTimeMillis()));
        return t.format(new Date(createdAt.getTime()));
    }

    public Timestamp getTimestamp() {
        Timestamp reviewCreatedAt = new Timestamp(System.currentTimeMillis());
        return reviewCreatedAt;
    }
}
