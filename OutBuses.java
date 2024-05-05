package BUSTATION;

public class OutBuses extends Bus{
	private String destination;
	public OutBuses(String trip,int passengers,int arrivalTime,String destination,UnboundedBuffer<Bus> gateLine) { //constructor
		this.trip=trip;
		this.passengers=passengers;
		this.destination=destination;
		this.arrivalTime=arrivalTime;
		this.gateLine=gateLine;
	}
	//getters
	public int getPassengers() {
		return this.passengers;
	}
	public String getTrip() {
		return this.trip;
	}
	public String getDestination() {
		return this.destination;
	}
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	public int getCostOfTreatment(){
		 return this.costOfTreatment;
	}
	public int getTotalStay() {
		return this.totalstay;
	}
	//getters

} //OutBuses
