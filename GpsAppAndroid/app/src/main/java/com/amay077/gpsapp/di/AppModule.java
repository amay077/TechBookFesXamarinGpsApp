package com.amay077.gpsapp.di;

import android.app.Application;
import android.content.Context;

import com.amay077.gpsapp.models.StopWatchModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;

    public AppModule(Application app) {
        context = app;
    }

    @Singleton
    @Provides
    public StopWatchModel provideStopWatch() {
        return new StopWatchModel();
    }

}