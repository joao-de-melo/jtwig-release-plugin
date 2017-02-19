package org.jtwig.plugins.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import java.io.IOException;

public class ResponseUtils {
    public static String toLogString(HttpResponse response) {
        return response.toString() + "\nContent: \n" + getContent(response);
    }


    public static String getContent(HttpResponse httpResponse) {
        try {
            return IOUtils.toString(httpResponse.getEntity().getContent());
        } catch (IOException e) {
            return "";
        }
    }
}
