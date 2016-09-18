package HTTP;

import com.sun.istack.internal.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nikolaev on 17.09.16.
 */
public class ResponseHeaders {
    private static final String HTTP_VERSION = "1.1";
    private String serverName = "JavNetty";

    private int statusCode;
    public Map<String, String> headers = new HashMap<>();

    public static Map<Integer, String> statusDescriptions = new HashMap<>();

    private static final String DATE = "Date";
    private static final String SERVER = "Server";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";
    private static final String CONNECTION = "Connection";

    static {
        statusDescriptions.put(200, "OK");
        statusDescriptions.put(404, "Not found");
        statusDescriptions.put(403, "Forbidden");
        statusDescriptions.put(400, "Bad request");
    }
    public ResponseHeaders(int statusCode) {
        this.statusCode = statusCode;
        addDefaultHeaders();
    }
    private void addDefaultHeaders() {
        headers.put(DATE, (new Date()).toString());
        headers.put(SERVER, serverName);
        headers.put(CONNECTION, "close");
    }

    @NotNull
    private String getHeaders() {
        final String mainHeader = String.format("HTTP/%s %d %s",
                HTTP_VERSION, statusCode, statusDescriptions.get(statusCode));

        StringBuilder sb = new StringBuilder();
        sb.append(mainHeader).append("\r\n");
        for(Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getHeaders() + "\r\n";
    }

}
