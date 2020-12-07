package final_project;
import java.util.*;
import java.io.*;
/**Final Project, Java Implementation of Round Robin CPU Scheduling Algorithm
/**
 * Round Robin CPU Scheduling Algorithm Simulation (Class: Round_Robin.Java)
 * @author Justin Cook/ Jcook03266
 * 
 * Round_Robin class, this is where the processes are parsed from the input file and placed into arrays with their
 * proper information in order to be turned into process objects that retain all of this necessary information for later
 * tracking application and analysis. This class accepts the two fundamental inputs which are the time quantum and the input file that is parsed.
 */
public class Round_Robin {
	private String csvFile = null;//Reference our source text file from the 'Resource' folder of this repository
	private BufferedReader br = null;//buffered reader initialized
	private String line = "";//initialize our line variable
	private String delimiter = ",";//what character we use to separate each column
	private String headers;//where we store the headers of this file
	private int pid[] = null;// process id's 
	private int bt[] = null;// Burst time of all processes 
	private int at[] = null;// Arrival times for each individual process
	private int quant; // Time quantum 
	private int i = 0;//Our indexer / counter for the looping of the parser 
	private ArrayList<Process> Process_Array = new ArrayList<Process>();//Process array, an array of our processes to export to the other classes
	private String Eventlog;//Event log string that keeps track of all information printed and exports it to be printed via the gui textarea
	//End of initialization


	//Create Round Robin object that accepts the two required fields, the time quantum and the csv file
	public Round_Robin(String csvFile, int quant) {
		this.csvFile = csvFile;
		this.quant = quant;

		//Run the csv parser to parse our csv file and get back our valid parameters for the method after this 
		csvParser();
	}

	//Parser for CSV formatted .txt files
	public void csvParser() {
		/**Comma separated value file parser part 1: Find the size of the arrays for which we store these values
		 *Separate / parse the values in the file based on their division by the comma character
		 * For every line looped we add to our 'i' index counter value in order to specify the size of our arrays later on
		 */
		try {

			br = new BufferedReader(new FileReader(csvFile));
			headers = br.readLine(); //Make headers equal to a variable in order to reference the headers of the file later
			while ((line = br.readLine()) != null) {
				i++;
			}
			/**Necessary try and catch in order to see if the file is even there, and also close method to close off the reader
			 *In the latest editions of java you don't need to close off the bufferedreader as when you initialize a resource 
			 *inside of a try catch method then it then becomes try-resource where the close method is already invoked automatically
			 *to save you the trouble of manually closing it.
			 */
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//End of part 1

		//We initialize the arrays and our variables which we'll pass to the algorithm down the line
		// process id's 
		pid = new int[i];

		// Burst time of all processes 
		bt = new int[i]; 

		// Arrival times for each individual process
		at = new int[i];
		//End of initialization

		//We reset i in order to fill up our arrays, if we don't then index of bound error since
		i = 0;

		/**Comma separated value file parser part 2: Fill up our variables with the values in the file 
		 *Separate / parse the values in the file based on their division by the comma character
		 * For every line looped we add to our arrays using the column index value to reference which column we want to take from
		 * we put index value into our array for which position we need to index that referenced value at, and we count up our i again to fill up our arrays
		 */
		try {

			br = new BufferedReader(new FileReader(csvFile));
			headers = br.readLine(); //Make headers equal to a variable in order to reference the headers of the file later
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] data = line.split(delimiter);

				pid[i] =  Integer.parseInt(data[0]);
				at[i] =  Integer.parseInt(data[1]);
				bt[i] =  Integer.parseInt(data[2]);
				Process_Array.add(new Process(pid[i],at[i],bt[i],quant));

				Eventlog += " \nParsed Group " + (i+1) + ": \n" + " Process ID: " + pid[i] + "\n Arrival Time: " + at[i] + "\n Burst time: " + bt[i] + "\n\n";
				i++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//End of part 2	
	}

	//Return the process array to static methods
	public ArrayList<Process> getProcessArray(){
		return this.Process_Array;
	}

	//Return the time quantum value to static methods
	public int getquantum() {
		return this.quant;
	}

	//Return the event log string to the GUI to print all operational information 
	public String getEventlog() {
		String regex = "\\s*\\bnull\\b\\s*";//Remove all null values in the string with empty space
		Eventlog = Eventlog.replaceAll(regex, "");
		return this.Eventlog;
	}

	//Main method 
	public static void main(String[] args) {
	}

}
