package com.amay077.gpsapp.messengers;

import com.amay077.gpsapp.views.activities.RecordActivity;

public class StartActivityMessage implements Message {
    public final Class<RecordActivity> activityClass;

    public StartActivityMessage(Class<RecordActivity> activityClass) {
        this.activityClass = activityClass;
    }
}
