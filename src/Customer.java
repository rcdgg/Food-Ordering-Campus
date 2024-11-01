import java.util.ArrayList;

public class Customer extends User{
    protected String user, pass;
    private boolean order_details_saved;
    private int order_cnt;
    public Customer(ArrayList<String> cred){
        user = cred.get(0);
        pass = cred.get(1);
        order_details_saved = false; // if true, dont ask for details during checkout
        order_cnt = 0;
    }

}
