package BUSTATION;

import java.util.Random;

public class LogisticsAttendant extends Worker {
	private String id;
	private UnboundedBuffer<ReturnBuses> logisticsLine;
	private UnboundedBuffer<ReturnBuses> cleanLine;
	private UnboundedBuffer<ReturnBuses> technicianLine;
	private double unloadingtime;
	private static Random rnd=new Random();

	public LogisticsAttendant(String id,UnboundedBuffer<ReturnBuses> logisticsLine,UnboundedBuffer<ReturnBuses> cleanline, UnboundedBuffer<ReturnBuses> technicianLine,double unloadingtime) {
		this.logisticsLine=	logisticsLine;
		this.cleanLine= cleanline;
		this.technicianLine=technicianLine;
		this.unloadingtime=unloadingtime;
		this.id=id;
	}
	public void run() {
		while (!logisticsLine.endDay) {  //as long as we didnt ranout of buses
			ReturnBuses b = this.logisticsLine.extract();
			if (b == null)
				break;
			enterBus(b);
			sendNext(b);
		}
	}
	private void enterBus(ReturnBuses b){ //do the work of the LogisticsAttendant
		try {
			long stay=(long) (unloadingtime*b.getCargo());
			Thread.sleep(stay);
			b.setTotalstay((int)stay);
		} catch (InterruptedException e) {
		}
	}
	private void sendToCleanLine(ReturnBuses b) { //sends to the next station
		this.cleanLine.insert(b);
	}
	private void sendToeTchnicianLine(ReturnBuses b) { //sends to the next station
		this.technicianLine.insert(b);
	}
	private void sendNext(ReturnBuses b) { //sends to the next station
		if(rnd.nextInt(1,10)!=1) { //checks if we need treatment
			sendToCleanLine(b);
		}
		else {
			b.setNeededTechnicalAttendant("LogisticsAttendant");
			sendToeTchnicianLine(b);
		}
	}
} //LogisticsAttendant
