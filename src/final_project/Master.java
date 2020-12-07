package final_project;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**Final Project, Java Implementation of Round Robin CPU Scheduling Algorithm
/**
 * Round Robin CPU Scheduling Algorithm Simulation (Class: Master.Java)
 * @author Justin Cook/ Jcook03266
 * 
 * Basic GUI that allows the user to interact with the round robin algorithm with simplicity
 * The user simply provides the path directory to the csv format file that's required by this implementation of 
 * the scheduler via typing it directly, or by importing it, and gives the time quantum value by sliding the slider from 1 - 15 at an interval of 1. The results
 * of the algorithm are printed out to the custom 'Event log' console located on the right half of the JFrame, with these
 * results being able to be saved to a text file named 'RRSimEventLog.txt' located on your desktop by default. You can
 * restart the simulation as many times as you'd like and provide multiple different time quantum values for each run.
 * Note: For the csv file input, you can simply remove the placeholder text around Resources/data.txt and use only that
 * no quotations or any other characters, as your input path as the default csv file is located via that path. 
 */

/*
@SuppressWarnings("serial")
 * 
 */
public class Master extends JFrame {
	private int quant = 15;//Default middle slider value for the time quantum value

	public Master() {
		//border styles
		Border blackline = BorderFactory.createLineBorder(Color.black,1);
		Border whiteline = BorderFactory.createLineBorder(Color.white,1);
		Border grayline = BorderFactory.createLineBorder(Color.gray,1);
		Border redline = BorderFactory.createLineBorder(Color.red,1);

		//Fonts 
		Font f1 = new Font("Helvetica",Font.PLAIN, 10);
		Font f2 = new Font("Helvetica",Font.PLAIN, 15);
		Font f3 = new Font("Raleway", Font.BOLD, 12);
		Font f4 = new Font("Raleway", Font.BOLD, 18);

		//JTextarea that displays the title of the program 
		JPanel Interior_Frame_Left_Header_txt = new JPanel();
		Interior_Frame_Left_Header_txt.setLayout(new BorderLayout());
		Interior_Frame_Left_Header_txt.setBounds(25,0,450,25); 
		Interior_Frame_Left_Header_txt.setBorder(blackline);
		Interior_Frame_Left_Header_txt.setBackground(Color.WHITE);
		//End of Program title header

		//JTextarea that displays the title of the program 
		JTextArea Header = new JTextArea();
		Interior_Frame_Left_Header_txt.add(Header);
		Header.setEditable(false);
		Header.setBackground(Color.BLACK);
		Header.setFont(f4);
		Header.setForeground(Color.RED);
		add(Interior_Frame_Left_Header_txt);
		Header.append("//////Round Robin CPU Scheduling Algorithm Sim///////");
		//End of Program title header

		//Start of csv file input console object
		//This part of the UI is very important so it has to be painted on top of everything else to avoid UI interference
		JPanel Interior_Frame_Left_Interior_Header_field = new JPanel();
		Interior_Frame_Left_Interior_Header_field.setLayout(new BorderLayout());
		Interior_Frame_Left_Interior_Header_field.setBounds(80,85,340,25);  
		Interior_Frame_Left_Interior_Header_field.setBorder(redline);
		Interior_Frame_Left_Interior_Header_field.setBackground(Color.DARK_GRAY);

		//JTextarea that displays the read out of the algorithm
		JTextArea csvfield = new JTextArea();
		Interior_Frame_Left_Interior_Header_field.add(csvfield);
		csvfield.setEditable(true);
		csvfield.setBackground(Color.DARK_GRAY);
		csvfield.setFont(f3);
		csvfield.setForeground(Color.WHITE);
		add(Interior_Frame_Left_Interior_Header_field);
		csvfield.append("Enter the path to your csv file here ex.) 'Resources/data.txt'");
		//End of csv file input console object

		//Start of simulation interaction button set 
		//All of these buttons provide the functionality between the front end and back end of this program
		JPanel Interior_Frame_Left_Interior_Header_left_button = new JPanel();
		Interior_Frame_Left_Interior_Header_left_button.setLayout(new BorderLayout());
		Interior_Frame_Left_Interior_Header_left_button.setBounds(130,115,80,25); 
		Interior_Frame_Left_Interior_Header_left_button.setBorder(blackline);
		Interior_Frame_Left_Interior_Header_left_button.setBackground(Color.RED);

		//This button clears the CSV Input console bar so you don't have to do it manually
		JButton Clear_csv = new JButton("Clear");
		Clear_csv.setFont(f2);
		Clear_csv.setHorizontalAlignment(SwingConstants.CENTER);
		Clear_csv.setBackground(Color.RED);
		Clear_csv.setForeground(Color.WHITE);
		Interior_Frame_Left_Interior_Header_left_button.add(Clear_csv);
		add(Interior_Frame_Left_Interior_Header_left_button);

		JPanel Interior_Frame_Right_Interior_Header_Right_button = new JPanel();
		Interior_Frame_Right_Interior_Header_Right_button.setLayout(new BorderLayout());
		Interior_Frame_Right_Interior_Header_Right_button.setBounds(300,115,90,25);
		Interior_Frame_Right_Interior_Header_Right_button.setBorder(blackline);
		Interior_Frame_Right_Interior_Header_Right_button.setBackground(Color.RED);

		//This button validates that the file you've input is present, and that it is a readable csv file
		JButton validate_csv = new JButton("Validate");
		validate_csv.setFont(f2);
		validate_csv.setHorizontalAlignment(SwingConstants.CENTER);
		validate_csv.setBackground(Color.RED);
		validate_csv.setForeground(Color.WHITE);
		Interior_Frame_Right_Interior_Header_Right_button.add(validate_csv);
		add(Interior_Frame_Right_Interior_Header_Right_button);

		JPanel Interior_Frame_Right_Interior_Header_Center_button = new JPanel();
		Interior_Frame_Right_Interior_Header_Center_button.setLayout(new BorderLayout());
		Interior_Frame_Right_Interior_Header_Center_button.setBounds(210,115,90,25);
		Interior_Frame_Right_Interior_Header_Center_button.setBorder(blackline);
		Interior_Frame_Right_Interior_Header_Center_button.setBackground(Color.RED);

		//This button allows you to import a csv .txt file's path from your system's directory without having to find the link yourself
		JButton Import_csv = new JButton("Import");
		Import_csv.setFont(f2);
		Import_csv.setHorizontalAlignment(SwingConstants.CENTER);
		Import_csv.setBackground(Color.RED);
		Import_csv.setForeground(Color.WHITE);
		Interior_Frame_Right_Interior_Header_Center_button.add(Import_csv);
		add(Interior_Frame_Right_Interior_Header_Center_button);

		//This button starts the simulation, the most important part of this UI is this big shiny red button right here, so push it :)
		JPanel start_simulation_button_panel = new JPanel();
		start_simulation_button_panel.setLayout(new BorderLayout());
		start_simulation_button_panel.setBounds(125,300,250,25);;
		start_simulation_button_panel.setBorder(blackline);
		start_simulation_button_panel.setBackground(Color.BLACK);
		JButton start_simulation_button = new JButton("Start Simulation");
		start_simulation_button.setFont(f2);
		start_simulation_button.setHorizontalAlignment(SwingConstants.CENTER);
		start_simulation_button.setBackground(Color.RED);
		start_simulation_button.setForeground(Color.WHITE);
		start_simulation_button_panel.add(start_simulation_button);
		add(start_simulation_button_panel);

		//This button allows you to restart the simulation using different input values as many times as you'd like
		JPanel refresh_simulation_button_panel = new JPanel();
		refresh_simulation_button_panel.setLayout(new BorderLayout());
		refresh_simulation_button_panel.setBounds(125,325,250,25);;
		refresh_simulation_button_panel.setBorder(blackline);
		refresh_simulation_button_panel.setBackground(Color.BLACK);
		JButton refresh_simulation_button = new JButton("Restart Simulation");
		refresh_simulation_button.setFont(f2);
		refresh_simulation_button.setHorizontalAlignment(SwingConstants.CENTER);
		refresh_simulation_button.setBackground(Color.RED);
		refresh_simulation_button.setForeground(Color.WHITE);
		refresh_simulation_button_panel.add(refresh_simulation_button);
		refresh_simulation_button.setVisible(false);
		add(refresh_simulation_button_panel);

		//This button allows you to clear the event log text area in order to remove any unwanted information 
		JPanel clear_simulation_button_panel = new JPanel();
		clear_simulation_button_panel.setLayout(new BorderLayout());
		clear_simulation_button_panel.setBounds(125,350,250,25);;
		clear_simulation_button_panel.setBorder(blackline);
		clear_simulation_button_panel.setBackground(Color.RED);
		JButton clear_simulation_button = new JButton("Clear Event Log");
		clear_simulation_button.setFont(f2);
		clear_simulation_button.setHorizontalAlignment(SwingConstants.CENTER);
		clear_simulation_button.setBackground(Color.RED);
		clear_simulation_button.setForeground(Color.WHITE);
		clear_simulation_button_panel.add(clear_simulation_button);
		add(clear_simulation_button_panel);

		//This button allows you to save the event log text area to a file named RRsimEventLog.txt found on your system's default desktop
		JPanel save_simulation_button_panel = new JPanel();
		save_simulation_button_panel.setLayout(new BorderLayout());
		save_simulation_button_panel.setBounds(125,375,250,25);;
		save_simulation_button_panel.setBorder(blackline);
		save_simulation_button_panel.setBackground(Color.RED);
		JButton save_simulation_button = new JButton("Save Event Log");
		save_simulation_button.setFont(f2);
		save_simulation_button.setHorizontalAlignment(SwingConstants.CENTER);
		save_simulation_button.setBackground(Color.RED);
		save_simulation_button.setForeground(Color.WHITE);
		save_simulation_button_panel.add(save_simulation_button);
		add(save_simulation_button_panel);

		//And finally, this button, gives you the ability to quit the simulation whenever you'd like, but you love this simulation, why would you even want to? :)))
		JPanel exit_simulation_button_panel = new JPanel();
		exit_simulation_button_panel.setLayout(new BorderLayout());
		exit_simulation_button_panel.setBounds(125,400,250,25);;
		exit_simulation_button_panel.setBorder(blackline);
		exit_simulation_button_panel.setBackground(Color.RED);
		JButton exit_simulation_button = new JButton("Exit");
		exit_simulation_button.setFont(f2);
		exit_simulation_button.setHorizontalAlignment(SwingConstants.CENTER);
		exit_simulation_button.setBackground(Color.RED);
		exit_simulation_button.setForeground(Color.WHITE);
		exit_simulation_button_panel.add(exit_simulation_button);
		add(exit_simulation_button_panel);
		//End of simulation interaction button set 

		//Start of Time Quantum Value Slider object
		JPanel Interior_Frame_Left_Interior_Header_txt_2 = new JPanel();
		Interior_Frame_Left_Interior_Header_txt_2.setLayout(new BorderLayout());
		Interior_Frame_Left_Interior_Header_txt_2.setBounds(125,150,250,25);  
		Interior_Frame_Left_Interior_Header_txt_2.setBorder(blackline);
		Interior_Frame_Left_Interior_Header_txt_2.setBackground(Color.WHITE);

		//JTextarea that displays the header label of the Time Quantum value input bar
		JTextArea Header_4 = new JTextArea();
		Interior_Frame_Left_Interior_Header_txt_2.add(Header_4);
		Header_4.setEditable(false);
		Header_4.setBackground(Color.BLACK);
		Header_4.setFont(f4);
		Header_4.setForeground(Color.RED);
		add(Interior_Frame_Left_Interior_Header_txt_2);
		Header_4.append("Time Quantum Value Input");
		//End of time quantum slider header label

		//Slider container 
		JPanel time_quantum_value_panel = new JPanel();
		time_quantum_value_panel.setLayout(new BorderLayout());
		time_quantum_value_panel.setBackground(Color.BLACK);
		time_quantum_value_panel.setBounds(125,190,250,50); 
		JSlider time_quantum_value_slider = new JSlider(JSlider.HORIZONTAL,
				1, 30, 15);
		time_quantum_value_slider.setMinorTickSpacing(1);
		time_quantum_value_slider.setPaintTicks(true);
		time_quantum_value_slider.setPaintLabels(true);
		time_quantum_value_slider.setBackground(Color.BLACK);
		time_quantum_value_slider.setFont(f3);
		time_quantum_value_slider.setForeground(Color.red);
		Hashtable<Integer, JLabel> position = new Hashtable<Integer, JLabel>();
		position.put(1, new JLabel("1s"));
		position.put(15, new JLabel("15s"));
		position.put(30, new JLabel("30s"));
		time_quantum_value_slider.setLabelTable(position);   
		time_quantum_value_panel.add(time_quantum_value_slider);      
		add(time_quantum_value_panel);

		JPanel Interior_Frame_Left_Interior_Header_txt_3 = new JPanel();
		Interior_Frame_Left_Interior_Header_txt_3.setLayout(new BorderLayout());
		Interior_Frame_Left_Interior_Header_txt_3.setBounds(235,240,35,25); 
		Interior_Frame_Left_Interior_Header_txt_3.setBorder(blackline);
		Interior_Frame_Left_Interior_Header_txt_3.setBackground(Color.WHITE);

		//JTextarea that displays the time quantum provided either by user input or the slider
		JTextArea Header_5 = new JTextArea();
		Interior_Frame_Left_Interior_Header_txt_3.add(Header_5);
		Header_5.setEditable(false);
		Header_5.setBackground(Color.BLACK);
		Header_5.setFont(f4);
		Header_5.setForeground(Color.RED);
		add(Interior_Frame_Left_Interior_Header_txt_3);
		Header_5.append("15");
		//End of time quantum slider object

		//JTextarea that displays the header of the csv file path input bar
		//Header label that tells the user what file type to enter into the console 
		JPanel Interior_Frame_Left_Interior_Header_txt = new JPanel();
		Interior_Frame_Left_Interior_Header_txt.setLayout(new BorderLayout());
		Interior_Frame_Left_Interior_Header_txt.setBounds(160,50,170,25);  
		Interior_Frame_Left_Interior_Header_txt.setBorder(blackline);
		Interior_Frame_Left_Interior_Header_txt.setBackground(Color.WHITE);

		JTextArea Header_3 = new JTextArea();
		Interior_Frame_Left_Interior_Header_txt.add(Header_3);
		Header_3.setEditable(false);
		Header_3.setBackground(Color.BLACK);
		Header_3.setFont(f4);
		Header_3.setForeground(Color.RED);
		add(Interior_Frame_Left_Interior_Header_txt);
		Header_3.append("CSV File Path Input");

		JPanel Interior_Frame_Left_Header = new JPanel();
		Interior_Frame_Left_Header.setLayout(new BorderLayout());
		Interior_Frame_Left_Header.setBounds(0,0,500,25); 
		Interior_Frame_Left_Header.setBorder(blackline);
		Interior_Frame_Left_Header.setBackground(Color.BLACK);
		add(Interior_Frame_Left_Header);

		JPanel Interior_Frame_Left_Interior = new JPanel();
		Interior_Frame_Left_Interior.setLayout(new BorderLayout());
		Interior_Frame_Left_Interior.setBounds(25,45,450,400); 
		Interior_Frame_Left_Interior.setBorder(blackline);
		Interior_Frame_Left_Interior.setBackground(Color.BLACK);
		add(Interior_Frame_Left_Interior);

		JPanel Interior_Frame_Left = new JPanel();
		Interior_Frame_Left.setLayout(new BorderLayout());
		Interior_Frame_Left.setBounds(0,0,500,500); 
		Interior_Frame_Left.setBorder(redline);
		Interior_Frame_Left.setBackground(Color.RED);
		add(Interior_Frame_Left);
		//End of left side of JFrame 

		//Start of right side of JFrame 
		//Event log object header that displays the label of the Event log text area below 
		//JTextarea that displays the read out of the algorithm
		JPanel Interior_Frame_Right_Header_txt = new JPanel();
		Interior_Frame_Right_Header_txt.setLayout(new BorderLayout());
		Interior_Frame_Right_Header_txt.setBounds(700,0,100,25); 
		Interior_Frame_Right_Header_txt.setBorder(blackline);
		Interior_Frame_Right_Header_txt.setBackground(Color.WHITE);
		/**Important note for UI development:
		 *Since UI Elements are paint on top of one another you must initialize the object you want on top of the other
		 *first, thus we must put text and other labels on top of panels that act as borders and backgrounds for those text areas 
		 */

		JTextArea Header_2 = new JTextArea();
		Interior_Frame_Right_Header_txt.add(Header_2);
		Header_2.setEditable(false);
		Header_2.setBackground(Color.BLACK);
		Header_2.setFont(f4);
		Header_2.setForeground(Color.RED);
		add(Interior_Frame_Right_Header_txt);
		Header_2.append("Event Log");

		JPanel Interior_Frame_Right_Header = new JPanel();
		Interior_Frame_Right_Header.setLayout(new BorderLayout());
		Interior_Frame_Right_Header.setBounds(500,0,500,25); 
		Interior_Frame_Right_Header.setBorder(blackline);
		Interior_Frame_Right_Header.setBackground(Color.BLACK);
		add(Interior_Frame_Right_Header);

		//Event log object, where all the events you'd normally print out to the console are now redirected
		JPanel Interior_Frame_Right = new JPanel();
		Interior_Frame_Right.setLayout(new BorderLayout());
		Interior_Frame_Right.setBounds(500,25,500,475); 
		Interior_Frame_Right.setBorder(blackline);
		Interior_Frame_Right.setBackground(Color.BLACK);

		//JTextarea that displays the read out of the algorithm
		//The scroll bar is recolored to red to fit the colorway of the UI
		JTextArea Display = new JTextArea();
		Interior_Frame_Right.add(Display);
		Display.setEditable(false);
		Display.setBackground(Color.BLACK);
		Display.setFont(f3);
		Display.setForeground(Color.RED);
		JScrollPane Display_SP = new JScrollPane(Display);
		Display_SP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Display_SP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		Display_SP.setBackground(Color.BLACK);
		Interior_Frame_Right.add(Display_SP);
		Display_SP.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Color.RED;
			}
		});
		add(Interior_Frame_Right);
		//End of Event Log scroll pane styling 
		//End of Event Log object
		//End of right side of JFrame

		//Background of the JPanel, you need a basic panel taking up the entire frame as JFrame has borderlayout by default
		//Once you have this base panel, you can add other panels anywhere you like
		JPanel Interior_background = new JPanel();
		Interior_background.setLayout(new BorderLayout());
		Interior_background.setBounds(0,0,1000,500); //Default res is 1000x500 pixels
		Interior_background.setBorder(blackline);
		Interior_background.setBackground(Color.BLACK);
		add(Interior_background);

		//JFrame properties 
		setLocationRelativeTo(null);
		ImageIcon img = new ImageIcon("Resources/redrobin.ico");
		setIconImage(img.getImage());
		setTitle("Round Robin CPU Scheduling Algorithm Simulation");
		setBackground(Color.RED);
		setSize(1000,500);//Default and only resolution is 1000x500 (x) by (y)
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		//Action listeners////////////////////////////////////////////////////////////////////////////////
		//Button Action Listeners 
		//Clear CSV File path input Action Listener
		//Clears the input text area field for the csv file path  
		Clear_csv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				csvfield.setText("");
			}
		});
		//CSV File input Action Listener

		Import_csv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					csvfield.setText(selectedFile.getPath());
				}
			}
		});
		//END

		//CSV File validator Action Listener
		//Validates that the placed file path is valid 
		validate_csv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(csvfield.getText().isEmpty()){
					csvfield.setText("Please enter a file path!");
				}
				else {
					int i = 0;//Our indexer / counter for the looping of the parser 
					BufferedReader br = null;//buffered reader initialized
					String line = "";//initialize our line variable
					String delimiter = ",";//what character we use to separate each column
					String headers;//where we store the headers of this file
					int pid[] = null;// process id's 
					int bt[] = null;// Burst time of all processes 
					int at[] = null;// Arrival times for each individual process

					/**Comma separated value file parser part 1: Find the size of the arrays for which we store these values
					 *Separate / parse the values in the file based on their division by the comma character
					 * For every line looped we add to our 'i' index counter value in order to specify the size of our arrays later on
					 */
					try {
						Display.append("////////////////Validating CSV File////////////////  \n");
						Display.append("////////////////Parsing data from CSV File////////////////  \n\n");

						br = new BufferedReader(new FileReader(csvfield.getText()));
						headers = br.readLine(); //Make headers equal to a variable in order to reference the headers of the file later
						while ((line = br.readLine()) != null) {
							i++;
						}
						/**Necessary try and catch in order to see if the file is even there, and also close method to close off the reader
						 *In the latest editions of java you don't need to close off the bufferedreader as when you initialize a resource 
						 *inside of a try catch method then it then becomes try-resource where the close method is already invoked automatically
						 *to save you the trouble of manually closing it.
						 */
					} catch (FileNotFoundException g) {
						csvfield.setText("ERROR: CSV File Not Found, check path and remove quotes!");
						Display.append("////////DEBUG Stack Trace Print out//////// \n" + g.getStackTrace().toString() + "\n");
					} catch (IOException g) {
						csvfield.setText("ERROR: IO Exception encountered, check file!");
						Display.append("////////DEBUG Stack Trace Print out//////// \n" + g.getStackTrace().toString() + "\n\n");
					} finally {
						if (br != null) {
							try {
								br.close();
							} catch (IOException g) {
								csvfield.setText("ERROR: IO Exception encountered, check file!");
								Display.append("////////DEBUG Stack Trace Print out//////// \n" + g.getStackTrace().toString() + "\n\n");
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

					try {
						br = new BufferedReader(new FileReader(csvfield.getText()));
						headers = br.readLine(); //Make headers equal to a variable in order to reference the headers of the file later
						while ((line = br.readLine()) != null) {

							// use comma as separator
							String[] data = line.split(delimiter);

							pid[i] =  Integer.parseInt(data[0]);
							at[i] =  Integer.parseInt(data[1]);
							bt[i] =  Integer.parseInt(data[2]);

							Display.append(" \nParsed Group " + (i+1) + ": \n");
							Display.append(" Process ID: " + pid[i] + "\n Arrival Time: " + at[i] + "\n Burst time: " + bt[i] + "\n\n");
							i++;
						}

					} catch (FileNotFoundException f) {
						csvfield.setText("ERROR: CSV File Not Found, check path and remove quotes!");
						Display.append("////////DEBUG Stack Trace Print out//////// \n" + f.getStackTrace().toString() + "\n");
					} catch (IOException f) {
						csvfield.setText("ERROR: IO Exception encountered, check file!");
						Display.append("////////DEBUG Stack Trace Print out//////// \n" + f.getStackTrace().toString() + "\n\n");
					} finally {
						if (br != null) {
							try {
								br.close();
							} catch (IOException f) {
								csvfield.setText("ERROR: IO Exception encountered, check file!");
								Display.append("////////DEBUG Stack Trace Print out//////// \n" + f.getStackTrace().toString() + "\n\n");
							}
						}
					}	


				}
			}
		});
		//CSV File input Action Listener

		//Time Quantum slider state change listener, gets the values the user selects by sliding the slider and sets that to the Time Quantum value instance variable 'quant'
		time_quantum_value_slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				quant = ((JSlider)e.getSource()).getValue();
				Header_5.setText(Integer.toString(quant));
			}
		});
		//end

		//Start simulation button starts the simulation and reveals the refresh button in case you want to update any values live
		start_simulation_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if(csvfield.getText().isEmpty()){
					csvfield.setText("Please enter a file path!");
				}
				else {
					int i = 0;//Our indexer / counter for the looping of the parser 
					BufferedReader br = null;//buffered reader initialized
					String line = "";//initialize our line variable
					String delimiter = ",";//what character we use to separate each column
					String headers;//where we store the headers of this file
					int pid[] = null;// process id's 
					int bt[] = null;// Burst time of all processes 
					int at[] = null;// Arrival times for each individual process

					/**Comma separated value file parser part 1: Find the size of the arrays for which we store these values
					 *Separate / parse the values in the file based on their division by the comma character
					 * For every line looped we add to our 'i' index counter value in order to specify the size of our arrays later on
					 */
					try {
						Display.append("////////////////Validating CSV File////////////////  \n");
						Display.append("////////////////Parsing data from CSV File////////////////  \n\n");

						br = new BufferedReader(new FileReader(csvfield.getText()));
						headers = br.readLine(); //Make headers equal to a variable in order to reference the headers of the file later
						while ((line = br.readLine()) != null) {
							i++;
						}
						/**Necessary try and catch in order to see if the file is even there, and also close method to close off the reader
						 *In the latest editions of java you don't need to close off the bufferedreader as when you initialize a resource 
						 *inside of a try catch method then it then becomes try-resource where the close method is already invoked automatically
						 *to save you the trouble of manually closing it.
						 */
					} catch (FileNotFoundException g) {
						csvfield.setText("ERROR: CSV File Not Found, check path and remove quotes!");
						Display.append("////////DEBUG Stack Trace Print out//////// \n" + g.getStackTrace().toString() + "\n");
					} catch (IOException g) {
						csvfield.setText("ERROR: IO Exception encountered, check file!");
						Display.append("////////DEBUG Stack Trace Print out//////// \n" + g.getStackTrace().toString() + "\n\n");
					} finally {
						if (br != null) {
							try {
								br.close();
							} catch (IOException g) {
								csvfield.setText("ERROR: IO Exception encountered, check file!");
								Display.append("////////DEBUG Stack Trace Print out//////// \n" + g.getStackTrace().toString() + "\n\n");
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

					try {
						br = new BufferedReader(new FileReader(csvfield.getText()));
						headers = br.readLine(); //Make headers equal to a variable in order to reference the headers of the file later
						while ((line = br.readLine()) != null) {

							// use comma as separator
							String[] data = line.split(delimiter);

							pid[i] =  Integer.parseInt(data[0]);
							at[i] =  Integer.parseInt(data[1]);
							bt[i] =  Integer.parseInt(data[2]);

							Display.append(" \nParsed Group " + (i+1) + ": \n");
							Display.append(" Process ID: " + pid[i] + "\n Arrival Time: " + at[i] + "\n Burst time: " + bt[i] + "\n\n");
							i++;
						}

					} catch (FileNotFoundException f) {
						csvfield.setText("ERROR: CSV File Not Found, check path and remove quotes!");
						Display.append("////////DEBUG Stack Trace Print out//////// \n" + f.getStackTrace().toString() + "\n");
					} catch (IOException f) {
						csvfield.setText("ERROR: IO Exception encountered, check file!");
						Display.append("////////DEBUG Stack Trace Print out//////// \n" + f.getStackTrace().toString() + "\n\n");
					} finally {
						if (br != null) {
							try {
								br.close();
							} catch (IOException f) {
								csvfield.setText("ERROR: IO Exception encountered, check file!");
								Display.append("////////DEBUG Stack Trace Print out//////// \n" + f.getStackTrace().toString() + "\n\n");
							}
						}
					}	


				}
				Round_Robin RR = new Round_Robin(csvfield.getText(), quant);
				//Gets rid of the start button for styling purposes 
				Display.append("////////////////Validating CSV File////////////////  \n");
				Display.append("////////////////Parsing data from CSV File////////////////  \n\n");
				Display.append(RR.getEventlog());
				start_simulation_button.setVisible(false);

				Execution exe = new Execution(RR.getProcessArray(), quant);
				exe.ExeRRSim();

				Display.append(exe.getEventlog());
				Display.append("Simulation ran at time quantum: " + quant + "\n");
				Display.append("END OF SIMULATION \n\n\n\n");

				//Sets the button to visible
				refresh_simulation_button.setVisible(true);
			}
		});
		//end

		//refresh simulation button restarts the simulation
		refresh_simulation_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start_simulation_button.doClick();
			}
		});
		//end

		//Clears the event log
		clear_simulation_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText("");
			}
		});

		//Saves the event log to a txt file
		save_simulation_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				FileWriter f;
				try {
					f = new FileWriter(System.getProperty("user.home") + "\\Desktop\\RRSimEventLog.txt");
					Display.write(f);
					csvfield.setText("");
					csvfield.append("File saved as: " + System.getProperty("user.home") + "\\Desktop\\RRSimEventLog.txt");
				} catch (IOException e2) {
					Display.append("ERROR: Event log could not be saved at this time, try again \n");
					Display.append("Stack Trace DEBUG: \n");
					Display.append(e2.getStackTrace().toString());
				}
			}
		});

		//Exit button ends the program
		exit_simulation_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		//end
		//Action listeners////////////////////////////////////////////////////////////////////////////////
	}
	//Main 
	public static void main(String[]args) {
		Master Main_Frame = new Master();
	}
}
