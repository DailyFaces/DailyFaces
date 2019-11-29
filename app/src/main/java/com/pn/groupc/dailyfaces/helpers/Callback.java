package com.pn.groupc.dailyfaces.helpers;

public interface Callback {

    void onError(String message);

    void onResponse(Object response);
}
