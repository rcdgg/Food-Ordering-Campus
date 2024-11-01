import java.util.ArrayList;
import java.util.Comparator;

public class Food{
    public String category, name;
    public int price;
    public boolean available;
    public ArrayList<String> reviews;
    public Food(String name, String category, int price){
        name = this.name;
        category = this.category;
        price = this.price;
        reviews = new ArrayList<>();
        available = true;
    }
    @Override
    public String toString(){
        return String.format("%s " + (available?"(available)":"(not available)") + " Price: %d\n", name, price);
    }

}

class FoodComparator implements Comparator<Food>{

    @Override
    public int compare(Food o1, Food o2) {
        return o2.price - o1.price;
    }
}
