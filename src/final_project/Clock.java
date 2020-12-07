package final_project;
import java.util.*;
/**Final Project, Java Implementation of Round Robin CPU Scheduling Algorithm
/**
 * Round Robin CPU Scheduling Algorithm Simulation (Class: Clock.Java)
 * @author Justin Cook/ Jcook03266
 * 
 * Clock Class, In this class we update the time elapsed since the start of the simulation, we update the arrival time of 
 * a process waiting to get into the ready queue, as well as the wait time of a process already in the ready queue by using an iterator
 * and then setting the wait time of the current cpu
 */
public class Clock {
	private int t; //our time 't'

	public Clock(){
		t = 0;//t initialized to a value of 0
	}

	//update time elapsed since start of the simulation
	public void updatetss(int cpuRT) {
		t = t + cpuRT;//add the original time to the passed cpu runtime
	}
	//get time elapsed since start of the simulation
	public int gettss() {
		return t;
	}
	//update the arrival time of a process waiting to get into the ready queue, using an array of the processes and the cpu runtime
	public void updateatwfrq(ArrayList<Process> Process_Array, int cpuRT) {
		for(int i = 0; i < Process_Array.size(); i++) {
			Process p = Process_Array.get(i);
			p.updateat(cpuRT);//Update the arrival time for the process by using the cpu runtime 'cpuRT' as argument
		}
	}
	//update the wait time of a process in the ready queue 
	//cpuRT = cpu runtime, cp = current process, process queue = the queue for the processes given to this method as arg
	public void updatewtfrq(Queue<Process> Process_Queue, int cpuRT, Process cp) {
		Iterator<Process> it = Process_Queue.iterator(); //We iterate through our queue using the imported iterator 
		while(it.hasNext()) {
			Process p = it.next();//get the next process in the iteratpr
			if(p != cp)//if the process at hand isn't equal to the next process then we set the wait time to the cpu runtime 
				p.setctWT(cpuRT);//Set the current wait time of the process 
		}
	}
}
