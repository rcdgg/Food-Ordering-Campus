import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;


public class GUI{
    JFrame frame;
    JButton menu, orders;
    JTextArea textArea;
    public GUI(){
        frame = new JFrame();
        frame.setTitle("Byte Me!");
        frame.setSize(880,880);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(200,200,300,300);

        menu = new JButton("View menu");
        menu.setBounds(100,100,100,100);

        orders = new JButton("view pending orders");
        orders.setBounds(0,0,100,100);

        menu.addActionListener(e -> {
            read_menu();
        });

        orders.addActionListener(e -> {
            read_pending();
        });

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
