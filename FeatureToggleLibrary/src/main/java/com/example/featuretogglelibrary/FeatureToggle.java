package com.example.featuretogglelibrary;

import com.example.featuretogglelibrary.api.FeatureController;
import com.example.featuretogglelibrary.model.FeatureToggleItem;
import com.example.featuretogglelibrary.interfaces.GenericCallBack;
import com.example.featuretogglelibrary.model.FeaturesStatistics;

import android.content.Context;
import android.util.Log;


import java.util.List;


/**
 * The FeatureToggle class provides utility methods to interact with feature toggle APIs.
 * It allows applications to manage and fetch feature toggles dynamically.
 */

public class FeatureToggle
{
    private  static FeatureController featureController=new FeatureController();
    public interface Callback_Data<T> {
        void onSuccess(T data);
        void onError(String errorMessage);
    }

    /**
     * Fetches all active feature toggles for the package.
     *
     * @param context  The Android context, used to get the package name.
     * @param callback The callback to handle the list of active feature toggles or errors.
     *
     * Example:
     * <pre>
     * FeatureToggle.getActiveFeatures(context, new FeatureToggle.Callback_Data<List<FeatureToggleItem>>() {
     *     @Override
     *     public void onSuccess(List<FeatureToggleItem> activeFeatures) {
     *         for (FeatureToggleItem feature : activeFeatures) {
     *             Log.d("ActiveFeature", "Feature: " + feature.getName());
     *         }
     *     }
     *
     *     @Override
     *     public void onError(String errorMessage) {
     *         Log.e("ActiveFeatureError", "Error fetching active features: " + errorMessage);
     *     }
     * });
     * </pre>
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

    /**
     * Fetch all feature toggles for the current application, including inactive ones.
     *
     * @param context  The Android context, used to retrieve the package name.
     * @param callback The callback to handle the list of all feature toggles or errors.
     */
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

    /**
     * Create a new feature toggle.
     *
     * @param context       The Android context, used to retrieve the package name.
     * @param featureToggle The feature toggle object containing the details to be created.
     * @param callback      The callback to handle the result of the operation or errors.
     *
     * Example usage:
     * <pre>
     * FeatureToggleItem newFeatureToggle = new FeatureToggleItem();
     * newFeatureToggle.setName("dark_mode");
     * newFeatureToggle.setDescription("Enable dark mode for the application interface.");
     * newFeatureToggle.setBeginning_date("2025-01-15 00:00:00");
     * newFeatureToggle.setExpiration_date("2025-12-31 23:59:59");
     *
     * FeatureToggle.createFeatureToggle(context, newFeatureToggle, new FeatureToggle.Callback_Data<String>() {
     *     @Override
     *     public void onSuccess(String message) {
     *         Log.d("CreateFeature", "Feature toggle created successfully: " + message);
     *     }
     *
     *     @Override
     *     public void onError(String errorMessage) {
     *         Log.e("CreateFeatureError", "Error creating feature toggle: " + errorMessage);
     *     }
     * });
     * </pre>
     */
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
     * Deletes a specific feature toggle for the sepcified package.
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

    /**
     * Updates the beginning and expiration dates of a specific feature toggle.
     *
     * @param context    The Android context, used to retrieve the package name.
     * @param featureId  The unique ID of the feature toggle to update.
     * @param updatedData An instance of {@link FeatureToggleItem} containing the updated dates.
     * @param callback   The callback to handle the result of the operation or errors.
     */
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

    /**
     * fetch all feature toggles created in the last 30 days for a specific package.
     *
     * @param context The Android context, used to get the package name.
     * @param callback The callback to handle the list of recent feature toggles or errors.
     */

    public static void getRecentFeatureToggles(Context context, Callback_Data<List<FeatureToggleItem>> callback) {
        if (callback == null) {
            return;
        }

        // Fetch recent feature toggles using the FeatureController
        featureController.getRecentFeatureToggles(
                context.getPackageName(),
                new GenericCallBack<List<FeatureToggleItem>>()
                {
                    @Override
                    public void success(List<FeatureToggleItem> data) {
                        // Notify the success callback with the list of recent feature toggles
                        callback.onSuccess(data);
                    }

                    @Override
                    public void error(String error) {
                        // Notify the error callback with the error message
                        callback.onError("Failed to fetch recent feature toggles: " + error);
                        Log.d("FeatureToggle", "Error: " + error);
                    }
                }
        );




    }

    /**
     * Update the name or description of a feature toggle
     *
     * @param context     The Android context, used to get the package name.
     * @param featureId   The ID of the feature toggle to update.
     * @param updatedData The updated information for the feature toggle.
     * @param callback    The callback to handle the success or error responses.
     */

    public static void updateFeatureInfo(Context context, String featureId, FeatureToggleItem updatedData, Callback_Data<String> callback) {
        if (callback == null) {
            return;
        }

        // Update the feature toggle information using the FeatureController
        featureController.updateFeatureInfo(
                context.getPackageName(),
                featureId,
                updatedData,
                new GenericCallBack<String>() {
                    @Override
                    public void success(String message) {
                        // Notify the success callback with the success message
                        callback.onSuccess(message);
                    }

                    @Override
                    public void error(String error) {
                        // Notify the error callback with the error message
                        callback.onError("Failed to update feature information: " + error);
                        Log.d("FeatureToggle", "Error: " + error);
                    }
                }
        );
    }

    /**
     * Fetch all active feature toggles for the current app within a specific date range.
     *
     * @param context The Android context, used to get the package name.
     * @param startDate The start date of the range (format: YYYY-MM-DD).
     * @param endDate The end date of the range (format: YYYY-MM-DD).
     * @param callback The callback to handle success or error responses.
     */
    public static void getActiveFeaturesInRange(Context context, String startDate, String endDate,
            Callback_Data<List<FeatureToggleItem>> callback) {
        if (callback == null) {
            return;
        }

        featureController.getActiveFeaturesInRange(
                context.getPackageName(),
                startDate,
                endDate,
                new GenericCallBack<List<FeatureToggleItem>>() {
                    @Override
                    public void success(List<FeatureToggleItem> data) {
                        // Notify the success callback with the data
                        callback.onSuccess(data);
                    }

                    @Override
                    public void error(String error) {
                        // Notify the error callback with the error message
                        callback.onError("Failed to fetch active features in range: " + error);
                        Log.d("FeatureToggle", "Error: " + error);
                    }
                }
        );


    }

    /**
     * Fetch feature toggle statistics for the current app.
     *
     * @param context The Android context, used to get the package name.
     * @param callback The callback to handle success or error responses.
     *
     * Example usage:
     * <pre>
     * FeatureToggle.getFeatureToggleStatistics(context, new FeatureToggle.Callback_Data<FeaturesStatistics>() {
     *     @Override
     *     public void onSuccess(FeaturesStatistics data) {
     *         // Handle the successful response
     *         Log.d("FeatureToggle", "Total Features: " + data.getTotalFeatures());
     *         Log.d("FeatureToggle", "Active Features: " + data.getActiveFeatures());
     *     }
     *
     *     @Override
     *     public void onError(String errorMessage) {
     *         // Handle the error response
     *         Log.e("FeatureToggle", "Error fetching statistics: " + errorMessage);
     *     }
     * });
     * </pre>
     */

    public static void getFeatureToggleStatistics(Context context, Callback_Data<FeaturesStatistics> callback) {
        if (callback == null) {
            return;
        }

        // Fetch statistics using the FeatureController
        featureController.getFeatureToggleStatistics(
                context.getPackageName(),
                new GenericCallBack<FeaturesStatistics>() {
                    @Override
                    public void success(FeaturesStatistics data) {
                        // Notify the success callback with the statistics data
                        callback.onSuccess(data);
                    }

                    @Override
                    public void error(String error) {
                        // Notify the error callback with the error message
                        callback.onError("Failed to fetch feature toggle statistics: " + error);
                        Log.d("FeatureToggle", "Error: " + error);
                    }
                }
        );
    }






        /**
         * Fetch all feature toggles active on a specific date for the specified package.
         *
         * @param context The Android context, used to get the package name.
         * @param date The date for which feature toggles are to be fetched, in format YYYY-MM-DD.
         * @param callback The callback to handle the data or errors.
         */

        public static void getFeatureTogglesByDate(Context context, String date, Callback_Data<List<FeatureToggleItem>> callback) {
            if (callback == null) {
                return;
            }

            // Fetch feature toggles by date using the FeatureController
            featureController.getFeatureTogglesByDate(
                    context.getPackageName(),
                    date,
                    new GenericCallBack<List<FeatureToggleItem>>() {
                        @Override
                        public void success(List<FeatureToggleItem> data) {
                            // Notify the success callback with the data
                            callback.onSuccess(data);
                        }

                        @Override
                        public void error(String error) {
                            // Notify the error callback with the error message
                            callback.onError("Failed to fetch feature toggles by date: " + error);
                            Log.d("FeatureToggle", "Error: " + error);
                        }
                    }
            );
        }


    /**
     * Delete all feature toggles for the current app.
     *
     * @param context The Android context, used to get the package name.
     * @param callback The callback to handle success or error responses.
     */
    public static void deleteAllFeatureToggles(
            Context context,
            Callback_Data<String> callback) {
        if (callback == null) {
            return;
        }

        // Delete all feature toggles using the FeatureController
        featureController.deleteAllFeatureToggles(
                context.getPackageName(),
                new GenericCallBack<String>() {
                    @Override
                    public void success(String message) {
                        // Notify the success callback with the message
                        callback.onSuccess(message);
                    }

                    @Override
                    public void error(String error) {
                        // Notify the error callback with the error message
                        callback.onError("Failed to delete all feature toggles: " + error);
                        Log.d("FeatureToggle", "Error: " + error);
                    }
                }
        );
    }

}
