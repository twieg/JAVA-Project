package BUSTATION;

public class ReturnBuses extends Bus {
	private int cargo;
	private boolean neededTechnicalAttendant=false;
	private String whereYouCameFrom;
	private boolean explode=false;
	//private int repairCost;
	private int arrivaltime;
	public ReturnBuses(String trip,int passengers,int arrivaltime,int cargo,UnboundedBuffer<Bus> gateLine) {
		this.trip=trip;
		this.passengers=passengers;
		this.cargo=cargo;
		this.arrivalTime=arrivaltime;
		this.gateLine=gateLine;
	}
	
	//getters
	public int getCargo() {
		return this.cargo;
	}
	public int getPassengers() {
		return this.passengers;
	}
	public String getTrip() {
		return this.trip;
	}
	public boolean getneededTechnicalAttendant() {
		return this.neededTechnicalAttendant;
	}
	public String getWhereYouCameFrom() {
		return this.trip;
	}
	public int getCostOfTreatment(){
		 return this.costOfTreatment;
	}
	public int getTotalStay() {
		return this.totalstay;
	}
	public boolean getExplode(){
		 return explode;
	}
	//getters
	
	public void setExplode(){
		this.explode=true;
	}
	public void setNeededTechnicalAttendant(String whereYouCameFrom ) { 
		this.whereYouCameFrom=whereYouCameFrom;
		neededTechnicalAttendant=true;
	}
	public void setCostOfTreatment(int costOfTreatment) { //total cost setter
		this.costOfTreatment=costOfTreatment;
	}
	public void setTotalstay(int tempstay){ //total stay setter
		this.totalstay+=tempstay;
	}
	
} //ReturnBuses
