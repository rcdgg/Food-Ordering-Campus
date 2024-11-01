import java.text.ParseException;
import java.util.*;

public class Menu {
    private HashMap<String, ArrayList<Food>> menu;
    Scanner s;
    public Menu(){
        menu = new HashMap<>();
        s = new Scanner(System.in);
    }
    public Set<String> get_categories(){ // print categories when using add items to menu
        return menu.keySet();
    }

    public void add_menu(String category, String name, int price){
        menu.putIfAbsent(category, new ArrayList<Food>());
        menu.get(category).add(new Food(name, category, price));
    }

    public void remove_menu(String category, String name){ //category and name is valid(make sure)
        Food temp = null;

        for(Food j: menu.get(category)){
            if(Objects.equals(j.name, name)){
                temp = j;
                break;
            }
        }
        menu.get(category).remove(temp);
        if(menu.get(category).isEmpty()){
            menu.remove(category);
        }
    }

    // returns true if update successful
    public boolean update_menu(String category, String name, int action){ //category and name is valid(make sure)
        boolean done = true;
        for(Food j: menu.get(category)){
            if(Objects.equals(j.name, name)){
                // action = 1 is name, 2 is price, 3 is availability
                if(action == 1){
                    j.name = s.nextLine();
                    System.out.println("name updated to " + j.name);
                }
                if(action == 2){
                    int pr;
                    while(true){
                        try {
                            pr = Integer.parseInt(s.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("enter a valid price!");
                        }
                    }
                    j.price = pr;
                }
                if(action == 3){
                    System.out.println(j);
                    System.out.print("change availability? (y/n): ");
                    String t = s.nextLine().strip().toLowerCase();
                    if(t.equals("y") || t.equals("yes")){
                        j.available = !j.available;
                        System.out.println("changed...");
                    }
                }
                else {
                    System.out.println("Enter a valid action index!");
                    done = false;
                }
                break;
            }
        }
        return done;
    }

    public void view_menu(){
        for(String i: menu.keySet()){
            System.out.printf("%s:\n", i.toUpperCase());
            for(Food j: menu.get(i)){
                System.out.println(j);
            }
        }
    }

    public void view_menu(String category){
        if(menu.containsKey(category)){
            System.out.println(category.toUpperCase() + ":");
            for(Food j: menu.get(category)){
                System.out.println(j);
            }
        }
        else{
            System.out.println("Category doesnt exist!");
        }
    }

    public void view_menu_key(String keyword){
        for(String i: menu.keySet()){
            System.out.printf("%s:\n", i.toUpperCase());
            for(Food j: menu.get(i)){
                if(j.name.contains(keyword)) System.out.println(j);
            }
        }
    }

    public void view_menu_sortByPrice(){
        ArrayList<Food> temp = new ArrayList<>();
        for(ArrayList<Food> f: menu.values()){
            temp.addAll(f);
        }
        FoodComparator f = new FoodComparator();
        temp.sort(f);
        for(Food j: temp) System.out.println(j);
    }
}
