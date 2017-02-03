
import java.net.*;

public class CustomBrowser {

    public static void main(String[] args) {
        HTMLWebBrowser app = new HTMLWebBrowser();
        try {
            app.makeConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

class WebBrowser {

    public void makeConnection() {
    }
}

class HTMLWebBrowser extends WebBrowser {

    public void makeConnection() throws RuntimeException {
        try {
            URL url = new URL("http://www.oracle.com");
        } catch (MalformedURLException e) {
            RuntimeException ae = new RuntimeException("Invalid url");
            ae.initCause(e);
            throw ae;
        }
    }
}