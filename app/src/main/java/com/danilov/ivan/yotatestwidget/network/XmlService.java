package com.danilov.ivan.yotatestwidget.network;


import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit.RestAdapter;
import retrofit.client.Request;
import retrofit.client.UrlConnectionClient;
import retrofit.converter.SimpleXMLConverter;


public class XmlService {

    private static final int TIMEOUT = 50000;

    public static IService getAPI(String url) {
        return new RestAdapter.Builder()
                .setClient(new CustomClient())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(url)
                .setConverter(new SimpleXMLConverter())
                .build()
                .create(IService.class);
    }

    private static class CustomClient extends UrlConnectionClient {
        @Override
        protected HttpURLConnection openConnection(Request request) throws IOException {
            HttpURLConnection connection = super.openConnection(request);
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            return connection;
        }
    }
}
