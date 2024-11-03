import java.util.*;

public class Menu {
    private final HashMap<String, ArrayList<Food>> menu_list;
    private static Menu instance;
    Scanner s;
    private Menu(){
        menu_list = new HashMap<>();
        s = new Scanner(System.in);
    }

    public static Menu getInstance(){
        if(instance == null) instance = new Menu();
        return instance;
    }
    public Set<String> get_categories(){ // print categories when using add items to menu
        return menu_list.keySet();
    }

    public ArrayList<Food> get_food_items(String cat){
        return menu_list.get(cat);
    }

    public boolean verify_item(String cat, String item){
        if(menu_list.containsKey(cat)){
            for(Food f: menu_list.get(cat)){
                if(f.name.equals(item)) return true;
            }
        }
        return false;
    }

    // MAKE SURE CATEGORY, NAME ARE IN LOWER CASE AND STRIPPED
    public void add(String category, String name, int price){
        menu_list.putIfAbsent(category, new ArrayList<>());
        for(Food f: menu_list.get(category)){
            if(Objects.equals(f.name, name)) return; // in case the item already exists
        }
        menu_list.get(category).add(new Food(name, category, price));
    }

    // returns true if update successful
    public boolean update(String category, String name, int action){ //category and name is valid(make sure)
        boolean done = true;
        for(Food j: menu_list.get(category)){
            if(Objects.equals(j.name, name)){
                // action = 1 is name, 2 is price, 3 is availability
                if(action == 1){
                    System.out.print("Enter new name: ");
                    j.name = s.nextLine();
                    System.out.println("name updated to " + j.name);
                }
                else if(action == 2){
                    int pr;
                    while(true){
                        System.out.print("Enter new price: ");
                        try {
                            pr = Integer.parseInt(s.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("enter a valid price!");
                        }
                    }
                    j.price = pr;
                }
                else if(action == 3){
                    System.out.println(j);
                    // if unavailable, it will be explicitly written otherwise nothing written
                    System.out.print("change availability? (y/n): ");
                    String t = s.nextLine().strip().toLowerCase();
                    if(t.equals("y") || t.equals("yes")){
                        j.available = !j.available;
                        System.out.println("changed...");
                    }
                    if(!j.available){
                        j.order_invalid();
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

    public void view_customer(){ // will only print available dishes
        for(String i: menu_list.keySet()){
            System.out.printf("# %s:\n", i.toUpperCase());
            for(Food j: menu_list.get(i)){
                if(j.available) System.out.println(j);
            }
        }
    }
    public void view_admin(){ //will bring non-available dishes to make available later on if admin wishes
        for(String i: menu_list.keySet()){
            System.out.printf("# %s:\n", i.toUpperCase());
            for(Food j: menu_list.get(i)){
                System.out.println(j);
            }
        }
    }

    public void view_cat_customer(String category){
        if(menu_list.containsKey(category)){
            System.out.println(category.toUpperCase() + ":");
            for(Food j: menu_list.get(category)){
                System.out.println(j);
            }
        }
        else{
            System.out.println("Category doesnt exist!");
        }
    }

    public void view_key_customer(String keyword){
        for(String i: menu_list.keySet()){
            System.out.printf("# %s:\n", i.toUpperCase());
            for(Food j: menu_list.get(i)){
                if(j.name.contains(keyword)) System.out.println(j);
            }
        }
    }

    public void view_sortByPrice_customer(){
        ArrayList<Food> temp = new ArrayList<>();
        for(ArrayList<Food> f: menu_list.values()){
            temp.addAll(f);
        }
        FoodComparator f = new FoodComparator();
        temp.sort(f);
        for(Food j: temp) System.out.println(j);
    }
}
