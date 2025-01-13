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
import com.example.featuretogglelibrary.interfaces.CallBack_Features;

public class FeatureController {

    private static final String BASE_URL = "http://127.0.0.1:5000/"; // Replace with your server's base URL

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

    private String extractErrorMessage(Response<?> response) {
        try {
            String errorBody = response.errorBody().string();
            JsonObject errorJson = new Gson().fromJson(errorBody, JsonObject.class);
            return errorJson.has("error") ? errorJson.get("error").getAsString() : "Unknown error";
        } catch (Exception e) {
            return "Failed to parse error response.";
        }
    }

    public void fetchAllFeatureToggles(String packageName, CallBack_Features callbackFeatures)
    {
        // Create a call object for the GET request
        Call<List<FeatureToggleItem>> call = getAPI().getAllFeatureToggles(packageName);

        // Enqueue the call to make an asynchronous request
        call.enqueue(new Callback<List<FeatureToggleItem>>() {
            @Override
            public void onResponse(Call<List<FeatureToggleItem>> call, Response<List<FeatureToggleItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Pass the response body to the success callback
                    callbackFeatures.ready(response.body());
                } else {
                    // Extract error message from the response and pass it to the failure callback
                    String errorMessage = extractErrorMessage(response);
                    callbackFeatures.failed("Failed to fetch feature toggles: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<FeatureToggleItem>> call, Throwable t) {
                // Pass the throwable message to the failure callback
                callbackFeatures.failed("Error: " + t.getMessage());
            }
        });

    }




    public void fetchAllActiveFeatures(String packageName, CallBack_Features callbackFeatures)
    {
        Call<List<FeatureToggleItem>> call = getAPI().getActiveFeatureToggles(packageName);
        call.enqueue(new Callback<List<FeatureToggleItem>>() {
            @Override
            public void onResponse(Call<List<FeatureToggleItem>> call, Response<List<FeatureToggleItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callbackFeatures.ready(response.body());
                } else {
                    String errorMessage = extractErrorMessage(response);
                    callbackFeatures.failed("Failed to delete feature toggle: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<FeatureToggleItem>> call, Throwable t) {
                callbackFeatures.failed("Error: " + t.getMessage());
            }
        });
    }

    public void createNewFeatureToggle(FeatureToggleItem featureToggle, GenericCallBack genericCallBack) {
        Call<ResponseBody> call = getAPI().createFeatureToggle(featureToggle);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    genericCallBack.success("Feature created successfully!");
                } else {
                    String errorMessage = extractErrorMessage(response);
                    genericCallBack.error("Failed to delete feature toggle: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                genericCallBack.error("Error: " + t.getMessage());
            }
        });
    }

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

    public void getRecentFeatureToggles(String packageName, CallBack_Features callbackFeatures) {
        // Create a call object for the GET request
        Call<List<FeatureToggleItem>> call = getAPI().getRecentFeatureToggles(packageName);

        // Enqueue the call to make an asynchronous request
        call.enqueue(new Callback<List<FeatureToggleItem>>() {
            @Override
            public void onResponse(Call<List<FeatureToggleItem>> call, Response<List<FeatureToggleItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Pass the list of recent feature toggles to the success callback
                    callbackFeatures.ready(response.body());
                } else {
                    // Extract the error message from the response and pass it to the failure callback
                    String errorMessage = extractErrorMessage(response);
                    callbackFeatures.failed("Failed to fetch recent feature toggles: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<FeatureToggleItem>> call, Throwable t) {
                // Pass the failure message to the failure callback
                callbackFeatures.failed("Error: " + t.getMessage());
            }
        });
    }

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

    public void getActiveFeaturesInRange(
            String packageName,
            String startDate,
            String endDate,
            CallBack_Features callbackFeatures)
    {
        // Create a call object for the GET request
        Call<List<FeatureToggleItem>> call = getAPI().getActiveFeaturesInRange(packageName, startDate, endDate);

        // Enqueue the call to make an asynchronous request
        call.enqueue(new Callback<List<FeatureToggleItem>>() {
            @Override
            public void onResponse(Call<List<FeatureToggleItem>> call, Response<List<FeatureToggleItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Notify the success callback with the retrieved features
                    callbackFeatures.ready(response.body());
                } else {
                    // Extract the error message from the response and pass it to the failure callback
                    String errorMessage = extractErrorMessage(response);
                    callbackFeatures.failed("Failed to retrieve active features in range: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<FeatureToggleItem>> call, Throwable t) {
                // Notify the failure callback with the error message
                callbackFeatures.failed("Error: " + t.getMessage());
            }
        });
    }

    public void getFeatureToggleStatistics(String packageName, GenericCallBack genericCallBack)
    {



    }












}
