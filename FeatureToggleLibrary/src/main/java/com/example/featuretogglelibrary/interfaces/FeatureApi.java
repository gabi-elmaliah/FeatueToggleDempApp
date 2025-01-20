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

/**
 * Interface defining API endpoints for managing feature toggles.
 */

public interface FeatureApi {

    /**
     * Creates a new feature toggle.
     *
     * @param featureToggleItem The {@link com.example.featuretogglelibrary.model.FeatureToggleItem} object containing the details of the feature toggle
     *                          to be created, including its name, description, beginning and expiration dates,
     *                          and package name.
     * @return A {@link Call} object to execute the request asynchronously or synchronously.
     *         The response contains a ResponseBody that holds the server's response message.
     */
    @POST("feature-toggle")
    Call<ResponseBody> createFeatureToggle(@Body FeatureToggleItem featureToggleItem);


    /**
     * Retrieves all feature toggles for a specified package.
     *
     * @param packageName The name of the package for which to retrieve feature toggles.
     * @return A {@link Call} object containing a list of {@link com.example.featuretogglelibrary.model.FeatureToggleItem} objects.
     */
    @GET("feature-toggles/{package_name}")
    Call<List<FeatureToggleItem>> getAllFeatureToggles(@Path("package_name") String packageName);

    /**
     * Retrieves all active feature toggles for a specified package.
     *
     * @param packageName The name of the package for which to retrieve active feature toggles.
     * @return A {@link Call} object containing a list of {@link com.example.featuretogglelibrary.model.FeatureToggleItem} objects.
     */
    @GET("feature-toggles/{package_name}/active")
    Call<List<FeatureToggleItem>> getActiveFeatureToggles(@Path("package_name") String packageName);

    /**
     * Deletes a specific feature toggle by its ID.
     *
     * @param packageName The name of the package containing the feature toggle.
     * @param featureId   The ID of the feature toggle to be deleted.
     * @return A {@link Call} object for the API response.
     */
    @DELETE("feature-toggles/{package_name}/{feature_id}")
    Call<ResponseBody> deleteFeatureToggle(@Path("package_name") String packageName, @Path("feature_id") String featureId);

    /**
     * Updates the beginning and expiration dates of a specific feature toggle.
     *
     * @param packageName The name of the package containing the feature toggle.
     * @param featureId   The ID of the feature toggle to be updated.
     * @param updatedData The updated feature toggle data.
     * @return A {@link Call} object for the API response.
     */
    @PUT("feature-toggles/{package_name}/{feature_id}/update-dates")
    Call<ResponseBody> updateFeatureDates(
            @Path("package_name") String packageName,
            @Path("feature_id") String featureId,
            @Body FeatureToggleItem updatedData
    );

    /**
     * Retrieves all feature toggles created in the last 30 days for a specified package.
     *
     * @param packageName The name of the package for which to retrieve recent feature toggles.
     * @return A {@link Call} object containing a list of {@link com.example.featuretogglelibrary.model.FeatureToggleItem} objects.
     */
    @GET("feature-toggles/{package_name}/recent")
    Call<List<FeatureToggleItem>> getRecentFeatureToggles(@Path("package_name") String packageName);

    /**
     * Updates the name and description of a specific feature toggle.
     *
     * @param packageName The name of the package containing the feature toggle.
     * @param featureId   The ID of the feature toggle to be updated.
     * @param updatedData The updated feature toggle data.
     * @return A {@link Call} object for the API response.
     */
    @PUT("feature-toggles/{package_name}/{feature_id}/update-info")
    Call<ResponseBody> updateFeatureInfo(
            @Path("package_name") String packageName,
            @Path("feature_id") String featureId,
            @Body FeatureToggleItem updatedData
    );

    /**
     * Retrieves active feature toggles within a specific date range for a specified package.
     *
     * @param packageName The name of the package for which to retrieve active feature toggles.
     * @param startDate   The start date of the range (format: YYYY-MM-DD HH:MM:SS).
     * @param endDate     The end date of the range (format: YYYY-MM-DD HH:MM:SS).
     * @return A {@link Call} object containing a list of {@link com.example.featuretogglelibrary.model.FeatureToggleItem} objects.
     */
    @GET("feature-toggles/{package_name}/active-in-range")
    Call<List<FeatureToggleItem>> getActiveFeaturesInRange(
            @Path("package_name") String packageName,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate
    );

    /**
     * Retrieves statistics for a specified package.
     *
     * @param packageName The name of the package for which to retrieve statistics.
     * @return A {@link Call} object for the API response.
     */
    @GET("feature-toggles/{package_name}/statistics")
    Call<ResponseBody> getFeatureToggleStatistics(@Path("package_name") String packageName);

    /**
     * Retrieves all active feature toggles for a specified package by a specific date.
     *
     * @param packageName The name of the package for which to retrieve feature toggles.
     * @param date        The specific date (format: YYYY-MM-DD HH:MM:SS).
     * @return A {@link Call} object containing a list of {@link com.example.featuretogglelibrary.model.FeatureToggleItem} objects.
     */
    @GET("feature-toggles/{package_name}/by-date")
    Call<List<FeatureToggleItem>> getFeatureTogglesByDate(
            @Path("package_name") String packageName,
            @Query("date") String date
    );

    /**
     * Deletes all feature toggles for a specified package.
     *
     * @param packageName The name of the package for which to delete all feature toggles.
     * @return A {@link Call} object for the API response.
     */
    @DELETE("feature-toggles/{package_name}")
    Call<ResponseBody> deleteAllFeatureToggles(@Path("package_name") String packageName);



}
