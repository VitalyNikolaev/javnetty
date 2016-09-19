package HTTP;

import java.util.HashMap;

/**
 * Created by nikolaev on 19.09.16.
 */
public class ContentType {
    private static final String DEFAULT_CONTENT_TYPE = "text/html";

    private static HashMap<String, String> contentTypes;

    static {
        contentTypes = new HashMap<>();
        contentTypes.put("css", "text/css");
        contentTypes.put("html", "text/html");
        contentTypes.put("gif", "image/gif");
        contentTypes.put("jpg", "image/jpeg");
        contentTypes.put("jpeg", "image/jpeg");
        contentTypes.put("png", "image/png");
        contentTypes.put("swf", "application/x-shockwave-flash");
        contentTypes.put("txt", "text/txt");
        contentTypes.put("js", "text/javascript");
        contentTypes.put("xml", "text/xml");
    }

    public static String getContentType(String extension) {
      String result = contentTypes.get(extension);
        if (result != null) {
            return result;
        } else {
            return DEFAULT_CONTENT_TYPE;
        }
    }
}
