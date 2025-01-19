package com.example.featuretogglelibrary.api;

import com.example.featuretogglelibrary.interfaces.FeatureApi;
import com.example.featuretogglelibrary.interfaces.GenericCallBack;
import com.example.featuretogglelibrary.model.FeatureToggleItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.featuretogglelibrary.model.FeaturesStatistics;

/**
 * The FeatureController class is responsible for interacting with the backend API
 * to manage feature toggles for the application.
 */

public class FeatureController {

    private static final String BASE_URL = "https://feature-toggle-api-mao-2102299.vercel.app/";

    /**
     * Retrieves an instance of the FeatureApi interface for making API calls.
     *
     * @return A FeatureApi instance.
     */

    private FeatureApi getAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .setLenient()
                                        .create()
                        )
                )
                .build();

        return retrofit.create(FeatureApi.class);
    }

    /**
     * Extracts error messages from a failed API response.
     *
     * @param response The Response object containing error details.
     * @return The extracted error message or a default error message if parsing fails.
     */

    private String extractErrorMessage(Response<?> response) {
        try {
            String errorBody = response.errorBody().string();
            JsonObject errorJson = new Gson().fromJson(errorBody, JsonObject.class);
            return errorJson.has("error") ? errorJson.get("error").getAsString() : "Unknown error";
        } catch (Exception e) {
             return "Failed to parse error response.";
        }
    }

    /**
     * Fetches all feature toggles for the given package.
     *
     * @param packageName The package name to retrieve feature toggles for.
     * @param callbackFeatures The callback to handle the result or errors.
     */

    public void fetchAllFeatureToggles(String packageName, GenericCallBack<List<FeatureToggleItem>> callbackFeatures)
    {
        // Create a call object for the GET request
        Call<List<FeatureToggleItem>> call = getAPI().getAllFeatureToggles(packageName);

        // Enqueue the call to make an asynchronous request
        call.enqueue(new Callback<List<FeatureToggleItem>>() {
            @Override
            public void onResponse(Call<List<FeatureToggleItem>> call, Response<List<FeatureToggleItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Pass the response body to the success callback
                    callbackFeatures.success(response.body());
                } else {
                    // Extract error message from the response and pass it to the failure callback
                    String errorMessage = extractErrorMessage(response);
                    callbackFeatures.error("Failed to fetch feature toggles: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<FeatureToggleItem>> call, Throwable t) {
                // Pass the throwable message to the failure callback
                callbackFeatures.error("Error: " + t.getMessage());
            }
        });

    }



    /**
     * Fetches all active feature toggles for the given package.
     *
     * @param packageName The package name to retrieve active feature toggles for.
     * @param callbackFeatures The callback to handle the result or errors.
     */
    public void fetchAllActiveFeatures(String packageName, GenericCallBack<List<FeatureToggleItem>> callbackFeatures)
    {
        Call<List<FeatureToggleItem>> call = getAPI().getActiveFeatureToggles(packageName);
        call.enqueue(new Callback<List<FeatureToggleItem>>() {
            @Override
            public void onResponse(Call<List<FeatureToggleItem>> call, Response<List<FeatureToggleItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callbackFeatures.success(response.body());
                } else {
                    String errorMessage = extractErrorMessage(response);
                    callbackFeatures.error("Failed to fetch active feature toggle: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<FeatureToggleItem>> call, Throwable t) {
                callbackFeatures.error("Error: " + t.getMessage());
            }
        });
    }

/**
 * Sends a request to create a new feature toggle in the backend.
 *
 * @param featureToggle    The {@link FeatureToggleItem} object containing the details of the new feature toggle to be created.
 * @param genericCallBack  A callback to handle success or error responses from the server.
 */


    public void createNewFeatureToggle(FeatureToggleItem featureToggle, GenericCallBack genericCallBack) {
        Call<ResponseBody> call = getAPI().createFeatureToggle(featureToggle);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    genericCallBack.success("Feature created successfully!");
                } else {
                    String errorMessage = extractErrorMessage(response);
                    genericCallBack.error("Failed to create feature toggle: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                genericCallBack.error("Error: " + t.getMessage());
            }
        });
    }

/**
 * Sends a request to delete a specific feature toggle from the backend.
 *
 * @param packageName     The name of the package to which the feature toggle belongs.
 * @param featureId       The unique ID of the feature toggle to be deleted.
 * @param genericCallBack A callback to handle success or error responses from the server.
 */

    public void deleteFeatureToggle(String packageName, String featureId, GenericCallBack genericCallBack) {
        // Create a call object for the DELETE request
        Call<ResponseBody> call = getAPI().deleteFeatureToggle(packageName, featureId);

        // Enqueue the call to make an asynchronous request
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Notify the success callback with a success message
                    genericCallBack.success("Feature toggle deleted successfully.");
                } else {
                    String errorMessage = extractErrorMessage(response);
                    genericCallBack.error("Failed to delete feature toggle: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Notify the failure callback with the error message
                genericCallBack.error("Error: " + t.getMessage());
            }
        });
    }

    /**
     * Updates the beginning and expiration dates of a specific feature toggle.
     *
     * @param packageName     The name of the package to which the feature toggle belongs.
     * @param featureId       The unique ID of the feature toggle to be updated.
     * @param updatedData     A {@link FeatureToggleItem} object containing the new beginning and expiration dates.
     * @param genericCallBack A callback to handle success or error responses from the server.
     *
     * Example usage:
     * <pre>
     * FeatureToggleItem updatedData = new FeatureToggleItem();
     * updatedData.setBeginning_date("2025-02-01 00:00:00");
     * updatedData.setExpiration_date("2025-12-31 23:59:59");
     *
     * featureController.updateFeatureDates(
     *     "com.example.myapp", // Replace with the actual package name
     *     "814b5dda-c77b-4929-9a40-b683c56adbc6", // Replace with the actual feature ID
     *     updatedData,
     *     new GenericCallBack<String>() {
     *         @Override
     *         public void success(String message) {
     *             System.out.println("Feature dates updated successfully: " + message);
     *         }
     *
     *         @Override
     *         public void error(String error) {
     *             System.err.println("Error updating feature dates: " + error);
     *         }
     *     }
     * );
     * </pre>
     */
    public void updateFeatureDates(String packageName, String featureId, FeatureToggleItem updatedData,
                                   GenericCallBack genericCallBack)
    {
        // Create a call object for the PUT request
        Call<ResponseBody> call = getAPI().updateFeatureDates(packageName, featureId, updatedData);

        // Enqueue the call to make an asynchronous request
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Notify the success callback with a success message
                    genericCallBack.success("Feature toggle dates updated successfully.");
                } else {
                    // Extract error message from the response and pass it to the failure callback
                    String errorMessage = extractErrorMessage(response);
                    genericCallBack.error("Failed to update feature toggle dates: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Notify the failure callback with the error message
                genericCallBack.error("Error: " + t.getMessage());
            }
        });
    }


/**
 * Retrieves all feature toggles created in the last 30 days for a specific package.
 *
 * @param packageName     The name of the package for which recent feature toggles are to be fetched.
 * @param callbackFeatures A callback to handle the success or error responses.
 */
    public void getRecentFeatureToggles(String packageName, GenericCallBack<List<FeatureToggleItem>> callbackFeatures) {
        // Create a call object for the GET request
        Call<List<FeatureToggleItem>> call = getAPI().getRecentFeatureToggles(packageName);

        // Enqueue the call to make an asynchronous request
        call.enqueue(new Callback<List<FeatureToggleItem>>() {
            @Override
            public void onResponse(Call<List<FeatureToggleItem>> call, Response<List<FeatureToggleItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Pass the list of recent feature toggles to the success callback
                    callbackFeatures.success(response.body());
                } else {
                    // Extract the error message from the response and pass it to the failure callback
                    String errorMessage = extractErrorMessage(response);
                    callbackFeatures.error("Failed to fetch recent feature toggles: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<FeatureToggleItem>> call, Throwable t) {
                // Pass the failure message to the failure callback
                callbackFeatures.error("Error: " + t.getMessage());
            }
        });
    }

    /**
     * Updates the name and descriotion of a specific feature toggle for a package.
     *
     * @param packageName   The name of the package containing the feature toggle.
     * @param featureId     The unique identifier of the feature toggle to be updated.
     * @param updatedData   The updated feature toggle data, including changes to be applied.
     * @param genericCallBack A callback to handle the success or error responses.
     */
    public void updateFeatureInfo(String packageName, String featureId,
                                  FeatureToggleItem updatedData, GenericCallBack genericCallBack)
    {
        // Create a call object for the PUT request
        Call<ResponseBody> call = getAPI().updateFeatureInfo(packageName, featureId, updatedData);

        // Enqueue the call to make an asynchronous request
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Notify the success callback with a success message
                    genericCallBack.success("Feature information updated successfully.");
                } else {
                    // Extract the error message from the response and pass it to the failure callback
                    String errorMessage = extractErrorMessage(response);
                    genericCallBack.error("Failed to update feature information: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Notify the failure callback with the error message
                genericCallBack.error("Error: " + t.getMessage());
            }
        });
    }

/**
 * Retrieves all active feature toggles for a specific package within a specified date range.
 *
 * @param packageName    The name of the package containing the feature toggles.
 * @param startDate      The start date of the range in the format "YYYY-MM-DD HH:MM:SS".
 * @param endDate        The end date of the range in the format "YYYY-MM-DD HH:MM:SS".
 * @param callbackFeatures A callback to handle the success or error responses,
 *                         providing a list of active feature toggles or an error message.
 */

    public void getActiveFeaturesInRange(String packageName, String startDate, String endDate,
            GenericCallBack<List<FeatureToggleItem>> callbackFeatures)
    {
        // Create a call object for the GET request
        Call<List<FeatureToggleItem>> call = getAPI().getActiveFeaturesInRange(packageName, startDate, endDate);

        // Enqueue the call to make an asynchronous request
        call.enqueue(new Callback<List<FeatureToggleItem>>() {
            @Override
            public void onResponse(Call<List<FeatureToggleItem>> call, Response<List<FeatureToggleItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Notify the success callback with the retrieved features
                    callbackFeatures.success(response.body());
                } else {
                    // Extract the error message from the response and pass it to the failure callback
                    String errorMessage = extractErrorMessage(response);
                    callbackFeatures.error("Failed to retrieve active features in range: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<FeatureToggleItem>> call, Throwable t) {
                // Notify the failure callback with the error message
                callbackFeatures.error("Error: " + t.getMessage());
            }
        });
    }


    /**
     * Fetches statistics about feature toggles for a specific package.
     *
     * @param packageName    The name of the package for which the statistics are to be retrieved.
     * @param genericCallBack A callback to handle the success or error responses.
     *                        On success, it provides a {@link FeaturesStatistics} object containing the statistics.
     *
     * Example usage:
     * <pre>
     * featureController.getFeatureToggleStatistics(
     *     "com.example.myapp", // Replace with your package name
     *     new GenericCallBack<FeaturesStatistics>() {
     *         @Override
     *         public void success(FeaturesStatistics statistics) {
     *             System.out.println("Feature Statistics:");
     *             System.out.println("Total Toggles: " + statistics.getTotalToggles());
     *             System.out.println("Active Toggles: " + statistics.getActiveToggles());
     *             System.out.println("Inactive Toggles: " + statistics.getInactiveToggles());
     *         }
     *
     *         @Override
     *         public void error(String errorMessage) {
     *             System.err.println("Error fetching statistics: " + errorMessage);
     *         }
     *     }
     * );
     * </pre>
     */

    public void getFeatureToggleStatistics(String packageName, GenericCallBack genericCallBack)
    {

        // Create a call object for the GET request
        Call<ResponseBody> call = getAPI().getFeatureToggleStatistics(packageName);

        // Enqueue the call to make an asynchronous request
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // Parse the response JSON into the FeatureStatistics object
                        String responseBody = response.body().string();
                        Gson gson = new Gson();
                        FeaturesStatistics statistics = gson.fromJson(responseBody, FeaturesStatistics.class);

                        // Pass the success message with the statistics data
                        genericCallBack.success(statistics);
                    } catch (Exception e) {
                        // Handle any parsing or I/O errors
                        genericCallBack.error("Failed to parse statistics response.");
                    }
                } else {
                    // Extract the error message from the response
                    String errorMessage = extractErrorMessage(response);
                    genericCallBack.error("Failed to fetch statistics: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Notify the failure callback with the error message
                genericCallBack.error("Error: " + t.getMessage());
            }
        });



    }

/**
 * Fetches all feature toggles for a specific package that are active on a given date.
 *
 * @param packageName     The name of the package for which feature toggles are to be retrieved.
 * @param date            The specific date in the format "YYYY-MM-DD", used to filter active toggles.
 * @param genericCallBack A callback to handle the success or error responses.
 *                        On success, it provides a list of {@link FeatureToggleItem} objects.
 */
    public void getFeatureTogglesByDate(String packageName, String date, GenericCallBack<List<FeatureToggleItem>> genericCallBack
    ) {
        // Create a call object for the GET request
        Call<List<FeatureToggleItem>> call = getAPI().getFeatureTogglesByDate(packageName, date);

        // Enqueue the call to make an asynchronous request
        call.enqueue(new Callback<List<FeatureToggleItem>>() {
            @Override
            public void onResponse(Call<List<FeatureToggleItem>> call, Response<List<FeatureToggleItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Pass the retrieved data to the success callback
                    genericCallBack.success(response.body());
                } else {
                    // Extract the error message from the response
                    String errorMessage = extractErrorMessage(response);
                    genericCallBack.error("Failed to fetch feature toggles by date: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<FeatureToggleItem>> call, Throwable t) {
                // Notify the failure callback with the error message
                genericCallBack.error("Error: " + t.getMessage());
            }
        });
    }





/**
 * Deletes all feature toggles for a specific package.
 *
 * @param packageName     The name of the package whose feature toggles are to be deleted.
 * @param genericCallBack A callback to handle the result of the operation.
 *                        On success, it provides a success message as a {@link String}.
 */



    public void deleteAllFeatureToggles(String packageName, GenericCallBack<String> genericCallBack) {
        // Create a call object for the DELETE request
        Call<ResponseBody> call = getAPI().deleteAllFeatureToggles(packageName);

        // Enqueue the call to make an asynchronous request
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        // Extract the success message from the response
                        String message =response.body().string() ;
                        genericCallBack.success(message);
                    } catch (Exception e) {
                        genericCallBack.error("Failed to parse server response.");
                    }
                } else {
                    // Extract the error message from the response
                    String errorMessage = extractErrorMessage(response);
                    genericCallBack.error("Failed to delete feature toggles: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Notify the failure callback with the error message
                genericCallBack.error("Error: " + t.getMessage());
            }
        });
    }








}
