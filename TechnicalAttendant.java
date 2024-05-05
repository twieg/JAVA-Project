package BUSTATION;

import java.util.Random;
import java.util.Vector;

public class TechnicalAttendant extends Worker{
	private String id;
	private UnboundedBuffer<BusDetails> managerLine;
	private UnboundedBuffer<ReturnBuses> cleanLine;
	private UnboundedBuffer<ReturnBuses> fuelLine;
	private UnboundedBuffer<ReturnBuses> technicianLine;
	private static Random rnd=new Random();

	public TechnicalAttendant( String id,UnboundedBuffer<ReturnBuses> cleanLine,UnboundedBuffer<BusDetails> managerLine, UnboundedBuffer<ReturnBuses>  fuelLine, UnboundedBuffer<ReturnBuses> technicianLine) {
		this.managerLine=managerLine;
		this.fuelLine=fuelLine;
		this.cleanLine=cleanLine;
		this.technicianLine=technicianLine;
		this.id=id;
	}

	public void run() {
		while (!technicianLine.endDay) { //as long as we didnt ranout of buses
			ReturnBuses b = this.technicianLine.extract();
			if (b == null)
				break;
			enterBus(b);
			sendNext(b);
		}
	}

	private void enterBus(ReturnBuses b){ //do the work of the Technical Attendant
		if(b.getWhereYouCameFrom() =="fuelLine") { //if the bus came from the fuel line
			try {
				int stay= rnd.nextInt(3000,5000)+1000;
				Thread.sleep(stay);
				b.setTotalstay(stay);
			} catch (InterruptedException e) {
			}
		}
		else {
			try {
				int stay= rnd.nextInt(3000,5000);
				Thread.sleep(stay);
				b.setTotalstay(stay);
			} catch (InterruptedException e) {
			}
		}
		b.setCostOfTreatment(rnd.nextInt(500,1000));
	}

	private void sendToCleanLine(ReturnBuses b) { //sends to the next station
		this.cleanLine.insert(b);
	}
	private void sendTofuelLine(ReturnBuses b) { //sends to the next station
		this.fuelLine.insert(b);
	}

	private void createReturnBusesDetails(ReturnBuses b) { //creates a document of the bus and send it to the manager
		String trip = b.getTrip();
		int cargo=b.getCargo();
		int passengers=b.getPassengers();
		boolean neededTechnicalAttendant=b.getneededTechnicalAttendant();
		int costOfTreatment=rnd.nextInt(500,1000);
		int totalstay=b.getTotalStay();
		boolean didexsplode=b.getExplode();
		BusDetails ReturnBusesDocument= new ReturnBusesDetails(passengers,trip,neededTechnicalAttendant,cargo,costOfTreatment,totalstay,didexsplode);
		ReturnBusesDocument.setCostOfTreatment(costOfTreatment);
		this.managerLine.insert(ReturnBusesDocument);
	}

	private void sendNext(ReturnBuses b) { //sends to the next station
		if(b.getWhereYouCameFrom()=="LogisticsAttendant") {
			sendToCleanLine(b);
		}
		if(b.getWhereYouCameFrom()=="Cleaner") {
			sendTofuelLine( b);
		}
		else {
			createReturnBusesDetails(b);
		}
	}

} //TechnicalAttendant
