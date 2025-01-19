package com.example.featuretogglelibrary.interfaces;

/**
 * A generic callback interface to handle API responses.
 *
 * @param <T> The type of data returned in case of success.
 */

public interface GenericCallBack <T> {

    /**
     * Called when the operation is successful.
     *
     * @param data The data returned from the operation.
     */
    void success(T data); // For success responses
    /**
     * Called when the operation fails.
     *
     * @param error The error message.
     */
    void error(String error);     // For failure responses
}
