import java.lang.reflect.Array;
import java.util.ArrayList;

public class Customer extends User{
    protected String user, pass;
    private boolean order_details_saved;
    private ArrayList<Order> order_list;
    public boolean vip;
    public Customer(ArrayList<String> cred){
        super();
        user = cred.get(0);
        pass = cred.get(1);
        order_details_saved = false; // if true, dont ask for details during checkout
        order_list = new ArrayList<>();
        vip = false; // need to buy vip
    }

    public void become_vip(){
        if(!vip) {
            vip = true;
            System.out.println("You are VIP now!");
        }
        else System.out.println("Already a VIP!");
    }

    public void order_history(){
        for(Order i: order_list){
            i.view_order();
        }
    }

    public void create_order(){
        Order o = new Order(backend.get_id(vip), user, vip);
        order_list.add(o);
        backend.add_order(o);
        System.out.print("Any special requests? (y/n): ");
        String ans = s.nextLine().toLowerCase().strip();
        if(ans.equals("y") || ans.equals("yes")){
            System.out.print("Write a brief description of your requests: ");
            String r = s.nextLine();
            o.add_requests(r);
        }
    }

}
