package BUSTATION;

public class ReturnBusesDetails extends BusDetails {
	private boolean neededTechnicalAttendant;
	protected int costOfTreatment;

	public ReturnBusesDetails(int passengers,String trip,boolean neededTechnicalAttendant,int cargo,int costOfTreatment , int totalstay,boolean didexsplode) {
		this.trip=trip;
		this.passengers=passengers;
		this.neededTechnicalAttendant=neededTechnicalAttendant;
		super.cargo=cargo;
		this.costOfTreatment=costOfTreatment;
		this.totalFuel=totalFuel;
		this.didexsplode=didexsplode;
	}
	
	//getters
	public String getDestination() {
		return " ";
	}
	public String getTrip() {
		return this.trip;
	}
	public int getPassengers() {
		return this.passengers;
	}
	public boolean getNeededTechnicalAttendant() {
		return this.neededTechnicalAttendant;
	}
	public int getTotalCargo() {
		return this.cargo;
	}
	public int getCostOfTreatment(){
		 return this.costOfTreatment;
	}
	public int getTotalstay(){
		return totalstay;
	}
	public boolean getDidExsplode() {
		return didexsplode;
	}
	//getters
	
	public void setCostOfTreatment(int costOfTreatment){ //total cosr setter
		this.costOfTreatment=costOfTreatment;
	}

} //ReturnBusesDetails
