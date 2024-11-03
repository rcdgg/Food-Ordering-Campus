import javax.swing.plaf.IconUIResource;
import java.util.*;

public class Backend {
    protected static ArrayList<Customer> customer_list;
    protected static ProcessQueue order_list;
    protected static int reg_id, vip_id;
    private static Backend instance;

    private Backend(){
        customer_list = new ArrayList<>();
        order_list = new ProcessQueue();
        reg_id = 0;
        vip_id = 0;
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

    public void add_order(Order o){
        order_list.add(o);
    }

    public Order poll_order(){
        return order_list.poll();
    }

    public void view_order_queue(){
        order_list.print();
    }
}

