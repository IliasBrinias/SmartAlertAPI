package com.unipi.msc.smartalertapi.Shared;

import java.util.Date;

public class Tools {
    public static Long getDaysBefore(int days){
        return new Date(new Date().getTime() - days * 24 * 3600 * 1000 ).getTime();
    }
}
