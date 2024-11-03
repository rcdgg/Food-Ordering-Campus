import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Customer extends User{
    protected String user, pass;
    private boolean order_details_saved;
    private ArrayList<Order> order_list;
    private LinkedHashSet<Food> food_list;
    private Order curr_order;
    public boolean vip;
    public Customer(ArrayList<String> cred){
        super();
        user = cred.get(0);
        pass = cred.get(1);
        order_details_saved = false; // if true, dont ask for details during checkout
        order_list = new ArrayList<>();
        food_list = new LinkedHashSet<>(); // ordered set
        curr_order = null;
        vip = false; // need to buy vip
    }

    public void become_vip(){
        if(!vip) {
            vip = true;
            System.out.println("You are VIP now!");
        }
        else System.out.println("Already a VIP!");
    }

    public void view_menu(){
        menu.view_customer();
    }

    public void view_key_menu(String key){
        menu.view_key_customer(key);
    }

    public void view_cat_menu(){
        Set<String> temp = menu.get_categories();
        System.out.println("All categories:");
        for(String cat: temp) System.out.print(cat + " ");
        System.out.print("Choose a category for the filter");
        String category = s.nextLine();
        menu.view_cat_customer(category);
    }

    public void view_sortByPrice_menu(){
        menu.view_sortByPrice_customer();
    }

    public void add_to_order(){
        view_menu();
        Set<String> temp = menu.get_categories();
        System.out.println("All categories:");
        for(String cat: temp) System.out.print(cat + " ");
        System.out.print("Choose a category for the filter");
        String category = s.nextLine().strip().toLowerCase();
        if(!(temp.contains(category))){
            // i dont keep retrying here until i get the right category because this gives a way to escape this function
            System.out.println("Invalid category!");
            return;
        }
        ArrayList<Food> food_items = menu.get_food_items(category);
        int ind;
        while(true){
            ind = 1;
            try{
                System.out.println("Choose the index of the item you want: ");
                for(Food i: food_items) {
                    System.out.printf("%d. "+i+"\n", ind++);
                }
                String ss = s.nextLine();
                ind = Integer.parseInt(ss);
                if(ind < 1 || ind > food_items.size()){
                    System.out.println("Enter a valid index!");
                    continue;
                }
            } catch (NumberFormatException e){
                System.out.println("Enter a valid index!");
                continue;
            }
            curr_order.modify_item(food_items.get(ind - 1), 1);
            food_items.get(ind - 1).order_list.add(curr_order);
            break;
        }
        System.out.printf("%s added to the order!\n", food_items.get(ind - 1).name);
    }

    /* remove or change count in order
    * action = 0 is remove, 1 = modify
    */
    public void modify_order(int action){
        curr_order.view_status();
        if(!(curr_order.valid)){
            System.out.println("Current order can't be processed!");
            return;
        }
        curr_order.view_order();
        int ind;
        while(true){
            System.out.print("Choose an item index to modify: ");
            try{
                String ss = s.nextLine();
                ind = Integer.parseInt(ss);
                if(ind < 1 || ind > curr_order.items.size()){
                    System.out.println("Enter a valid index!");
                }
                else break;
            } catch (NumberFormatException e){
                System.out.println("Enter a valid index!");
            }
        }
        Set<Food> keySet = curr_order.items.keySet();
        Food[] keyArray = keySet.toArray(new Food[0]);
        Food f = keyArray[ind - 1];
        if(action == 0){
            curr_order.modify_item(f);
            f.order_list.remove(curr_order);
        }
        else {
            System.out.printf("%d. %s (%d rs.)      x%d\n", ind, f.name, f.price, curr_order.items.get(f));
            int quan;
            while(true){
                System.out.print("Change quantity by(-ve to decrease): ");
                try{
                    String ss = s.nextLine();
                    quan = Integer.parseInt(ss);
                    break;
                } catch (NumberFormatException e){
                    System.out.println("Enter a valid quantity!");
                }
            }
            if (quan + curr_order.items.get(f) <= 0) f.order_list.remove(curr_order);
            curr_order.modify_item(f, quan);
        }
    }

    public void view_total(){
        if(curr_order != null) System.out.printf("Bill: %d\n", curr_order.total);
        else {
            System.out.println("You haven't started an order yet!");
        }
    }

    public void checkout(){
        if(curr_order == null){
            System.out.println("You haven't started an order yet!");
            return;
        }
        boolean cont = curr_order.checkout();
        if(!cont){
            System.out.println("You can't checkout!");
            return;
        }
        if(!order_details_saved){
            System.out.print("Enter address: ");
            s.nextLine();
            System.out.print("\nEnter payment method: ");
            s.nextLine();
            order_details_saved = true;
        } else {
            System.out.print("Use already saved order details? (y/n): ");
            String ss = s.nextLine().strip().toLowerCase();
            if(ss.equals("n") || ss.equals("no")){
                System.out.print("Enter address: ");
                s.nextLine();
                System.out.print("\nEnter payment method: ");
                s.nextLine();
                order_details_saved = true;
            }
        }
        food_list.addAll(curr_order.items.keySet());
        System.out.print("Any special requests? (y/n): ");
        String ans = s.nextLine().toLowerCase().strip();
        if(ans.equals("y") || ans.equals("yes")){
            System.out.print("Write a brief description of your requests: ");
            String r = s.nextLine();
            curr_order.add_requests(r);
        }
        System.out.printf("Order placed! (Order ID: %d)\n", curr_order.id);
        curr_order = null;
    }

    public void order_status(){
        for (Order o: order_list){
            System.out.printf("Order ID: %d | ", o.id);
            o.view_status();
        }
    }

    public void cancel_order(){
        //print all orders which have +ve status + not already delivered
        for(Order o: order_list){
            if(o.status >= 0 && o.status != 3){
                o.view_order();
            }
        }
        int id;
        Order t = null;
        while(true){
            System.out.println("Choose an order to cancel by ID");
            boolean done = false;
            try{
                String ss = s.nextLine();
                id = Integer.parseInt(ss);
                for(Order o: order_list){
                    if(o.id == id){
                        done = true;
                        t = o;
                        break;
                    }
                }
            } catch (NumberFormatException e){
                System.out.println("Enter a valid ID!");
            }
            if(done) break;
            else System.out.println("Enter a valid ID!");
        }
        t.cancel_order();
    }

    public void order_history(){
        for(Order i: order_list){
            i.view_order();
        }
    }

    public void create_order(){
        if(curr_order != null){ // make curr_order null after checkout
            System.out.printf("You already have a pending order! (Order ID: %d)\n", curr_order.id);
            System.out.print("Do you wish to discard this order to create a new one? (y/n): ");
            String ans = s.nextLine().strip().toLowerCase();
            if(!(ans.equals("n") || ans.equals("no"))) return;
        }
        Order o = new Order(backend.get_id(vip), user, vip);
        curr_order = o;
        order_list.add(o);
        backend.add_order(o);
    }

    public void view_reviews(){
        for(Food f: food_list){
            System.out.println(f.name + " " + f.get_review(user));
        }
    }

    public void add_review(){
        int ind = 1;
        if(food_list.isEmpty()){
            System.out.println("Buy items first to review them!");
            return;
        }
        for(Food f: food_list){
            System.out.printf("%d. " + f, ind++);
        }
        while(true){
            try {
                String ss = s.nextLine();
                ind = Integer.parseInt(ss);
                if (ind < 1 || ind > food_list.size()) {
                    System.out.println("Enter a valid index!");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid index!");
                continue;
            }
            break;
        }
        Food temp = food_list.toArray(new Food[0])[ind - 1];
        System.out.print("Write your review: ");
        String rev = s.nextLine();
        temp.add_review(user, rev);
        System.out.println("Review added!");
    }
}
