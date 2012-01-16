package com.specl.api.client;

public interface CallBack {
    public <T> T decode(Class<T> clazz, String json) throws Exception;
}
