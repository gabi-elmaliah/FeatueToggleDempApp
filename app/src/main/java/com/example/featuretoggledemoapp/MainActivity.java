package com.example.featuretoggledemoapp;

import android.os.Bundle;
import android.util.Log;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.featuretogglelibrary.FeatureToggle;
import com.example.featuretogglelibrary.api.FeatureController;
import com.example.featuretogglelibrary.interfaces.GenericCallBack;
import com.example.featuretogglelibrary.model.FeatureToggleItem;

import java.util.List;


public class MainActivity extends AppCompatActivity
{

    private static final String TAG = "LibraryTest";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            System.out.println("package name is :"+ this.getPackageName());

            // Test the getActiveFeatures function
            FeatureToggle.getActiveFeatures(this, new FeatureToggle.Callback_Data<List<FeatureToggleItem>>() {
                @Override
                public void onSuccess(List<FeatureToggleItem> data) {
                    // Handle the success case
                    Log.d(TAG, "Active Features fetched successfully: " + data.toString());
                }
                @Override
                public void onError(String errorMessage) {
                    // Handle the error case
                    Log.e(TAG, "Error fetching active features: " + errorMessage);
                }
            });
    }
}





