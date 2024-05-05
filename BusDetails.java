package BUSTATION;

abstract class BusDetails {
	protected String trip;
	protected int passengers;
	protected int costOfTreatment;
	protected int totalstay;
	protected int cargo;
	protected int totalFuel;
	protected boolean didexsplode;
	protected String destination;
	abstract String getTrip();
	abstract int getPassengers();
	abstract int getCostOfTreatment();
	abstract void setCostOfTreatment(int cost);
	abstract int getTotalstay();
	abstract int getTotalCargo();
	abstract boolean getDidExsplode();
	abstract String getDestination();
} //BusDetails
