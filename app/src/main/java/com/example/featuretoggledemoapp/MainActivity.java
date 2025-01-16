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

        FeatureToggleItem updatedData = new FeatureToggleItem();
        updatedData.setBeginning_date("2025-02-01 00:00:00"); // Example beginning date
        updatedData.setExpiration_date("2026-12-31 23:59:59"); // Example expiration date

        FeatureToggle.updateFeatureDates(
                this, // Pass the context
                "814b5dda-c77b-4929-9a40-b683c56adbc6", // Replace with the actual feature toggle ID
                updatedData,
                new FeatureToggle.Callback_Data<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d(TAG, "Feature toggle dates updated successfully: " + data);
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e(TAG, "Error updating feature toggle dates: " + errorMessage);
                    }
                }
        );
    }
}







