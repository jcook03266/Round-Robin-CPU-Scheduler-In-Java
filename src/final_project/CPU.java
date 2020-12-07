package final_project;
/**Final Project, Java Implementation of Round Robin CPU Scheduling Algorithm
/**
 * Round Robin CPU Scheduling Algorithm Simulation (Class: CPU.Java)
 * @author Justin Cook/ Jcook03266
 * 
 * CPU Class, In this class we execute the processes and see if their remaining burst times are less than, greater than or 
 * equal to the time quantum value, if any, we update the remaining burst time, mark the processes as completed with the counter
 * and return the time quantum value or the remaining burst time depending on the condition.
 */
public class CPU {
	int quant;//Time quantum
	int cpc;//Completed process counter, used to eventually calculate throughput 

	public CPU(int quant) {
		this.quant = quant;//set the time quantum value passed to this class
		cpc = 0; //current completed processes is defaulted to 0
	}

	//We execute the process by checking the remaining burst time and seeing if it's > < or =  time quantum 
	public int execute(Process p) {
		//If rBT is > time quantum then we return time quantum, and update the rBT
		if(p.getrBT() > quant) {
			p.updaterBT();//update the remaining burst time
			return quant; //Time quantum, i.e the time the cpu has spent running the process for the quantum
		}
		//If rBT is < time quantum then we return the remaining burst time, add to completed process counter and update the rBT
		else if(p.getrBT() < quant) {
			int rBT = p.getrBT();//Remaining burst time stored from current process object
			p.updaterBT();//Update remaining burst time
			cpc++;//Count up completed process counter to mark another completed process
			return rBT;//Return the remaining burst time 
		}
		else //If the remaining burst time is equal to the time quantum then we return time quantum, add to the completed process counter and update the remaining burst time
		{
			p.updaterBT();//Update remaining burst time
			cpc++;//Count up completed process counter to mark another completed process
			return quant;//Return time quantum value
		}
	}

	//We use the number of completed processes returned by this function to later compute the throughput
	public int getcpc() {
		return cpc;//return number of completed processes 
	}
}


