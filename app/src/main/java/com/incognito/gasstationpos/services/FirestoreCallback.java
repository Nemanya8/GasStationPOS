package com.incognito.gasstationpos.services;

public interface FirestoreCallback<T> {
    void onSuccess(T result);
    void onFailure(Exception e);
}