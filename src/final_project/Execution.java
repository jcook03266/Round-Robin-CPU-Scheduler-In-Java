package final_project;
import java.util.*;
/**Final Project, Java Implementation of Round Robin CPU Scheduling Algorithm
/**
 * Round Robin CPU Scheduling Algorithm Simulation (Class: Execution.Java)
 * @author Justin Cook/ Jcook03266
 * 
 * Execution class, this is where all of our process objects go to get executed and analyzed while being executed
 * by the round robin scheduling algorithm. We basically input a process into the scheduling algorithm where we track
 * whether or not it has been completed or not, its position in the ready queue, whether it's ready to go into the ready
 * queue or come out of it to be executed, and if it has to go back into the queue and popped out from the head of the queue once more. This class is pretty 
 * self explanatory, it executes the processes using RR.
 * 
 */

public class Execution {
	//Variable stack
	private ArrayList<Process> Process_Array;//Process array
	private int quant;//time quantum
	private CPU cpu1;//cpu object that reflect the cpu execution
	private Queue<Process> RQueueReady;//Queue object that reflects the ready queue
	private Clock clock1;//clock object
	private ArrayList<Process> Completed_Processes_Array;//Completed processes array
	private static int tsp;//total system processes 
	private String Eventlog;
	//End of variable stack

	//Construct our execution object from the two required inputs, the processes and the time quantum
	public Execution(ArrayList<Process> Process_Array, int quant) {

		this.Process_Array = Process_Array; //Extract our processes array from the passed array
		this.quant = quant;//Make the time quantum variable in this system equal to the time quantum passed
		cpu1 = new CPU(quant);//New CPU object with the time quantum passed being input as its constructor's argument
		clock1 = new Clock();//New clock object to time stamp the events for all processes, time of creation, completion time etc.
		RQueueReady = new LinkedList<Process>(); //New FIFO ready queue in the form of a linked list, (first in first out) used by the process creator and cpu
		Completed_Processes_Array = new ArrayList<Process>(); //To keep track of the completed processes we make an array of the processes and add them to it as they're marked done
		tsp = Process_Array.size();//Total system processes, the total number of processes currently in the system

		//Here the initial arrival times and burst times are set for later application 
		for(int i = 0; i < Process_Array.size(); i++) {
			Process_Array.get(i).setiat();
			Process_Array.get(i).setibt();
		}
	}
	//We call this in order to execute everything else in this class
	public void ExeRRSim() {
		Pc(null);//Adds process to ready queue thus creating it
		RR();//Processes the processes until they're done and after which they're added to the 'completed' arraylist object
	}

	/**Creates processes, Process creator if you will
	 *This looks to see if the arrival time of the process is 0 and the burst time isn't 0 which would inherently mean that 
	 *The process is ready to execute, (Checks to see if the process is ready to execute)
	 */
	public void Pc(Process cp) {
		for(int i = 0; i < Process_Array.size(); i++) {
			Process p = Process_Array.get(i); 
			//checks to see if the process is ready to execute and is not complete and that the process isn't equal to the current process and isn't in the ready queue
			if(p.isreadytoexe() == true && p.isprocesscomplete() == 0 && (p != cp) && !RQueueReady.contains(p)) {
				p.settat();//Set the arrival times 
				p.getcpinfo();//Get the information on the current process which will be printed out later
				RQueueReady.add(p);//Add the ready process into the ready queue in order for it to be processed later
				Eventlog += " \nNew Process: " + p.getpid() + " Added to Ready Queue \n\n";//Add this info to the event log string in order to be printed out in the GUI
			}
		}

	}

	//Exports the event log string which contains all information on the entire program's operation to the GUI when called
	public String getEventlog() {
		String regex = "\\s*\\bnull\\b\\s*";//Remove all null values in the string with empty space
		Eventlog = Eventlog.replaceAll(regex, "");
		return this.Eventlog;
	}

	//Round Robin method processes the processes until they're done and after which they're added to the 'completed' arraylist object
	//This is the heart of this entire program, this is the Round Robin algorithm 
	public void RR() {
		boolean complete = false;//While loop that iterates until all processes are processed 

		while(complete == false) {
			Process cp = RQueueReady.peek();//Sets Process object p to the process at the start(head) of the ready queue
			if(cp != null) {//If the current process isn't null aka it's an actual process / not empty space
				int cpuRT = cpu1.execute(cp);//our cpu run time 
				clock1.updatetss(cpuRT);//update the time since start with the cpu run time we just calculated 
				clock1.updateatwfrq(Process_Array, cpuRT);//We update the arrival time of the process waiting to get into the ready queue 
				clock1.updatewtfrq(RQueueReady, cpuRT, cp);//Update the wait time of the process already in the ready queue
				Pc(cp);//Add current process to ready queue 
				if(cp.isprocesscomplete() == 1) {//If the current process is complete we then mark it as complete and then remove it from the queue
					Eventlog += cp.getEventlog();
					Eventlog += "Process " + cp.getpid() + " completed execution \n\n";
					Process_Array.remove(cp);
					Eventlog += "Process " + cp.getpid() + " removed from ready queue \n\n";
					Completed_Processes_Array.add(RQueueReady.remove());//Process is removed from ready queue and put in the finished process array

					if(Completed_Processes_Array.size() == tsp) {
						//If the completed process array equals the total system processes then this therefore means that all process
						//And since all processes are complete we can then invoke the final method which analyzes everything that just happened
						Complete();
						complete = true;
					}
				}
				else
				{
					//If none of the others is true then we do the following:
					cp.updatecsc();//Context switched, process switched out of cpu thus we add to the context switch counter
					cp.settat();//Setting arrival time again, without doing this we'll get a 0 since they're not initialized prior
					cp.getcpinfo();//Get properties again which will then be printed out later
					RQueueReady.remove();//Get rid of the process from the ready queue's leading position (head)
					RQueueReady.add(cp);//Add the process to the ready queue's trailing position (tail)
					Pc(cp);//Go back to process creator with the current process
				}
			}
			//If there's no current process aka the current process 'cp' = null 
			else 
			{
				//If none of the above then:
				clock1.updatetss(quant);//Update clock's time since the start of the execution for the simulation
				clock1.updateatwfrq(Process_Array, quant);//Update the arrival time of the processes awaiting entry into the ready queue
				Pc(null); //Give the process creator method 'Pc' an argument of null 
			} 
		}
	}

	public void Complete() {
		int tat[] = new int[tsp]; //Turn around time array
		int tot_wt = 0, tot_tat = 0, tot_cs = 0; //total wait time total context switches and total turn around time initialized with a value of 0
		int wt = 0; //wait time
		double avgWt = 0.0;//Average wait time
		double avgTat = 0.0;//Average turnaround time
		double Tp = 0;//Throughput
		float cpu_util = 0; //CPU utilization, float b/c if we have a regular double or int then dividing will give us 0.0

		//Set the arrival times for all of the completed processes 
		for(int i = 0; i < Completed_Processes_Array.size(); i++) {
			Completed_Processes_Array.get(i).settat();
		}

		//Get the sum of the turnaround times, the sum of the wait times, and the sum of the context switches for all processes
		for(int i = 0; i < Completed_Processes_Array.size(); i++) {
			tat[i] = Completed_Processes_Array.get(i).gettat();
			tot_tat += tat[i];
			wt += Completed_Processes_Array.get(i).gettwt();
			tot_wt = wt;
			tot_cs += Completed_Processes_Array.get(i).getcsc();
		}

		//Calculate the average wait time by dividing the total wait time by the total amount of system processes
		//Calculate the turnaround time by dividing the total turnaround time by the total amount of system processes
		avgWt = (double)tot_wt / tsp;
		avgTat = (double)tot_tat / tsp;

		//Event log that will print out to the GUI later on 
		Eventlog += "\n \n ////Analysis//// \n" + "Average wait time: " + avgWt + "\n" + "Average turnaround time: " + avgTat + "\n" ;

		//Throughput calculated, this is basically the efficiency percentage of the cpu with the following algorithm
		//Computed via dividing the number of completed processes by the time since the start of operating
		Tp = (double)cpu1.getcpc()/clock1.gettss() * 100.0;//Calculate throughput by get the number of completed processes and dividing it by the time elapsed

		//CPU utilization is calculated, basically how much the cpu is used by this algorithm
		//This is computed using the time since the start of operation subtracted by the total context switches divided again by the time since the start
		cpu_util = (float)(((float)(clock1.gettss()-tot_cs)/clock1.gettss())*100.0);

		Eventlog += "Throughput: " + Tp + "%" + "\n";
		Eventlog += "\n" + "CPU utilization: " + cpu_util + "%" + "\n\n\n";
		Eventlog += "";
	}
}
