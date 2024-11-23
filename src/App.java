import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private static final Scanner s = new Scanner(System.in);
    private static final Admin admin = new Admin();
    private static Customer customer = null;
    public static void main(String[] args) {
        GUI gui = new GUI();
        while(true) run();
    }
    private static void run(){
        System.out.println("=======Welcome to Byte Me!=========");
        while(true){
            System.out.println("Login as:\n1. Admin\n2. Customer\n3. Exit");
            String act;
            int action;
            while(true) {
                try {
                    act = s.nextLine();
                    action = Integer.parseInt(act);
                    break;
                } catch (Exception e) {
                    System.out.println("Enter a number!");
                }
            }
            if(action == 3){
                System.out.println("Exiting...");
                System.out.println("==================================\n");
                s.close();
                System.exit(0);
            }
            else if(action > 0 && action < 3) {
                App.run_sign_page(action);
                break;
            }
            else System.out.println("Enter correct number!");
        }
    }

    private static void run_sign_page(int mode){
        ArrayList<String> cred;
        switch (mode) {
            case 1 -> {
                App.run_admin();
            }
            case 2 -> {
                System.out.println("1. Sign in\n2. Sign up\n");
                String act;
                int action;
                while(true) {
                    try {
                        act = s.nextLine().strip();
                        action = Integer.parseInt(act);
                        break;
                    } catch (Exception e) {
                        System.out.println("Enter a number!");
                    }
                }
                if(action == 1){
                    while(true){
                        cred = User.login();
                        try {
                            customer = admin.fetch_cust(cred);
                            break;
                        } catch (InvalidLoginException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                else{
                    cred = User.signup();
                    try {
                        customer = admin.add_cust(cred);
                    } catch (InvalidLoginException e) {
                        System.out.println(e.getMessage());
                    }
                }
                run_cust();
            }
            default -> throw new AssertionError();
        }
    }

    private static void run_cust(){
        OUT:
        while(true){
            customer.update_text();
            System.out.printf("===========Welcome, %s============\n", customer.user);
            if(customer.vip) System.out.println("(VIP)");
            System.out.println("Choose Action: ");
            System.out.println("1. Browse menu\n2. Create new order\n3. Modify order\n4. Track/cancel order\n5. Checkout\n6. Become VIP\n7. Item reviews\n8. Exit");
            int action;
            while(true) {
                try {
                    String act = s.nextLine().strip();
                    action = Integer.parseInt(act);
                    break;
                } catch (Exception e) {
                    System.out.println("Enter a number!");
                }
            }
            switch (action) {
                case 1 -> {
                    System.out.println("---------");
                    System.out.println("1. View entire menu\n2. Filter by category\n3. Filter using keywords\n4. Sort by price\n5. Exit");
                    while(true) {
                        try {
                            String act = s.nextLine().strip();
                            action = Integer.parseInt(act);
                            if(action < 1 || action > 5){
                                System.out.println("Enter a valid number!");
                                continue;
                            }
                        } catch (Exception e) {
                            System.out.println("Enter a number!");
                            continue;
                        }
                        break;
                    }
                    switch (action) {
                        case 1 -> {
                            customer.view_menu();
                        }
                        case 2 -> {
                            customer.view_cat_menu();
                        }
                        case 3 -> {
                            System.out.print("Enter keyword: ");
                            String key = s.nextLine();
                            customer.view_key_menu(key);
                        }
                        case 4 -> {
                            customer.view_sortByPrice_menu();
                        }
                        case 5 -> System.out.println("Exiting.");
                        default -> System.out.println("Enter valid index!");
                    }
                    System.out.println("---------");
                }
                case 2 -> {
                    System.out.println("---------");

                    customer.create_order();

                    System.out.println("---------");
                }
                case 3 -> {
                    System.out.println("---------");
                    customer.order_status();
                    if(!customer.able_to_checkout()) break;
                    System.out.println("1. Add items\n2. Modify quantities\n3. Remove items\n4. View total amount\n5. Exit");
                    while(true) {
                        try {
                            String act = s.nextLine().strip();
                            action = Integer.parseInt(act);
                            if(action < 1 || action > 5){
                                System.out.println("Enter a valid number!");
                                continue;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Enter a number!");
                            continue;
                        }
                        break;
                    }
                    switch (action) {
                        case 1 -> {
                            customer.add_to_order();
                        }
                        case 2 -> {
                            customer.modify_order(1);
                        }
                        case 3 -> {
                            customer.modify_order(0);
                        }
                        case 4 -> {
                            customer.view_total();
                        }
                        case 5 -> {
                            System.out.println("Exiting.");
                        }
                        default -> System.out.println("Enter valid index!");
                    }

                    System.out.println("---------");
                }
                case 4 -> {
                    System.out.println("---------");
                    System.out.println("1. View current order status\n2. View status of all orders\n3. Cancel order\n4. Order history\n5. Exit");
                    while(true) {
                        try {
                            String act = s.nextLine().strip();
                            action = Integer.parseInt(act);
                            if(action < 1 || action > 5){
                                System.out.println("Enter a valid number!");
                                continue;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Enter a number!");
                            continue;
                        }
                        break;
                    }
                    switch (action) {
                        case 1 -> {
                            customer.order_status();
                        }
                        case 2 -> {
                            customer.order_status_all();
                        }
                        case 3 -> {
                            customer.cancel_order();
                        }
                        case 4 -> {
                            customer.order_history();
                        }
                        case 5 -> {
                            System.out.println("Exiting.");
                        }
                        default -> System.out.println("Enter valid index!");
                    }

                    System.out.println("---------");
                }
                case 5 ->{
                    System.out.println("---------");
                    customer.checkout();
                    System.out.println("---------");
                }
                case 6 -> {
                    System.out.println("---------");
                    customer.become_vip();
                    System.out.println("---------");
                }
                case 7 -> {
                    System.out.println("---------");
                    System.out.println("1. View reviews\n2. Give review\n3. Exit");
                    while(true) {
                        try {
                            String act = s.nextLine().strip();
                            action = Integer.parseInt(act);
                            if(action < 1 || action > 5){
                                System.out.println("Enter a valid number!");
                                continue;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Enter a number!");
                            continue;
                        }
                        break;
                    }
                    switch (action) {
                        case 1 -> {
                            customer.view_reviews();
                        }
                        case 2 -> {
                            customer.add_review();
                        }
                        case 3 -> System.out.println("Exiting.");
                        default -> System.out.println("Enter valid index!");
                    }
                }
                case 8 -> {
                    System.out.println("Logging out...");
                    break OUT;
                }
                default -> System.out.println("Enter correct number!");
            }
        }
    }
    private static void run_admin(){
        OUT:
        while(true){
            System.out.print("===========Welcome, Admin============\n");
            System.out.println("Choose Action: ");
            System.out.println("1. Menu management:\n2. Order management:\n3. Expense report:\n4. Exit");
            int action;
            while(true) {
                try {
                    String act = s.nextLine().strip();
                    action = Integer.parseInt(act);
                    break;
                } catch (Exception e) {
                    System.out.println("Enter a number!");
                }
            }
            switch (action) {
                case 1 -> {
                    System.out.println("---------");
                    admin.view_menu();
                    System.out.println("1. Add new items\n2. Update items\n3. Remove items\n4. Exit");
                    while(true) {
                        try {
                            String act = s.nextLine().strip();
                            action = Integer.parseInt(act);
                            if(action < 1 || action > 4){
                                System.out.println("Enter a valid number!");
                                continue;
                            }
                        } catch (Exception e) {
                            System.out.println("Enter a number!");
                            continue;
                        }
                        break;
                    }
                    switch (action) {
                        case 1 -> {
                            admin.add_menu();
                        }
                        case 2 -> {
                            admin.update_menu();
                        }
                        case 3 -> {
                            System.out.println("Make availability false to remove an item.");
                            admin.update_menu();
                        }
                        case 4 -> {
                            System.out.println("Exiting.");
                        }
                        default -> System.out.println("Enter valid index!");
                    }
                    System.out.println("---------");
                }
                case 2 -> {
                    System.out.println("---------");
                    System.out.println("1. View order queue\n2. Process orders\n3. Exit");
                    while(true) {
                        try {
                            String act = s.nextLine().strip();
                            action = Integer.parseInt(act);
                            if(action < 1 || action > 3){
                                System.out.println("Enter a valid number!");
                                continue;
                            }
                        } catch (Exception e) {
                            System.out.println("Enter a number!");
                            continue;
                        }
                        break;
                    }
                    switch (action) {
                        case 1 -> {
                            admin.view_order_queue();
                        }
                        case 2 -> {
                            admin.process_order();
                        }
                        case 3 -> {
                            System.out.println("Exiting.");
                        }
                        default -> System.out.println("Enter valid index!");
                    }
                    System.out.println("---------");
                }
                case 3 -> {
                    System.out.println("---------");

                    admin.sales_report();

                    System.out.println("---------");
                }
                case 4 -> {
                    System.out.println("Logging out...");
                    break OUT;
                }

                default -> System.out.println("Enter correct number!");
            }
        }
    }
}