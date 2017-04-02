package com.amay077.gpsapp.di;

import com.amay077.gpsapp.di.scope.ActivityScope;
import com.amay077.gpsapp.views.activities.MainActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);
}
