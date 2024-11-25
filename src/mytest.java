import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;

public class mytest {
    Menu menu;
    ArrayList<String> cred;
    Customer c;
    @Before
    public void b4() throws InvalidLoginException {
        menu = Menu.getInstance();
        cred = new ArrayList<>();
        cred.add("test"); cred.add("test");
        c = App.admin.add_cust(cred);
        cred.clear();
    }

    @Test(expected = InvalidLoginException.class)
    public void loginfail() throws InvalidLoginException {
        cred.add("aaa"); cred.add("bbb");
        App.admin.fetch_cust(cred);
    }

    @Test
    public void loginpass() throws InvalidLoginException {
        cred.add("rohan"); cred.add("rohan");
        Customer c = App.admin.fetch_cust(cred);
        assertNotNull(c);
    }

    @Test
    public void cartTotal(){
        if(c.curr_order == null) c.create_order();
        ArrayList<Food> egg_items = menu.get_food_items("eggs");
        c.curr_order.modify_item(egg_items.getFirst(), 2);
        c.curr_order.billing();
        assertEquals(14, c.curr_order.total);

    }

    @Test
    public void cartModify(){
        if(c.curr_order == null) c.create_order();
        ArrayList<Food> egg_items = menu.get_food_items("eggs");
        c.curr_order.modify_item(egg_items.getFirst(), 3);
        c.curr_order.billing();
        assertNotEquals(14, c.curr_order.total);
    }

    @Test
    public void cartModifyneg(){
        if(c.curr_order == null) c.create_order();
        ArrayList<Food> egg_items = menu.get_food_items("eggs");
        c.curr_order.modify_item(egg_items.get(1), -10);
        assertNull(c.curr_order.items.get(egg_items.get(1)));
    }

    @After
    public void after(){
        File file = new File("users/test.txt");
        file.delete();
    }
}
