package com.amay077.gpsapp.views.activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amay077.gpsapp.App;
import com.amay077.gpsapp.R;
import com.amay077.gpsapp.databinding.ActivityRecordBinding;
import com.amay077.gpsapp.viewmodel.RecordViewModel;

import javax.inject.Inject;

public class RecordActivity extends AppCompatActivity {

    @Inject
    /*private final*/ RecordViewModel _viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inject by Dagger2
        final App app = (App) getApplication();
        app.getApplicationComponent().inject(this);

        ActivityRecordBinding binding =  DataBindingUtil.setContentView(this, R.layout.activity_record);
        binding.setViewModel(_viewModel);
    }
}
