package HTTP;

import java.util.HashMap;

/**
 * Created by nikolaev on 16.09.16.
 */
public class Request {
    private String reqString;
    private HashMap <String,String> reqParams;
    private boolean isValidRequest = true;

    public Request(String reqString) {
        this.reqString = reqString;
        reqParams = new HashMap<>();
        readHeaders();
    }
    private void readHeaders() {
        int uriStart = reqString.indexOf(" ") + 1;
        String path = reqString.substring(uriStart, reqString.indexOf(" ", uriStart));
        String method = reqString.substring(0, reqString.indexOf(" ")).toUpperCase();

        if (method.equals("")) {
            isValidRequest = false;
        } else {
            reqParams.put("Method", method);
            reqParams.put("Path", path);
        }
    }
    public String getMethod() {
        return reqParams.get("Method");
    }

    public String getPath () {
        return reqParams.get("Path");
    }
    public boolean isValidRequest() {
        return isValidRequest;
    }

}
