import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;
import java.util.HashSet;

public class Backend {
    protected static ArrayList<ArrayList<Customer>> customer_list;
    public Backend(){
        customer_list = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            customer_list.set(i, new ArrayList<>());
        }
    }
    public void add_customer(ArrayList<String> cred, int status){
        customer_list.get(status).add(new Customer(cred));
    }
}
