package BUSTATION;

public class OutBusesDetails extends BusDetails {
	private int arrivalTime;
	private int costOfTreatment;
	public OutBusesDetails(String trip,int passengers,int arrivalTime,String destination,int totalstay,int costOfTreatment ) {
		this.trip=trip;
		this.passengers=passengers;
		this.destination=destination;
		this.arrivalTime= arrivalTime;
		this.costOfTreatment=0;
		this.totalstay=totalstay;
		this.didexsplode=false;
		super.costOfTreatment=costOfTreatment;
	}
	//getters
	public String getTrip() {
		return this.trip;
	}
	public int getPassengers() {
		return this.passengers;
	}
	public String getDestination() {
		return this.destination;
	}
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	public void setCostOfTreatment(int costOfTreatment){
		this.costOfTreatment=costOfTreatment;
	}
	public int getCostOfTreatment(){
		 return this.costOfTreatment;
	}
	public int getTotalstay(){
		return totalstay;
	}
	public int getTotalCargo() {
		return 0;
	}
	public boolean getDidExsplode() {
		return false;
	}
	//getters
	
} //OutBusesDetails
