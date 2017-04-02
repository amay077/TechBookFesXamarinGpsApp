package com.amay077.gpsapp.di;

import android.app.Application;
import android.content.Context;

import com.amay077.gpsapp.api.LocationClient;
import com.amay077.gpsapp.api.LocationClientImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context _context;

    public AppModule(Application app) {
        _context = app;
    }

    @Singleton
    @Provides
    public LocationClient provideLocationClient() {
        return new LocationClientImpl(_context);
    }
}