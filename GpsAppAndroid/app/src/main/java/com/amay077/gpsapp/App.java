package com.amay077.gpsapp;

import android.app.Application;

import com.amay077.gpsapp.di.AppComponent;
import com.amay077.gpsapp.di.AppModule;
import com.amay077.gpsapp.di.DaggerAppComponent;

public class App extends Application {

    private AppComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        applicationComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getApplicationComponent() {
        return applicationComponent;
    }
}
