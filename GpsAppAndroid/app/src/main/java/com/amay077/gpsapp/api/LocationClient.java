package com.amay077.gpsapp.api;

import android.location.Location;

import com.annimon.stream.Optional;

import java.util.function.Consumer;

public interface LocationClient {
    boolean isRunning();
    Optional<Location> getLatestLocation();

    void start();
    void stop();

    void setOnLocationChangeListener(OnLocationChangedListener listener);
    void setOnRunningChangeListener(OnRunningChangeListener listener);

    interface OnLocationChangedListener {
        void onChanged(Location location);
    }

    interface OnRunningChangeListener {
        void onChanged(boolean isRunning);
    }
}
