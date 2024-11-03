import java.util.LinkedList;
import java.util.Queue;

public class ProcessQueue {
    private final Queue<Order> vipQueue = new LinkedList<>();
    private final Queue<Order> regQueue = new LinkedList<>();

    public void add(Order o) {
        if (o.vip) {
            vipQueue.add(o);
        } else {
            regQueue.add(o);
        }
    }
    public Order poll() {
        if (!vipQueue.isEmpty()) {
            return vipQueue.poll();
        } else {
            return regQueue.poll();
        }
    }

    public boolean isEmpty() {
        return vipQueue.isEmpty() && regQueue.isEmpty();
    }

    public void print(){
        System.out.println("VIP:");
        for(Order o: vipQueue) System.out.println(o);
        System.out.println("Regular:");
        for(Order o: regQueue) System.out.println(o);
    }
}