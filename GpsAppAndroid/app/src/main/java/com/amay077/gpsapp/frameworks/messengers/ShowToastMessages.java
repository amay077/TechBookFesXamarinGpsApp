package com.amay077.gpsapp.frameworks.messengers;

public class ShowToastMessages implements Message {
    public final String text;

    public ShowToastMessages(String text) {
        this.text = text;
    }
}
