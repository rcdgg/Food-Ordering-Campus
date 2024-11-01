import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class Order {
    public int status; // 0 = pending, 1 = preparing, 2 = out for delivery, 3 = delivered, -1 = cancelled, -2 = refunded
    public String requests;
    public int total;
    public HashMap<Food, Integer> items;
    Scanner s;
    public int id;
    public Order(int id){
        status = 0;
        total = 0;
        items = new HashMap<>();
        s = new Scanner(System.in);
        this.id = id;
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

    public void view_order(){
        total = 0;
        boolean done = true;
        ArrayList<String> bill = new ArrayList<>();
        for(Food i: items.keySet()){
                total += i.price * items.get(i);
                bill.add(String.format("%s (%d rs.)      x%d\n", i.name, i.price, items.get(i)));
            if(!i.available){
                done = false;
            }
        }
        if(done){
            System.out.println("--------");
            for(String s: bill) System.out.println(s);
            System.out.println("Bill: " + total);
            System.out.println("--------");
        } else {
            cancel_order();
        }
    }

    // if true returned, proceed with checkout
    //also call in case of view status
    public boolean checkout(){
        if(status == -1){
            System.out.println("Order has been cancelled(will be refunded shortly)");
        }
        else if(status == -2){
            System.out.println("Order has been refunded.");
        }
        else if(status == 1){
            System.out.println("Preparing your order!");
        }
        else if(status == 2){
            System.out.println("Out for delivery!");
        }
        else if(status == 3){
            System.out.println("Order already delivered!");
        }
        else{
            view_order();
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


}
