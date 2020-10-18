package com.jedna.landregistrationsystem.util;

import com.jedna.landregistrationsystem.model.LandOwner;
import com.jedna.landregistrationsystem.util.enums.UserType;

public class CurrentUser {

    public static UserType userType;
    public static LandOwner landOwner;

    public static UserType getUserType() {
        return userType;
    }

    public static void setUserType(UserType userType) {
        CurrentUser.userType = userType;
    }

    public static LandOwner getLandOwner() {
        return landOwner;
    }

    public static void setLandOwner(LandOwner landOwner) {
        CurrentUser.landOwner = landOwner;
    }
}
