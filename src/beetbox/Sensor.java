package beetbox;

import java.util.LinkedList;

public class Sensor {
    LinkedList<Event> q = new LinkedList<>();
    
    public void clear() {
        synchronized (q) {
            q.clear();
        }
    }
    
    public void enque(String name, Object ... args) {
        Event ev = new Event(name, args);
        synchronized (q) {
            q.add(ev);
            if (q.size() == 1) {
                q.notify();
            }
        }
    }
    
    public LinkedList<Event> queue() {
        return q;
    }
}
