package com.raomeng.common.network.http;

import com.raomeng.common.network.HostType;

/**
 * @author RaoMeng
 * @describe 网络请求地址
 * @date 2018/1/3 15:21
 */

public class HttpUrls {
    private static final String HOST_TEST = "http://192.168.253.203:8090/ERP/";

    public interface UrlCommonList {
        String url = "mobile/common/list.action";

        interface Params {
            String caller = "caller";
            String condition = "condition";
            String page = "page";
            String pageSize = "pageSize";
        }

        interface Key {
            String key1 = "";
            String key2 = "";
            String key3 = "";
        }
    }

    /**
     * 获取域名
     *
     * @param hostType
     * @return
     */
    public static String getHost(HostType hostType) {
        String host = "";
        switch (hostType) {
            case HOST_TEST:
                host = HOST_TEST;
                break;
            default:
                break;
        }
        return host;
    }
}
