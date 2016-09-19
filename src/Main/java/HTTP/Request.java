package HTTP;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

/**
 * Created by nikolaev on 16.09.16.
 */
public class Request {
    private String reqString;
    private String parsedUri;
    private String method = "";
    private HashMap <String,String> reqParams;
    private boolean isValidRequest = true;
    private enum availableMethods {
        GET,
        HEAD,
    }

    public Request(String reqString) {
        this.reqString = reqString;
        reqParams = new HashMap<>();
        readHeaders();
    }
    private void readHeaders() {
        try {
            parsedUri = parseUri(reqString);
        } catch (UnsupportedEncodingException e) {
            throw new Error(e);
        }

        if (reqString.length() > 1) {
             method = reqString.substring(0, reqString.indexOf(" ")).toUpperCase();
        }

        if (method.equals("")) {
            isValidRequest = false;
        } else {
            reqParams.put("Method", method);
            reqParams.put("Path", parsedUri);
        }
        if (!isInEnum(reqParams.get("Method"), availableMethods.class)) {
            isValidRequest = false;
        }
    }

    private <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if(e.name().equals(value)) { return true; }
        }
        return false;
    }
    private static String checkUri(String uri) {
        try {
            uri = URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new Error(e);
        }

        if (uri.isEmpty() || uri.charAt(0) != '/') {
            return null;
        }

        if (uri.contains("/.") || uri.contains("./") || uri.charAt(uri.length() - 1) == '.') {
            return null;
        }

        return uri.replace('/', File.separatorChar);
    }

    private static String parseUri(String request) throws UnsupportedEncodingException {
        if (request.length() > 1) {
            int uriStart = request.indexOf(" ") + 1;
            String uri = request.substring(uriStart, request.indexOf(" ", uriStart));

            int queryStart = uri.indexOf('?');
            if (queryStart != -1) {
                uri = uri.substring(0, queryStart);
            }
            return URLDecoder.decode(uri, "UTF-8");
        } else {
            return "";
        }
    }

    public String getMethod() {
        return reqParams.get("Method");
    }

    public String getPath () {
        return checkUri(reqParams.get("Path"));
    }
    public boolean isValidRequest() {
        return isValidRequest;
    }
}
