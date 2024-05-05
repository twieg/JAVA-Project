package BUSTATION;
import java.util.Vector;

public class UnboundedBuffer<T> {
    protected Vector <T> queue;
    protected boolean endDay;

    public UnboundedBuffer() { //Constructor
        this.queue= new Vector<T>();
        this.endDay=false;
    }

    public synchronized void insert(T bus) { //add an object to the collection
        queue.add(bus);
        this.notifyAll();
    }

    public synchronized T extract() { //remove an object from the collection
        while(this.queue.isEmpty()&&!this.endDay) {
            try {
                this.wait();
                if (this.endDay) {
                    return null;
                }
            }
            catch (InterruptedException e) {
            }
            if (this.endDay&&this.queue.isEmpty()) {
                return null;
            }
        }
        T ans=queue.remove(0);
        this.notifyAll();
        return ans;
    }

    public synchronized void EndDay() { //the end of the day
        this.endDay=true;
        notifyAll();
    }
    
} //UnboundedBuffer
