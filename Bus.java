package BUSTATION;

public class Bus implements Runnable {
	protected UnboundedBuffer<Bus> gateLine;
	protected String trip;
	protected int passengers;
	protected int arrivalTime;
	protected int totalstay;
	protected int costOfTreatment;

	//getters
	public int getTotalStay() {
		return this.totalstay;
	}
	public boolean getExplode(){
		return false;
	}
	public String getTrip() {
		return this.trip;
	}
	public int getPassengers() {
		return this.passengers;
	}
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	public int getCostOfTreatment(){
		return this.costOfTreatment;
	}
	//getters
	
	private void enterBus() { //enter the bus to gate line
		gateLine.insert(this);
	}
	public void run() {
		enterBus();
	}
	public void setCostOfTreatment(int costOfTreatment) { //treatment cost setter
		costOfTreatment=0;
	}
	public void setTotalstay(int tempstay) {//sums the stay time for each bus
		this.totalstay+=tempstay;
	}

} //Bus
