package com.apa.namaa.web.rest.util;

import org.springframework.http.HttpHeaders;

/**
 * Utility class for http header creation.
 *
 */
public class HeaderUtil {
 
    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-namaaApaApp-alert", message);
        headers.add("X-namaaApaApp-params", param);
        return headers;
    }
    
    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert("namaaApaApp." + entityName + ".created", param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert("namaaApaApp." + entityName + ".updated", param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert("namaaApaApp." + entityName + ".deleted", param);
    }

}