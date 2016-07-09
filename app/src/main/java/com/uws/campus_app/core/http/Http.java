package com.uws.campus_app.core.http;

public interface Http {
    void open(String url, String cacheFile);
    String get();
    String send();
}
