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

    public static void getActiveFeatures(Context context, Callback_Data<List<FeatureToggleItem>> callback)
    {
        if (callback == null) {
            return;
        }

        // Fetch active features using the FeatureController
        featureController.fetchAllActiveFeatures(
                context.getPackageName(),
                new GenericCallBack<List<FeatureToggleItem>>() {
                    @Override
                    public void success(List<FeatureToggleItem> data)
                    {
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

    // Fetch all feature toggles for the current app.
    public static void getAllFeatures(Context context, Callback_Data<List<FeatureToggleItem>> callback)
    {
        if (callback == null) {
            return;
        }
        featureController.fetchAllFeatureToggles(context.getPackageName(), new GenericCallBack<List<FeatureToggleItem>>()
        {
            @Override
            public void success(List<FeatureToggleItem> data) {
                callback.onSuccess(data);
            }

            @Override
            public void error(String error) {
                // Notify the error callback with the error message
                callback.onError("Failed to fetch all features: " + error);
                Log.d("FeatureToggle", "Error: " + error);
            }
        });
    }

    //Create a new feature toggle.
    public static void createFeatureToggle(Context context, FeatureToggleItem featureToggle, Callback_Data<String> callback) {
        if (callback == null) {
            return;
        }

        // Ensure package_name is set in the featureToggle
        featureToggle.setPackage_name(context.getPackageName());

        // Create a new feature toggle using the FeatureController
        featureController.createNewFeatureToggle(
                featureToggle,
                new GenericCallBack<String>() {
                    @Override
                    public void success(String message) {
                        // Notify the success callback with the message
                        callback.onSuccess(message);
                    }

                    @Override
                    public void error(String error) {
                        // Notify the error callback with the error message
                        callback.onError("Failed to create feature toggle: " + error);
                        Log.d("FeatureToggle", "Error: " + error);
                    }
                }
        );
    }

    /**
     * Deletes a specific feature toggle for the current app.
     *
     * @param context   The Android context, used to get the package name.
     * @param featureId The ID of the feature toggle to delete.
     * @param callback  The callback to handle success or error responses.
     */
    public static void deleteFeatureToggle(Context context, String featureId, Callback_Data<String> callback) {
        if (callback == null) {
            return;
        }

        // Use the FeatureController to delete the feature toggle
        featureController.deleteFeatureToggle(
                context.getPackageName(),
                featureId,
                new GenericCallBack<String>() {
                    @Override
                    public void success(String message) {
                        // Notify the success callback with the message
                        callback.onSuccess(message);
                    }

                    @Override
                    public void error(String error) {
                        // Notify the error callback with the error message
                        callback.onError("Failed to delete feature toggle: " + error);
                        Log.d("FeatureToggle", "Error: " + error);
                    }
                }
        );
    }

    //Updates the beginning and expiration dates of a specific feature toggle for the current app.
    public static void updateFeatureDates(Context context, String featureId, FeatureToggleItem updatedData, Callback_Data<String> callback) {
        if (callback == null) {
            return;
        }


        // Use the FeatureController to update the feature toggle dates
        featureController.updateFeatureDates(
                context.getPackageName(),
                featureId,
                updatedData,
                new GenericCallBack<String>() {
                    @Override
                    public void success(String message) {
                        // Notify the success callback with the message
                        callback.onSuccess(message);
                    }

                    @Override
                    public void error(String error) {
                        // Notify the error callback with the error message
                        callback.onError("Failed to update feature toggle dates: " + error);
                        Log.d("FeatureToggle", "Error: " + error);
                    }
                }
        );
    }























}
