import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class Order implements Comparable<Order>{
    Scanner s;
    public int status; // 0 = pending, 1 = preparing, 2 = out for delivery, 3 = delivered, -1 = cancelled, -2 = refunded
    public static HashMap<Integer, String> status_dict;
    public String requests;
    public int total;
    public HashMap<Food, Integer> items;
    public int id;
    public String name;
    public boolean vip;
    public Order(int id, String name, boolean vip){
        this.name = name;
        status = 0;
        total = 0;
        items = new HashMap<>();
        s = new Scanner(System.in);
        this.id = id;
        this.vip = vip;
        status_dict = new HashMap<>();
        status_dict.put(-1, "Cancelled");
        status_dict.put(-2, "Refunded");
        status_dict.put(0, "Pending");
        status_dict.put(1, "Preparing");
        status_dict.put(2, "Out for delivery");
        status_dict.put(3, "Delivered");
    }

    public void modify_item(Food food, int count){ // -ve count means removing items
        // food is the object from menu
        if(status == 0){ // order not placed or cancelled yet
            if (items.containsKey(food)) {
                int temp = Integer.max(0, items.get(food) + count);
                items.put(food, temp);
            } else { // adding new item
                items.putIfAbsent(food, Integer.max(count, 0));
            }
            if (items.get(food) <= 0) { //removing the item if its count is 0
                items.remove(food);
            }
        }
        else if(status > 0){
            System.out.println("Order already placed!");
        }
        else {
            System.out.println("Order already cancelled!");
        }
    }

    public void add_requests(String s){
        requests = s;
    }

    public void cancel_order(){
        if(status == 0) { // order wasnt placed
            System.out.println("Order has been cancelled!");
            total = 0;
        }
        else if(status == 1 || status == 2){ // order was placed
            System.out.printf("Order has been cancelled! (you will be refunded for %drs soon)\n", total);
        }
        else {
            System.out.println("Order already cancelled!");
        }
        status = -1;
    }

    // returns true when order is valid
    public boolean billing(){
        total = 0;
        boolean done = true;
        if(status < 0) done = false;
        ArrayList<String> bill = new ArrayList<>();
        System.out.println("Order ID: #" + id);
        System.out.printf("Status: %s\n", status_dict.get(status));
        for(Food i: items.keySet()){
                total += i.price * items.get(i);
                bill.add(String.format("%s (%d rs.)      x%d\n", i.name, i.price, items.get(i)));
            if(!i.available){
                done = false;
            }
        }
        System.out.println("--------");
        for(String s: bill) System.out.println(s);
        System.out.println("Bill: " + total);
        System.out.println("--------");
        return done;
    }

    public void view_order(){
        billing();
    }

    // if true returned, proceed with checkout
    //also call in case of view status
    public boolean checkout(){
        System.out.printf("Order status: %s\n", status_dict.get(status));
        if(status == 0){
            if(!billing()){
                cancel_order();
            }
            if(status == 0){
                System.out.print("Proceed with checkout? (y/n): ");
                String ans = s.nextLine().toLowerCase().strip();
                if(ans.equals("y") || ans.equals("yes")){
                    status = 1;
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public int compareTo(Order o) {
        return o.id - this.id;
    }
}
