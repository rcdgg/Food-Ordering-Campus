import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Admin extends User{
    public Set<Order> completed_orders;
    public Admin() {
        super();
        completed_orders = new HashSet<>();
        menu.add("paratha","paneer paratha",30);
        menu.add("paratha","mooli paratha",30);
        menu.add("eggs","boiled eggs",7);
        menu.add("eggs","omelette",20);
        menu.add("maggi","fried maggi",40);
        menu.add("dosa","masala dosa",60);
    }

    public void view_menu(){
        menu.view_admin();
    }

    public void add_menu(){
        menu.view_admin();
        System.out.print("Category: ");
        String cat = s.nextLine().strip().toLowerCase();
        System.out.print("\nName of dish: ");
        String name = s.nextLine().toLowerCase().strip();
        int price;
        while(true){
            System.out.print("\nPrice: ");
            try{
                String ss = s.nextLine();
                price = Integer.parseInt(ss);
            } catch (NumberFormatException e){
                System.out.println("Enter a valid price!");
                continue;
            }
            break;
        }
        menu.add(cat, name, price);
    }

    public void update_menu(){
        menu.view_admin();
        System.out.print("Category: ");
        String cat = s.nextLine().strip().toLowerCase();
        System.out.print("\nName of dish: ");
        String name = s.nextLine().toLowerCase().strip();
        if(!menu.verify_item(cat, name)){
            System.out.println("Invalid item!");
            return;
        }
        int action;
        System.out.println("Choose an action: ");
        while(true){
            System.out.println("1. Update name 2. Update price 3. Update availability(toggle)");
            try{
                String ss = s.nextLine();
                action = Integer.parseInt(ss);
            } catch (NumberFormatException e){
                System.out.println("Enter a valid price!");
                continue;
            }
            break;
        }
        while(!menu.update(cat, name, action));
    }

    public void view_order_queue(){
        backend.view_order_queue();
    }

    public void process_order(){
        Order o = backend.poll_order();
        if(o == null){
            System.out.println("No orders to process currently!");
            return;
        }
        // requeue it unless the status is delivered or refunded
        // 0 = pending, 1 = preparing, 2 = out for delivery, 3 = delivered, -1 = cancelled, -2 = refunded
        System.out.println(o);
        switch (o.status) {
            case 2, 3 -> {
                System.out.print("Do you want to mark order as delivered? (y/n): ");
                String ss = s.nextLine().strip().toLowerCase();
                if(ss.equals("y") || ss.equals("yes")){
                    o.status = 4;
                    System.out.println(o);
                    if(o.cust.curr_order == o) o.cust.curr_order = null;
                    completed_orders.add(o);
                }
                else {
                    backend.add_order(o);
                }
            }
            case 1 -> {
                System.out.println("Choose an action: ");
                System.out.println("2 = Preparing, 3 = Out for delivery, 4 = Delivered, -1 = Cancelled");
                while(true){
                    try {
                        String ss = s.nextLine().strip().toLowerCase();
                        o.status = Integer.parseInt(ss);
                        if (o.status < -1 || o.status > 4) {
                            System.out.println("Enter a valid action!");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Enter a valid action!");
                        continue;
                    }
                    break;
                }
                if(o.status != 4) backend.add_order(o);
                else completed_orders.add(o);
                if(o.status == -1) o.cancel_order_admin();
                if(o.cust.curr_order == o) o.cust.curr_order = null;
            }
            case -1 -> {
                System.out.print("Do you want to process the refund? (y/n): ");
                String ss = s.nextLine().strip().toLowerCase();
                if(ss.equals("y") || ss.equals("yes")){
                    o.status = -2;
                    if(o.cust.curr_order == o) o.cust.curr_order = null;
                    completed_orders.add(o);
                }
            }
        }
    }

    public void sales_report(){
        int tot = 0;
        for(Order o: completed_orders){
            System.out.println(o);
            if(o.status > 0) tot += o.total;
            else tot -= o.total;
        }
        System.out.println("Today's income: " + tot + "rs");
    }

    public Customer fetch_cust(ArrayList<String> cred) throws InvalidLoginException{
        for(Customer c: Backend.customer_list){
            if(c.user.equals(cred.get(0)) && c.pass.equals(cred.get(1))) return c;
        }
        throw new InvalidLoginException("Invalid login credentials!");
    }

    public Customer add_cust(ArrayList<String> cred) throws InvalidLoginException{
        backend.add_customer(cred);
        return fetch_cust(cred);
    }
}
