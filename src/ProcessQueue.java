import java.util.PriorityQueue;
import java.util.Queue;

public class ProcessQueue {
    private final Queue<Order> vipQueue = new PriorityQueue<>(); // priority queue instead of normal queue because if someone upgrades to vip, their order will get ordered properly in this queue
    private final Queue<Order> regQueue = new PriorityQueue<>();

    public void add(Order o) {
        if (o.vip) {
            vipQueue.add(o);
        } else {
            regQueue.add(o);
        }
    }
    public void remove(Order o){
        if(o.vip){
            vipQueue.remove(o);
        } else {
            regQueue.remove(o);
        }
    }
    public Order poll() {
        if(isEmpty()){
            return null;
        }
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
        for(Order o: vipQueue) if(o.status != 0) System.out.println(o);
        System.out.println("Regular:");
        for(Order o: regQueue) if(o.status != 0) System.out.println(o);
    }
}