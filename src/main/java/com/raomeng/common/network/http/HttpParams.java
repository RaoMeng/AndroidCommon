package com.raomeng.common.network.http;

import com.raomeng.common.network.HostType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author RaoMeng
 * @describe 网络请求参数
 * @date 2018/1/3 15:18
 */

public class HttpParams {
    /**
     * host类型
     */
    private HostType hostType;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 回调标记
     */
    private int flag;

    /**
     * 请求参数
     */
    private Map<String, Object> params;

    /**
     * 请求头
     */
    private Map<String, Object> headers;

    /**
     * 请求方式
     */
    private HttpMethod method;


    public HttpParams(Builder builder) {
        this.hostType = builder.hostType;
        this.url = builder.url;
        this.flag = builder.flag;
        this.params = builder.params;
        this.headers = builder.headers;
        this.method = builder.method;
    }

    public HostType getHostType() {
        return hostType;
    }

    public String getUrl() {
        return url;
    }

    public int getFlag() {
        return flag;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public static class Builder {
        private HostType hostType;
        private String url;
        private int flag;
        private Map<String, Object> params = new HashMap<>();
        private Map<String, Object> headers = new HashMap<>();
        private HttpMethod method;

        public Builder hostType(HostType val) {
            this.hostType = val;
            return this;
        }

        public Builder url(String val) {
            this.url = val;
            return this;
        }

        public Builder flag(int val) {
            this.flag = val;
            return this;
        }

        public Builder method(HttpMethod httpMethod) {
            this.method = httpMethod;
            return this;
        }

        public Builder setParams(Map<String, Object> params) {
            if (this.params == null) {
                this.params = new HashMap<>();
            }
            if (params != null) {
                this.params.putAll(params);
            }
            return this;
        }

        public Builder addParam(String key, Object value) {
            if (this.params == null) {
                this.params = new HashMap<>();
            }
            this.params.put(key, value);
            return this;
        }

        public Builder setHeaders(Map<String, Object> headers) {
            if (this.headers == null) {
                this.headers = new HashMap<>();
            }
            if (headers != null) {
                this.headers.putAll(headers);
            }
            return this;
        }

        public Builder addHeader(String key, Object value) {
            if (this.headers == null) {
                this.headers = new HashMap<>();
            }
            this.headers.put(key, value);
            return this;
        }

        public HttpParams build() {
            return new HttpParams(this);
        }
    }
}
