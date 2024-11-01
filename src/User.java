import java.util.ArrayList;
import java.util.Scanner;

public class User {
    protected static Backend backend;
    public User(){
        backend = new Backend();
    }
    public ArrayList<String> login(Scanner scanner){
        String username; String pass;
        ArrayList<String> cred = new ArrayList<>();
        System.out.println("Username: ");
        username = scanner.nextLine().strip();
        System.out.println("Password: ");
        pass = scanner.nextLine().strip();
        cred.add(username); cred.add(pass);

        return cred;
    }
    public ArrayList<String> signup(Scanner scanner){
        String username; String pass;
        ArrayList<String> cred = new ArrayList<>();
        System.out.println("---");
        System.out.println("Choose Username: ");
        username = scanner.nextLine().strip();
        System.out.println("Choose Password: ");
        pass = scanner.nextLine().strip();
        cred.add(username); cred.add(pass);
        System.out.println("---");
        return cred;
    }
}
