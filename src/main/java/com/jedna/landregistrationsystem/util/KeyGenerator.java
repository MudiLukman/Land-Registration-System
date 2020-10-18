package com.jedna.landregistrationsystem.util;

import java.time.LocalDate;
import java.util.Date;

public class KeyGenerator {

    public static String generateKey(){

        LocalDate today = LocalDate.now();
        int year = Integer.parseInt(String.valueOf(today.getYear()).substring(2, 4));
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        Date date = new Date();

        return "" + year + month + day + date.getHours() + date.getMinutes() + date.getSeconds();
    }

}
