package HTTP;

import com.sun.istack.internal.Nullable;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by nikolaev on 16.09.16.
 */
public class Request {
    private String reqString;
    private HashMap<String,String> reqParams;
    public Request(String reqString) {
        this.reqString = reqString;
        reqParams = new HashMap<>();
        readHeaders();
    }
    private void readHeaders() {
        int uriStart = reqString.indexOf(" ") + 1;
        String path = reqString.substring(uriStart, reqString.indexOf(" ", uriStart));
        String method = reqString.substring(0, reqString.indexOf(" ")).toUpperCase();

        reqParams.put("Method", method);
        reqParams.put("Path", path);

    }

}
