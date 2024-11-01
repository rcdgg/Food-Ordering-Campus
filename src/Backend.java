import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Backend {
    protected static ArrayList<Customer> customer_list;
    protected static PriorityQueue<Order> order_list;
    protected static int reg_id, vip_id;
    private static Backend instance;

    private Backend(){
        customer_list = new ArrayList<>();
        order_list = new PriorityQueue<>();
        reg_id = 0;
        vip_id = -10000;
    }

    public static Backend getInstance(){
        if (instance == null) instance = new Backend();
        return instance;
    }

    public int get_id(boolean vip){
        if(vip) return vip_id++;
        else return reg_id++;
    }

    public void add_customer(ArrayList<String> cred){
        customer_list.add(new Customer(cred));
    }

    public void add_order(Order o){ //status = 0 is vip, = 1 is normal
        order_list.add(o);
    }
}
