package BUSTATION;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class Manager implements Runnable {
	private String id;
	private UnboundedBuffer<BusDetails> managerLine;
	private UnboundedBuffer<Bus> gateLine;
	private UnboundedBuffer<ReturnBuses> logisticsLine;
	private UnboundedBuffer<ReturnBuses> fuelLine;
	private UnboundedBuffer<ReturnBuses> technicianLine;
	private UnboundedBuffer<ReturnBuses> cleanLine;
	private Vector<BusDetails> outBusesVector=new Vector<BusDetails>();
	private int counter=1;
	private int totalBuses;
	private int totalCargo=0;
	private int totalPassengers=0;
	private int totalCost=0;
	private int totalfuel=0;
	private int totalExploadings=0;
	public Manager(String id,UnboundedBuffer<BusDetails> managerLine,int totalBuses,UnboundedBuffer<Bus> gateLine,UnboundedBuffer<ReturnBuses> logisticsLine,UnboundedBuffer<ReturnBuses> fuelLine,UnboundedBuffer<ReturnBuses> technicianLine,UnboundedBuffer<ReturnBuses> cleanLine) {
		this.managerLine=managerLine;
		this.totalBuses=totalBuses;
		this.id=id;
		this.gateLine=gateLine;
		this.logisticsLine=logisticsLine;
		this.fuelLine=fuelLine;
		this.technicianLine=technicianLine;
		this.cleanLine=cleanLine;
	}
	
	private void insertToExiting(OutBusesDetails report) { //write to a file
		String content = report.getTrip() + " " + report.passengers + " " + report.getDestination() + " " + report.getTotalstay()+"\n";
		// Try block to check if exception occurs
		try {
			FileWriter fWriter=new FileWriter("Exiting.txt",true);
			fWriter.write(content);
			fWriter.close();
		}
		catch (IOException e) {
		}
	}

	private void insertToEntering(ReturnBusesDetails report) {  //write to a file
		String content = report.getTrip() + " " + report.getPassengers() +"	"+ report.getTotalCargo() + " " + report.getCostOfTreatment()+"	"+report.getDidExsplode()+"	"+report.getTotalstay()+ "\n";
		// Try block to check if exception occurs
		try {
			FileWriter fWriter=new FileWriter("Entering.txt",true);
			fWriter.write(content);
			fWriter.close();
		}
		catch (IOException e) {
		}
	}

	public void run()  {
		while(counter<=totalBuses) { //loop thats runs on all the buses we have
			while(this.managerLine!=null) {
				BusDetails b=managerLine.extract();
				WriteToForm(b);
				PrintBus(b);
				counter++; //counts the buses
				totalPassengers+=b.getPassengers();
				if(b instanceof ReturnBusesDetails) {
					totalCargo= totalCargo+b.getTotalCargo();
				}
				totalCost=totalCost+b.getCostOfTreatment();
				if(b instanceof ReturnBusesDetails) {
					totalfuel=totalfuel+200;
				}
				if(b.getDidExsplode())
					totalExploadings++;
				outBusesVector.add(b);
				if(counter==totalBuses+1)
					PrintFinalReport();
			}
		}
		this.notifyAll();
		managerLine.EndDay();
		endDay(gateLine,logisticsLine,fuelLine,technicianLine,cleanLine);
	}

	public void WriteToForm(BusDetails busDetails) { //write to a file
		if(busDetails instanceof ReturnBusesDetails) {
			insertToEntering((ReturnBusesDetails)busDetails);
		}
		if(busDetails instanceof OutBusesDetails) {
			insertToExiting((OutBusesDetails)busDetails);
		}		
	}

	public void endDay (UnboundedBuffer<Bus> gateLine, UnboundedBuffer<ReturnBuses> logisticsLine,UnboundedBuffer<ReturnBuses> fuelLine,UnboundedBuffer<ReturnBuses> technicianLine,UnboundedBuffer<ReturnBuses> cleanLine){ //updates all the queue that all the buses arrive or return and we end all the threds
		gateLine.EndDay();
		logisticsLine.EndDay();
		fuelLine.EndDay();
		technicianLine.EndDay();
		cleanLine.EndDay();
	}

	public String mostPopularDestination() { //popular destination
		int currentDestCounter = 0;
		int bestDestCounter = 0;
		String mostPopularDest = "";
		for(BusDetails bus: this.outBusesVector) {
			for(BusDetails bus2: this.outBusesVector) {
				if(bus.getDestination().equals(bus2.getDestination())) {
					currentDestCounter++;
				}
			}
			if(currentDestCounter>bestDestCounter) {
				mostPopularDest=bus.getDestination();
			}
		}
		return mostPopularDest;
	}
	
	public void PrintBus(BusDetails b) { //each bus report
		System.out.println("The bus code is: "+b.getTrip());
		System.out.println("Total time of treatments: "+b.getTotalstay());
		System.out.println("Total cost of treatments: "+b.getCostOfTreatment());
		System.out.println();
	}
	
	public void PrintFinalReport() { //manager final report
		System.out.println("Total passengers: "+this.totalPassengers);
		System.out.println("Total cargo: "+this.totalCargo);
		System.out.println("The most popular destination is: " +mostPopularDestination());
		System.out.println("Total cost of treatments: "+this.totalCost);
		System.out.println("Total fuel used: "+this.totalfuel);
		System.out.println("Total amount of suspicious objects: "+this.totalExploadings);
	}
} //Manager





