package BUSTATION;

import java.util.Random;
import java.util.Vector;


public class FuelAttendant extends Worker {
	private String id;
	private int inatiallyfuel;
	private int currntfuel;
	private UnboundedBuffer<BusDetails> managerLine;
	private UnboundedBuffer<ReturnBuses> fuelLine;
	private UnboundedBuffer<ReturnBuses> technicianLine;
	private static Random rnd=new Random();

	public FuelAttendant( String id,int inatiallyfuel,UnboundedBuffer<BusDetails> managerLine,UnboundedBuffer<ReturnBuses> fuelLine, UnboundedBuffer<ReturnBuses> technicianLine) {
		this.managerLine=managerLine;
		this.fuelLine=fuelLine;
		this.technicianLine=technicianLine;
		this.inatiallyfuel=inatiallyfuel;
		this.id=id;
		this.currntfuel=inatiallyfuel;
	}
	public void run() {
		while (!fuelLine.endDay) { //as long as we didnt ranout of buses
			ReturnBuses b = this.fuelLine.extract();
			if (b == null)
				break;
			enterBus(b);
			sendNext(b);
		}
	}
	private void enterBus(ReturnBuses b){ //do the work of the FuelAttendant
		if(currntfuel>=200) {
			try {
				int stay= rnd.nextInt(3000,4000);
				Thread.sleep(stay);
				b.setTotalstay(stay);
			} catch (InterruptedException e) { 
			}
			currntfuel-=200;
		}
		else {
			try {
				Thread.sleep(5000);
				b.setTotalstay(5000);
			} catch (InterruptedException e) {
			}
			currntfuel=inatiallyfuel;
		}
	}

	private void sendToeTchnicianLine(ReturnBuses b) { //sends to the next station
		this.technicianLine.insert(b);
	}
	
	private void createReturnBusesDetails(ReturnBuses b) { //creates a document of the bus and send it to the manager
		String trip = b.getTrip();
		int cargo=b.getCargo();
		int passengers=b.getPassengers();
		boolean neededTechnicalAttendant=b.getneededTechnicalAttendant();
		int costOfTreatment=b.getCostOfTreatment();
		int totalstay=b.getTotalStay();
		boolean didexsplode=b.getExplode();
		BusDetails ReturnBusesDocument= new ReturnBusesDetails(passengers,trip,neededTechnicalAttendant,cargo,costOfTreatment, totalstay,didexsplode);
		this.managerLine.insert(ReturnBusesDocument);
	}
	private void sendNext(ReturnBuses b) { //sends to the next station
		if(rnd.nextInt(1,10)>3) { //checks if we need treatment
			createReturnBusesDetails( b);
		}
		else {
			b.setNeededTechnicalAttendant("FuelAttendant");
			sendToeTchnicianLine(b);
		}
	}
	
} //FuelAttendant



