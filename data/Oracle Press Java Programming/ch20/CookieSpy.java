
import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class CookieSpy {

    private final static String TIME_FORMAT_NOW = "HH:mm:ss";
    private final static SimpleDateFormat sdf =
            new SimpleDateFormat(TIME_FORMAT_NOW);

    public static void main(String[] args) {
        try {
            String urlString = "http://www.yahoo.com";
            CookieManager manager = new CookieManager();
            manager.setCookiePolicy(new CustomCookiePolicy());
            CookieHandler.setDefault(manager);
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            Object obj = connection.getContent();
            List<HttpCookie> cookies = manager.getCookieStore().getCookies();
            for (HttpCookie cookie : cookies) {
                System.out.println("Name: " + cookie.getName());
                System.out.println("Domain: " + cookie.getDomain());
                long age = cookie.getMaxAge();
                if (age == -1) {
                    System.out.println("This cookie will expire when "
                            + "browser closes");
                } else {
                    System.out.printf("This cookie will expire in %s "
                            + "seconds%n", sdf.format(age));
                }
                System.out.println("Secured: "
                        + ((Boolean) cookie.getSecure()).toString());
                System.out.println("Value: " + cookie.getValue());
                System.out.println();
            }
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL");
        } catch (IOException e) {
            System.out.println("Error in I/O operation");
        }
    }
}

class CustomCookiePolicy implements CookiePolicy {

    public boolean shouldAccept(URI uri, HttpCookie cookie) {
// return uri.getHost().equals("yahoo.com");
        return true;
    }
}