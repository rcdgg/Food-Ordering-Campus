import java.util.ArrayList;
import java.util.Scanner;

public class User {
    static Scanner s;
    protected static Backend backend;
    protected static Menu menu;
    public User(){
        s = new Scanner(System.in);
        backend = Backend.getInstance();
        menu = Menu.getInstance();
    }
    public static ArrayList<String> login(){
        String username; String pass;
        ArrayList<String> cred = new ArrayList<>();
        System.out.println("Username: ");
        username = s.nextLine().strip();
        System.out.println("Password: ");
        pass = s.nextLine().strip();
        cred.add(username); cred.add(pass);

        return cred;
    }
    public static ArrayList<String> signup(){
        String username; String pass;
        ArrayList<String> cred = new ArrayList<>();
        System.out.println("Choose Username: ");
        username = s.nextLine().strip();
        System.out.println("Choose Password: ");
        pass = s.nextLine().strip();
        cred.add(username); cred.add(pass);
        return cred;
    }
}
