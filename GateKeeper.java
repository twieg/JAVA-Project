package BUSTATION;
import java.util.Random;
import java.util.Vector;

public class GateKeeper extends Worker{
	private UnboundedBuffer<Bus> gateLine;
	private UnboundedBuffer<ReturnBuses> logisticsLine;
	private UnboundedBuffer<BusDetails> managerLine;
	private static Random rnd=new Random();

	public GateKeeper(UnboundedBuffer<Bus> gateLine,UnboundedBuffer<ReturnBuses> logisticsLine,UnboundedBuffer<BusDetails> managerLine )   //constructor
	{
		this.gateLine=gateLine;
		this.logisticsLine=logisticsLine;
		this.managerLine=managerLine;
	}

	public void run() {
		while (!gateLine.endDay) { //as long as we didnt ranout of buses
			Bus b = this.gateLine.extract();

			if (b == null)
				break;
			enterBus(b);
			sendNext(b);
		}
	}

	private void enterBus(Bus b){ //do the work of the GateKeeper
		try {
			int stay=rnd.nextInt(5000,10000);

			Thread.sleep(stay);
			b.setTotalstay(stay);
		} catch (InterruptedException e) {
		}
	}

	private void sendToLogistics(ReturnBuses b) { //sends to the next station
		this.logisticsLine.insert(b);
	}

	private void createOutBusesDetails(Bus b) { //creates a document of the bus and send it to the manager
		if(b instanceof OutBuses ) {
			String trip = ((OutBuses) b).getTrip();
			int passengers=((OutBuses) b).getPassengers();
			String destination=((OutBuses) b).getDestination();
			int arrivalTime=((OutBuses) b).getArrivalTime();
			int totalstay=b.getTotalStay();
			boolean didexsplode=b.getExplode();
			BusDetails OutBusesDocument = new OutBusesDetails(trip,passengers,arrivalTime,destination,totalstay,0);
			this.managerLine.insert(OutBusesDocument);
		}
	}

	private void sendNext(Bus b) { //sends to the next station
		if (b instanceof ReturnBuses)
			sendToLogistics((ReturnBuses)b);
		else {
			createOutBusesDetails( b); 
		}
	}
} //GateKeeper
