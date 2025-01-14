package com.example.featuretogglelibrary;

import com.example.featuretogglelibrary.api.FeatureController;
import com.example.featuretogglelibrary.model.FeatureToggleItem;
import com.example.featuretogglelibrary.interfaces.GenericCallBack;

import android.content.Context; // For getPackageName
import android.util.Log; // For Log.d


import java.util.List;

public class FeatureToggle
{
    private  static FeatureController featureController=new FeatureController();
    public interface Callback_Data<T> {
        void onSuccess(T data);
        void onError(String errorMessage);
    }

    /**
     * Fetch all active feature toggles for the current app.
     *
     * @param context The Android context, used to get the package name.
     * @param callback The callback to handle the data or errors.
     */

    public static void getActiveFeatures(Context context, Callback_Data<List<FeatureToggleItem>> callback) {
        if (callback == null) {
            return;
        }

        // Fetch active features using the FeatureController
        featureController.fetchAllActiveFeatures(
                context.getPackageName(),
                new GenericCallBack<List<FeatureToggleItem>>() {
                    @Override
                    public void success(List<FeatureToggleItem> data) {
                        // Notify the success callback with the data
                        callback.onSuccess(data);
                    }

                    @Override
                    public void error(String error) {
                        // Notify the error callback with the error message
                        callback.onError("Failed to fetch active features: " + error);
                        Log.d("FeatureToggle", "Error: " + error);
                    }
                }
        );
    }









}
