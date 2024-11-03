import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Admin extends User{
    public Set<Order> completed_orders;
    public Admin() {
        super();
        completed_orders = new HashSet<>();
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
        // requeue it unless the status is delivered or refunded
        // 0 = pending, 1 = preparing, 2 = out for delivery, 3 = delivered, -1 = cancelled, -2 = refunded
        System.out.println(o);
        switch (o.status) {
            case 1, 2 -> {
                System.out.print("Do you want to mark order as delivered? (y/n): ");
                String ss = s.nextLine().strip().toLowerCase();
                if(ss.equals("y") || ss.equals("yes")){
                    o.status = 3;
                }
            }
            case 0 -> {
                System.out.println("Choose an action: ");
                System.out.println("1 = Preparing, 2 = Out for delivery, 3 = Delivered, -1 = Cancelled");
                while(true){
                    try {
                        String ss = s.nextLine().strip().toLowerCase();
                        o.status = Integer.parseInt(ss);
                        if (o.status < -1 || o.status > 3) {
                            System.out.println("Enter a valid action!");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Enter a valid action!");
                        continue;
                    }
                    break;
                }
                if(!(o.status == 3)) backend.add_order(o);
                else completed_orders.add(o);
            }
            case -1 -> {
                System.out.print("Do you want to process the refund? (y/n): ");
                String ss = s.nextLine().strip().toLowerCase();
                if(ss.equals("y") || ss.equals("yes")){
                    o.status = -2;
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
        System.out.println("Today's income: " + tot);
    }
}
