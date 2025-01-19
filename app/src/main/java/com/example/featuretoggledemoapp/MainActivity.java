package com.example.featuretoggledemoapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;


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

    private static final String TAG = "FeatureToggleDemo";
    private LinearLayout mainLayout;
    private TextView greetingText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        mainLayout = findViewById(R.id.main_layout);
        greetingText = findViewById(R.id.greeting_text);

        // Fetch active features
        FeatureToggle.getActiveFeatures(this, new FeatureToggle.Callback_Data<List<FeatureToggleItem>>() {
            @Override
            public void onSuccess(List<FeatureToggleItem> activeFeatures) {
                Log.d(TAG, "Active Features: " + activeFeatures);

                // Update UI based on active features
                for (FeatureToggleItem feature : activeFeatures) {
                    switch (feature.get_id()) {
                        case "814b5dda-c77b-4929-9a40-b683c56adbc6":
                            updateUI(Color.RED, "Merry Christmas!");
                            break;
                        case "d02723f6-0df7-4b94-83e3-f737a53eb146":
                            updateUI(Color.parseColor("#FF8C00"), "Happy Halloween!");
                            break;
                        case "87bc1713-1f11-44e9-a9c3-bf5333b42ae8":
                            updateUI(Color.parseColor("#ADD8E6"), "Happy New Year!");
                            break;
                        case "1b35a1e4-3af5-4422-9422-065d9797ae70":
                            updateUI(Color.parseColor("#FFB6C1"), "Happy Velntines!");
                        default:
                            Log.d(TAG, "Unknown feature: " + feature.getName());
                            break;
                    }
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Error fetching active features: " + errorMessage);
                greetingText.setText("Failed to fetch features");
            }
        });
    }

    private void updateUI(int backgroundColor, String greeting) {
        runOnUiThread(() -> {
            mainLayout.setBackgroundColor(backgroundColor);
            greetingText.setText(greeting);
        });
    }


}


















