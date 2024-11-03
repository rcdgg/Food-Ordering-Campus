import java.util.*;

public class Food{
    public String category, name;
    public int price;
    public boolean available;
    public HashMap<String, String> reviews; //customer user maps to their review of this food object
    public Set<Order> order_list;
    public Food(String name, String category, int price){
        this.name = name;
        this.category = category;
        this.price = price;
        reviews = new HashMap<>();
        order_list = new HashSet<>();
        available = true; // item available by default
    }

    public void order_invalid(){
        for(Order o: order_list){
            o.cancel_order_admin();
        }
    }

    public void add_review(String ID, String review){
        reviews.put(ID, review);
    }

    public String get_review(String ID){
        if(reviews.get(ID) == null) return "None";
        return reviews.get(ID);
    }
    @Override
    public String toString(){
        if(available) return String.format("%s        %drs", name, price);
        else return String.format("%s        %drs (unavailable)", name, price); //for the admin
    }

}

class FoodComparator implements Comparator<Food>{

    @Override
    public int compare(Food o1, Food o2) {
        return -(o2.price - o1.price);
    }
}
