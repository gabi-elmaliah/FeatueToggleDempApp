package com.example.featuretogglelibrary.model;

/**
 * Represents the statistics for feature toggles in a package.
 *
 * This class is used to map the JSON response from the backend that provides
 * statistical information about feature toggles in a specific package.
 *
 * Example JSON structure:
 * {
 *     "total_features": 10,
 *     "active_features": 6
 * }
 *
 * Fields:
 * - total_features: Total number of feature toggles in the package.
 * - active_features: Number of currently active feature toggles in the package.
 */

public class FeaturesStatistics {

    private int total_features;
    private int active_features;

    public int getTotal_features() {
        return total_features;
    }

    public void setTotal_features(int total_features) {
        this.total_features = total_features;
    }

    public int getActive_features() {
        return active_features;
    }


    public void setActive_features(int active_features) {
        this.active_features = active_features;
    }


}
