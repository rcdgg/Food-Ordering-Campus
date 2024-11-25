import java.util.*;

public class Order implements Comparable<Order>{
    Scanner s;
    public int status; // 0 = pending, 1 = preparing, 2 = out for delivery, 3 = delivered, -1 = cancelled, -2 = refunded
    public static HashMap<Integer, String> status_dict;
    public String requests;
    public int total;
    public LinkedHashMap<Food, Integer> items; // to make order in the map predictable
    public int id;
    public String name;
    public boolean vip, valid;
    public Customer cust;
    public Order(int id, String name, boolean vip, Customer cust){
        this.name = name;
        status = 0;
        total = 0;
        items = new LinkedHashMap<>();
        s = new Scanner(System.in);
        this.id = id;
        this.vip = vip;
        this.cust = cust;
        valid = true;
        status_dict = new HashMap<>();
        status_dict.put(-1, "Cancelled (Refund in process)");
        status_dict.put(-2, "Refunded");
        status_dict.put(0, "Pending");
        status_dict.put(1, "Waiting for confirmation");
        status_dict.put(2, "Preparing");
        status_dict.put(3, "Out for delivery");
        status_dict.put(4, "Delivered");
    }

    public void modify_item(Food food){
        // no count means just remove the item
        // food is the object from menu
        if(status == 0){ // order not placed or cancelled yet
            items.remove(food);
        }
        else if(status > 0){
            System.out.println("Order already placed!");
        }
        else {
            System.out.println("Order already cancelled!");
        }
    }

    public void modify_item(Food food, int count){
        // -ve count means removing items
        // food is the object from menu
        if(status == 0){ // order not placed or cancelled yet
            if (items.containsKey(food)) {
                int temp = Integer.max(0, items.get(food) + count);
                items.put(food, temp);
            } else { // adding new item
                items.putIfAbsent(food, Integer.max(count, 0));
                System.out.println("alo");
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
        }
        else if(status == 1 || status == 2){ // order was placed
            System.out.printf("Order has been cancelled! (you will be refunded for %drs soon)\n", total);
        }
        else {
            System.out.println("Order already cancelled!");
        }
        status = -1;
        valid = false;
    }

    public void cancel_order_admin(){
        if(status == 1) { // order wasnt placed
            System.out.printf("Order(ID: %d) has been cancelled!\n", this.id);
            status = -1;
        }
        valid = false;
    }

    // returns true when order is valid
    public ArrayList<String> billing(){
        total = 0;
        if(status < 0) valid = false;
        ArrayList<String> bill = new ArrayList<>();
        bill.add("--------");
        bill.add("Order ID: #" + id);
        bill.add(view_status());
        int ind = 1;
        for(Food i: items.keySet()){
                total += i.price * items.get(i);
                bill.add(String.format("%d. %s (%d rs.)      x%d\n", ind++, i.name, i.price, items.get(i)));
            if(!i.available){
                valid = false;
            }
        }
        bill.add("Bill: " + total + "rs");
        bill.add("--------");

        return bill;
    }

    public void view_order(){
        ArrayList<String> sss = billing();
        for(String ss: sss) System.out.println(ss);
    }

    public String view_status(){
        return String.format("Order status: %s", status_dict.get(status));
    }

    // if true returned, proceed with checkout
    //also call in case of view status
    public boolean checkout(){
        view_status();
        if(status == 0){
            ArrayList<String> ss = billing();
            for(String c: ss) System.out.println(c);
            if(!valid){
                cancel_order();
            }
            if(status == 0){
                System.out.print("Proceed with checkout? (y/n): ");
                String ans = s.nextLine().toLowerCase().strip();
                // status = 1;
                // status will be updated by admin from pending to delivered
                return ans.equals("y") || ans.equals("yes");
            }
        }
        return false;
    }

    @Override
    public int compareTo(Order o) {
        return -(o.id - this.id);
    }

    @Override
    public String toString(){
        if(vip) return String.format("(VIP) Order ID: %d | %s | Special requests: %s", id, view_status(), requests);
        return String.format("Order ID: %d | %s | Special requests: %s", id, view_status(), requests);
    }
}
