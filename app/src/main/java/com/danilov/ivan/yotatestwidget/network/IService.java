package com.danilov.ivan.yotatestwidget.network;

import com.danilov.ivan.yotatestwidget.model.XmlResponse;

import retrofit.http.GET;

public interface IService {

    @GET("/sample.xml")
    XmlResponse getFeeds();
}
