package BUSTATION;

import java.util.Random;

public class Cleaner extends Worker{
	private int seniority;
	private double cleantime;
	private UnboundedBuffer<ReturnBuses> cleanLine;
	private UnboundedBuffer<ReturnBuses> fuelLine;
	private UnboundedBuffer<ReturnBuses> technicianLine;
	private static Random rnd=new Random();
	
	public Cleaner(int seniority,UnboundedBuffer<ReturnBuses> cleanLine,UnboundedBuffer<ReturnBuses> fuelLine, UnboundedBuffer<ReturnBuses> technicianLine,double cleantime) {
		this.cleanLine=	cleanLine;
		this.fuelLine=fuelLine;
		this.technicianLine=technicianLine;
		this.seniority=seniority;
		this.cleantime=cleantime;
	}
	
	public void run() {
		while (!cleanLine.endDay) { //as long as we didnt ranout of buses
			ReturnBuses b = this.cleanLine.extract();
			if (b == null)
				break;
			enterBus(b);
			sendNext(b);
		}
	}
	
	private void enterBus(ReturnBuses b){ //do the work of the cleaner
		try {
			long stay=(long) (cleantime);
			Thread.sleep(stay);
			b.setTotalstay((int)stay);
		} catch (InterruptedException e) {
		}
	}
	private void sendTofuelLine(ReturnBuses b) { //sends to the next station
		this.fuelLine.insert(b);
	}
	private void sendToeTchnicianLine(ReturnBuses b) { //sends to the next station
		this.technicianLine.insert(b);
	}
	private void sendNext(ReturnBuses b) { 
		if(rnd.nextInt(1,20)==1) {//checks if we have a explode
			try {
				b.setExplode();
				Thread.sleep(2000);
				b.setTotalstay(2000);
			} catch (InterruptedException e) {
			}
			sendTofuelLine(b);
		}
		else {
			if(rnd.nextInt(1,100)>25) {//checks if we need treatment
				sendTofuelLine(b);
			}
			else {
				b.setNeededTechnicalAttendant("Cleaner");
				sendToeTchnicianLine(b);
			}
		}
	}

} //Cleaner
