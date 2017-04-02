package com.amay077.gpsapp.api;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.annimon.stream.Optional;

import javax.inject.Inject;

public class LocationClientImpl implements LocationClient, android.location.LocationListener {

    private final Context _context;
    private final LocationManager _locationManager;

    private boolean _isRunning;
    private Optional<Location> _latestLocation;
    private OnLocationChangedListener _onLocationChanged;
    private OnRunningChangeListener _onRunningChanged;

    @Override
    public boolean isRunning() {
        return _isRunning;
    }

    @Override
    public Optional<Location> getLatestLocation() {
        return _latestLocation;
    }

    @Inject
    public LocationClientImpl(Context context) {
        _context = context;
        _locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @SuppressWarnings("MissingPermission") // よい子はマネしちゃダメだよ
    @Override
    public void start() {
        _isRunning = true;
        onUpdateRunning();
        _locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @SuppressWarnings("MissingPermission") // よい子はマネしちゃダメだよ
    @Override
    public void stop() {
        _locationManager.removeUpdates(this);
        _isRunning = false;
        onUpdateRunning();
    }

    @Override
    public void setOnLocationChangeListener(OnLocationChangedListener listener) {
        _onLocationChanged = listener;
    }

    @Override
    public void setOnRunningChangeListener(OnRunningChangeListener listener) {
        _onRunningChanged = listener;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (_onLocationChanged != null) {
            _latestLocation = Optional.of(location);
            _onLocationChanged.onChanged(location);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) { }

    @Override
    public void onProviderEnabled(String s) { }

    @Override
    public void onProviderDisabled(String s) {}

    private void onUpdateRunning() {
        if (_onRunningChanged != null) {
            _onRunningChanged.onChanged(isRunning());
        }
    }
}
