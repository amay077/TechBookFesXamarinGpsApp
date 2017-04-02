package com.amay077.gpsapp.frameworks.messengers;

import com.amay077.gpsapp.views.activities.LapActivity;

public class StartActivityMessage implements Message {
    public final Class<LapActivity> activityClass;

    public StartActivityMessage(Class<LapActivity> activityClass) {
        this.activityClass = activityClass;
    }
}
