package BUSTATION;

public class BoundedBuffer<T> extends UnboundedBuffer<T>  {
	private int maxSize;

	public BoundedBuffer(int maxSize){ //constructor
		this.maxSize=maxSize;
	}

	public synchronized void insert(T p)  { //add patient to the collection only if there is place
		while(this.queue.size()>=this.maxSize&&!this.endDay) { 
			try {
				this.wait();
			}
			catch (InterruptedException e) {
			}
		}
		this.queue.add(p);
		this.notifyAll();
	} //insert
	
} //BoundedBuffer
