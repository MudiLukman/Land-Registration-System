package com.jedna.landregistrationsystem.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Admin {

    private static final String USERNAME = System.getProperty("user.name");
    private static String password = "";

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static String getPassword() {
        return password;
    }

    public static String sha1(String input){
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] result = mDigest.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++)
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        return sb.toString();
    }

}
