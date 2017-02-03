
import java.io.Console;
import java.util.Arrays;

public class ConsoleApp {

    private static final int MAX_LOGINS = 3;

    public static void main(String[] args) {
        ConsoleApp app = new ConsoleApp();
        if (app.login()) {
            System.out.println("Thanks for logging in!");
        } else {
            System.out.println("Login failed!");
        }
    }

    private boolean login() {
        Console console = System.console();
        boolean isAuthenticated = false;
        if (console != null) {
            int count = 0;
            do {
                char[] pwd = console.readPassword("[%s]", "Password:");
                isAuthenticated = authenticate(pwd);
// delete password from memory
                Arrays.fill(pwd, ' ');
                console.writer().write("\n");
            } while (!isAuthenticated && ++count < MAX_LOGINS);
        }
        return isAuthenticated;
    }

    private boolean authenticate(char[] passwd) {
        char[] secret = {'M', 'c', 'G', 'R', 'A', 'W', 'H', 'I', 'L', 'L'};
        if (java.util.Arrays.equals(passwd, secret)) {
            java.util.Arrays.fill(passwd, ' ');
            System.out.println("Authenticated\n");
            return true;
        } else {
            System.out.println("Authentication failed\n");
        }
        return false;
    }
}