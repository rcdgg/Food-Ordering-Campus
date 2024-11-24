import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.BufferedReader;


public class GUI{
    JFrame frame;
    JButton menu, orders;
    JTextArea textArea;
    public GUI(){
        frame = new JFrame();
        frame.setTitle("Byte Me!");
        frame.setSize(880,880);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        textArea = new JTextArea();
        textArea.setFont(new Font("Helvetica", Font.PLAIN, 20));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(230,10,880 - 260,820);

        menu = new JButton("View menu");
        menu.setBounds(10,400,200,30);

        orders = new JButton("view pending orders");
        orders.setBounds(10,360,200,30);

        menu.addActionListener(_ -> read_menu());

        orders.addActionListener(_ -> read_pending());

        frame.add(menu);
        frame.add(orders);
        frame.add(scrollPane);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    private void read_menu(){
        textArea.setText("");
        try(BufferedReader read = new BufferedReader(new FileReader("src/menu.txt"))){
            String l;
            while((l = read.readLine()) != null){
                textArea.append(l + "\n");
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void read_pending(){
        textArea.setText("");
        try(BufferedReader read = new BufferedReader(new FileReader("src/pending.txt"))){
            String l;
            while((l = read.readLine()) != null){
                textArea.append(l + "\n");
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
