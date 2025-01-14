package com.example.featuretogglelibrary.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.example.featuretogglelibrary.model.FeatureToggleItem;


import java.util.List;

public interface FeatureApi {

    // 1. Create a new feature toggle
    @POST("feature-toggle")
    Call<ResponseBody> createFeatureToggle(@Body FeatureToggleItem featureToggleItem);
    // 2. Get all feature toggles for a package
    @GET("feature-toggles/{package_name}")
    Call<List<FeatureToggleItem>> getAllFeatureToggles(@Path("package_name") String packageName);

    // Get all active feature toggles for a package
    @GET("feature-toggles/{package_name}/active")
    Call<List<FeatureToggleItem>> getActiveFeatureToggles(@Path("package_name") String packageName);

    // Delete a specific feature toggle by ID
    @DELETE("feature-toggles/{package_name}/{feature_id}")
    Call<ResponseBody> deleteFeatureToggle(@Path("package_name") String packageName, @Path("feature_id") String featureId);

    // Update beginning and expiration dates of a feature toggle
    @PUT("feature-toggles/{package_name}/{feature_id}/update-dates")
    Call<ResponseBody> updateFeatureDates(
            @Path("package_name") String packageName,
            @Path("feature_id") String featureId,
            @Body FeatureToggleItem updatedData
    );

    // Get recently created feature toggles for a package (last 30 days)
    @GET("feature-toggles/{package_name}/recent")
    Call<List<FeatureToggleItem>> getRecentFeatureToggles(@Path("package_name") String packageName);

    // Update name and description of a feature toggle
    @PUT("feature-toggles/{package_name}/{feature_id}/update-info")
    Call<ResponseBody> updateFeatureInfo(
            @Path("package_name") String packageName,
            @Path("feature_id") String featureId,
            @Body FeatureToggleItem updatedData
    );

    // Get active feature toggles within a specific date range for a package
    @GET("feature-toggles/{package_name}/active-in-range")
    Call<List<FeatureToggleItem>> getActiveFeaturesInRange(
            @Path("package_name") String packageName,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate
    );

    // Get statistics for a package
    @GET("feature-toggles/{package_name}/statistics")
    Call<ResponseBody> getFeatureToggleStatistics(@Path("package_name") String packageName);

    // Get all active feature toggles by a specific date
    @GET("feature-toggles/{package_name}/by-date")
    Call<List<FeatureToggleItem>> getFeatureTogglesByDate(
            @Path("package_name") String packageName,
            @Query("date") String date
    );

    // Delete all feature toggles for a package
    @DELETE("feature-toggles/{package_name}")
    Call<ResponseBody> deleteAllFeatureToggles(@Path("package_name") String packageName);



}
