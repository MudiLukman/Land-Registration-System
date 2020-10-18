package com.jedna.landregistrationsystem.util;

import com.jedna.landregistrationsystem.model.Land;
import com.jedna.landregistrationsystem.model.LandOwner;
import com.jedna.landregistrationsystem.model.Location;
import com.jedna.landregistrationsystem.model.TitleDocs;
import com.jedna.landregistrationsystem.util.enums.*;

public class CurrentLand {

    private static Land land;

    public CurrentLand(){
        land = new Land("",
                new LandOwner("","", Gender.MALE, "", "", null, null, "", null, null),
                new Location("",0.0, 0.0),
                new TitleDocs("", null, null, null, null, null),
                null, "", Mediums.GOVERNMENT, Activities.COMMERCIAL, null, Stages.APPROVED, Status.REVOKED);
    }

    public CurrentLand(Land passedLand){
        land = passedLand;
    }

    public static Land getCurrentLand(){
        Land currentLand = land;
        land = null;
        return currentLand;
    }

}
