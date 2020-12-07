package final_project;
/**Final Project, Java Implementation of Round Robin CPU Scheduling Algorithm
/**
 * Round Robin CPU Scheduling Algorithm Simulation (Class: Process.Java)
 * @author Justin Cook/ Jcook03266
 * 
 * Process class enables the creation of our process objects which use like trading cards with the other
 * classes, with clock and cpu enabling the tracking of these processes and the statistics attributed to them 
 * via later analysis performed in the execution class. All of the process objects start off here.
 * 
 */

public class Process {
	//Variable stack for the processes 
	private int pid; //Process ID
	private int at; //Arrival Time
	private int bt; //Burst Time
	private int quant; //Time quantum
	private int iat;//Initial arrival time 
	private int ibt;//Initial burst time
	private int csc;//context switch counter
	private int tat;//Turn around time, aka process service aka how much time it took to complete the process
	private int wt;//Wait time aka how much time a process has to wait in order to get into the ready queue
	private String Eventlog;
	//End of variables

	//Main constructor, constructs the process object using the values from the input and the data structures in which they were stored in
	public Process(int pid, int at, int bt, int quant) {
		this.pid = pid;
		this.at = at;
		this.bt = bt;
		this.quant = quant;
	}
	//get the process id, returns the process id aka the name of the current process
	public int getpid() {
		return this.pid;
	}
	//Set inital arrival time, this is like declaring at as final, we can reference the untampered default value of at later on
	public void setiat(){
		this.iat = this.at;
	}
	//Get inital arrival time
	public int getiat() {
		return this.iat;
	}
	//Get arrival time
	public int getat() {
		return this.at;
	}
	//update the arrival time with the given cpu runtime value
	public void updateat(int cpuRT) {
		this.at -= cpuRT;
		if(at < 0)
			this.at = 0;
	}
	public void setibt() {
		this.ibt = this.bt;
	}
	public int getibt() {
		return this.ibt;
	}
	public void setctWT(int cpuRT) {
		if(getat() == 0)
			this.wt += cpuRT;
	}
	//Get total wait time
	public int gettwt(){
		return wt;
	}
	//update remaining burst time
	public void updaterBT() {
		this.bt -= quant;
		if(this.bt < 0) {
			this.bt = 0;
		}
	}
	//Return the remaining burst time
	public int getrBT() {
		return this.bt;
	}
	//update context switch counter
	public void updatecsc() {
		this.csc++;
	}
	//get context switch amount aka number of times a process is switched
	public int getcsc(){
		return this.csc;
	}
	//set turnaround time
	public void settat(){
		this.tat = this.gettwt() + this.getibt();
	}
	//get turn around time
	public int gettat() {
		return this.tat;
	}
	//get current process info / properties
	public void getcpinfo() {

		Eventlog += "/////////////////////////////////// \n" + "Process ID: " + pid + "\n" + "Process remaining burst time: " + getrBT() + "\n" +
				"Time until ready to enter ready queue: " + getat() + "\n" + "Process ready to enter ready queue: " + isreadytoexe() + "\n" + 
				"Process Context switch: " + csc + "\n" + "Process total wait time: " + gettwt() + "\n" + "Process total turnaound time: " + gettat() + "\n" +
				"/////////////////////////////////// \n\n";
	}
	//check to see if the process is complete
	public int isprocesscomplete() {
		if(this.getrBT() == 0)
			return 1; //process complete
		else 
			return 0;//process incomplete
	}
	//check to see if the process is ready to be executed 
	public boolean isreadytoexe() {
		if(this.getat() == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	//Return the event log to the UI main class in order for it to be printed out in the GUI textarea
	public String getEventlog() {
		String regex = "\\s*\\bnull\\b\\s*";//Remove all null values in the string with empty space
		Eventlog = Eventlog.replaceAll(regex, "");
		return this.Eventlog;
	}
}
