package HTTP;

/**
 * Created by nikolaev on 18.09.16.
 */
public class TemplateResponse {
    private static final String TEMPLATE = "<!DOCTYPE html>" +
            "<html><head> " +
            "<title>{description}</title></head " +
            "<body>" +
            "<h1>{code}</h1> " +
            "<h2>{description}</h2>" +
            "</body></html>";

    public static String generatePage(int code, String description) {
        return TEMPLATE.replace("{description}", description).replace("{code}", String.valueOf(code));
    }
}
