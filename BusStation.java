package BUSTATION;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class BusStation {
	private Vector<Worker> workers=new Vector<Worker>();
	private Vector<Bus> buses=new Vector<Bus>();
	private Vector<Thread> busesThread=new Vector<Thread>();
	private Vector<Thread> workersThreads =new Vector<Thread>();
	private Manager manager;
	private Thread managerThread;
	private UnboundedBuffer<Bus> gateLine=new UnboundedBuffer<Bus>() ;
	private UnboundedBuffer<ReturnBuses> logisticsLine=new UnboundedBuffer<ReturnBuses>();
	private UnboundedBuffer<BusDetails> managerLine=new UnboundedBuffer<BusDetails>();
	private UnboundedBuffer<ReturnBuses> fuelLine=new BoundedBuffer<ReturnBuses>(8);
	private UnboundedBuffer<ReturnBuses> technicianLine=new UnboundedBuffer<ReturnBuses>();
	private UnboundedBuffer<ReturnBuses> cleanLine=new UnboundedBuffer<ReturnBuses>();
	private int numTechnicalAttendant;
	private long cleaningTime;
	private int totalBuses;

	public BusStation(String buses,int NumTechnicalAttendant,double CleaningTime) {
		setbusesVector( buses);
		this.numTechnicalAttendant=NumTechnicalAttendant;
		this.cleaningTime=cleaningTime; 
		Worker gateKeeper1= new GateKeeper( gateLine, logisticsLine, managerLine);
		Worker gateKeeper2= new GateKeeper( gateLine, logisticsLine, managerLine);
		Worker logisticsAttendant1= new LogisticsAttendant("10000", logisticsLine, cleanLine,technicianLine,0.07);
		Worker logisticsAttendant2= new LogisticsAttendant("10001", logisticsLine, cleanLine,technicianLine,0.05);
		Worker logisticsAttendant3= new LogisticsAttendant("10002", logisticsLine, cleanLine,technicianLine,0.03);
		Worker cleaner1= new Cleaner(20, cleanLine, fuelLine,technicianLine,this.cleaningTime);
		Worker cleaner2=  new Cleaner(30, cleanLine, fuelLine,technicianLine,this.cleaningTime);
		Worker fuelAttendant1=  new FuelAttendant("10003", 2000, managerLine,fuelLine,technicianLine);
		Worker fuelAttendant2=  new FuelAttendant("10004", 1000, managerLine,fuelLine,technicianLine);
		Worker technicalAttendent;
		for (int i = 0; i < NumTechnicalAttendant; i++) {
			 technicalAttendent = new TechnicalAttendant(String.valueOf(i), cleanLine, managerLine, fuelLine,technicianLine); //loop that creates technicial 
			 workers.add(technicalAttendent);
			 Thread ttechnicalAttendent=new Thread(technicalAttendent);
			 workersThreads.add(ttechnicalAttendent);	
		}
		manager=new Manager("10005",managerLine,totalBuses,gateLine,logisticsLine,fuelLine, technicianLine, cleanLine);
		workers.add(gateKeeper1);
		workers.add(gateKeeper2);
		workers.add(logisticsAttendant1);
		workers.add(logisticsAttendant2);
		workers.add(logisticsAttendant3);
		workers.add(cleaner1);
		workers.add(cleaner2);
		workers.add(fuelAttendant1);
		workers.add(fuelAttendant2);

		Thread tGateKeeper1 = new Thread(gateKeeper1);
		Thread tGateKeeper2 = new Thread(gateKeeper2);
		Thread tLogisticsAttendant1 = new Thread(logisticsAttendant1);
		Thread tLogisticsAttendant2 = new Thread(logisticsAttendant2);
		Thread tLogisticsAttendant3 = new Thread(logisticsAttendant3);
		Thread tCleaner1 = new Thread(cleaner1);
		Thread tCleaner2 = new Thread(cleaner2);
		Thread tFuelAttendant1 = new Thread(fuelAttendant1);
		Thread tFuelAttendant2 = new Thread(fuelAttendant2);
		managerThread = new Thread(manager);
		workersThreads.add(tGateKeeper1);
		workersThreads.add(tGateKeeper2);
		workersThreads.add(tLogisticsAttendant1);
		workersThreads.add(tLogisticsAttendant2);
		workersThreads.add(tLogisticsAttendant3);
		workersThreads.add(tCleaner1);
		workersThreads.add(tCleaner2);
		workersThreads.add(tFuelAttendant1);
		workersThreads.add(tFuelAttendant2);

		Thread busThread;
		for (int i=0;i<this.buses.size();i++) { //creates a thread for each bus
			busThread=new Thread(this.buses.elementAt(i));
			busesThread.add(busThread);
		}
		runForEveryOne();
	}
	
	public void runForEveryOne() { //start for each thread
		for (int i=0;i<this.busesThread.size();i++) {
			busesThread.elementAt(i).start();
		}
		for (int i = 0; i<this.workersThreads.size(); i++) {
			workersThreads.elementAt(i).start();
		}
		managerThread.start();
	}

	private void setbusesVector(String buses) { //reading from a file
		BufferedReader inFile=null;
		String line;
		try {
			FileReader fr = new FileReader ("\u200F\u200Fbuses.txt"); //TODO changed file name, didn't work otherwise... you seem to have a weird symbol at the beginning of 'buses'
			inFile = new BufferedReader (fr);
			inFile.readLine();
			String tempLine;
			String [] arrLine = new String[4];
			while ((line = inFile.readLine()) != null){
				totalBuses++;
				tempLine = line;
				arrLine = tempLine.split("	");
				String trip = arrLine[0];
				int passengers = Integer.parseInt(arrLine[1]);
				int arrivaltime = Integer.parseInt(arrLine[2]);
				Bus b;
				if (arrLine.length>4) {//added check for returned busses vs out busses
					if (isStringInt(arrLine[4])) { 
						int cargo = Integer.parseInt(arrLine[4]); 
						b = new ReturnBuses(trip, passengers, arrivaltime, cargo, this.gateLine);
						this.buses.add(b);
					}
				}
				else {
					String destination = arrLine[3];
					b=new OutBuses(trip,passengers,arrivaltime,destination,this.gateLine);
					this.buses.add(b);
				}
			}
		}
		catch (FileNotFoundException exception) {
			System.out.println("The file " + buses + " was not found.");
		} catch (IOException exception) {
			System.out.println(exception);
		} finally {
			try {
				inFile.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}
	
	public boolean isStringInt(String s) { //checks if it is number
		try	{
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException ex)
		{
			return false;
		}
	}

} //BusStation
