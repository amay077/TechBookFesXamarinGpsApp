package com.amay077.gpsapp.util;

import android.location.Location;

public final class LatLonUtil {
    public static String formatDegrees(double degrees, boolean isDms) {
        if (isDms)
        {
            double tmp = degrees;
            int degree = (int)Math.floor(tmp);

            tmp = (tmp - degree) * 60;
            int minutes = (int)Math.floor(tmp);

            Double seconds = (tmp - minutes) * 60;

            return  String.format("%d度%02d分%02.3f秒", degree, minutes, seconds);
        }
        else
        {
            return String.format("%.3f秒", degrees);
        }
    }

    public static String formatLocation(Location location, boolean isDms) {
        return formatDegrees(location.getLatitude(), isDms) + "/" + formatDegrees(location.getLongitude(), isDms);
    }
}
