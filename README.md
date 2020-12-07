First off,
///////What is CPU Scheduling?/////////////////////////////////
CPU scheduling at the fundamental level is a method of assigning work 
to specific resources that will then complete said work, i.e cores on a cpu.
This work can be entirely virtual implementations of computational instances 
such as processes and threads and data flows of some sort. These virtual 
implementations are then scheduled onto hardware resources where they're then 
handled via the appropriate execution methods, these hardware resources can 
be comprised of processor cores much like I mentioned earlier, expansion cards i.e
graphics cards and even network links.

///////Why do we need CPU Scheduling?/////////////////////////////////
We use cpu scheduling for mainly efficiency purposes, we schedule tasks to 
be handled later on down the line in order to prevent cpu from idling and wasting 
precious cycles it could be using for doing essential work. This efficiency is then
used to make the system faster and more responsive as it can handle more by doing less,
hence scheduling, much like a form of procrastination but necessary procrastination.
The operating system has to select one of the process scheduled in the ready queue to be
executed. The scheduler selects from an assortment of processes in memory that are ready to be 
executed and allocated the cpu to one of them. Now the allocation process is up to the 
scheduling algorithm, this is where Round Robin and many other algorithms come into play.

///////What is the Round Robin Scheduling algorithm and how does it work?/////////////////////////////////
The Round Robin Scheduling algorithm is a scheduling algorithm utilized by process and 
network schedulers in computing. In this algorithm there are time slices aka time quantum that
are equally cut up and assigned to each process in a circular order. These processes are handled in a circular
order meaning one after the other and one before another, without priority designation to certain processes
unlike other algorithms which we won't discuss here, this process is known as cyclic executive, meaning there is
only one unified task, not many others with different priorities over one another. Round robin is a rather 
simple scheduling algorithm and is easy to implement with it being void of any risk of starvation, with 
starvation referring to resource starvation, starvation is a problem encountered in concurrent computing where 
a process is perpetually (endlessly) denied the necessary resources to process its work. Think of resource 
starvation like your printer refusing to print out the test page you need for your final exam, it can be 
pretty disastrous.

///////How was this algorithm simulated in Java?/////////////////////////////////
In Java, you implement Round Robin easily with the many libraries available. In terms
of hard coding the functionality you break the scheduling algorithm into 5 parts:
--------------------------------------------------------------------------------------
<Look at source code for the appropriate methods and variables and java libraries used in each class>

1.) CPU Class-
In this class we execute the processes and see if their remaining burst times are less than, greater than or 
equal to the time quantum value, if any, we update the remaining burst time, mark the processes as completed with the counter
and return the time quantum value or the remaining burst time depending on the condition.

2.) Clock Class-
Clock Class, In this class we update the time elapsed since the start of the simulation, we update the arrival time of 
a process waiting to get into the ready queue, as well as the wait time of a process already in the ready queue by using an iterator
and then setting the wait time of the current cpu

3.) Process Class-
Process class enables the creation of our process objects which use like trading cards with the other
classes, with clock and cpu enabling the tracking of these processes and the statistics attributed to them 
via later analysis performed in the execution class. All of the process objects start off here.

4.) Execution Class-
This is where all of our process objects go to get executed and analyzed while being executed
by the round robin scheduling algorithm. We basically input a process into the scheduling algorithm where we track
whether or not it has been completed or not, its position in the ready queue, whether it's ready to go into the ready
queue or come out of it to be executed, and if it has to go back into the queue and popped out from the head of the queue once more. This class is pretty 
self explanatory, it executes the processes using RR.

5.) Main Class-
This is where the processes are parsed from the input file and placed into arrays with their
proper information in order to be turned into process objects that retain all of this necessary information for later
tracking application and analysis. This class accepts the two fundamental inputs which are the time quantum and the input file that is parsed.

These are the fundamental parts of the basic algorithm implementation.
To further simplify simulating and debugging this scheduling algorithm we can design a basic UI to control our inputs, launch the simulation, and even save our session's data and quit at any time with the ability to restart the simulation after one has already been completed.


This is the 6th Class that bridges all of the others together
6.) GUI Class-
Basic GUI that allows the user to interact with the round robin algorithm with simplicity
The user simply provides the path directory to the csv format file that's required by this implementation of 
the scheduler via typing it directly, or by importing it, and gives the time quantum value by sliding the slider from 1 - 15 at an interval of 1. The results
of the algorithm are printed out to the custom 'Event log' console located on the right half of the JFrame, with these
results being able to be saved to a text file named 'RRSimEventLog.txt' located on your desktop by default. You can
restart the simulation as many times as you'd like and provide multiple different time quantum values for each run.
Note: For the csv file input, you can simply remove the placeholder text around Resources/data.txt and use only that
no quotations or any other characters, as your input path as the default csv file is located via that path. 

<Look at source code for the appropriate methods and variables and java libraries used in each class>
 
-Note: In this implementation of the algorithm we use CSV formatted files to parse the process information and feed it to our
backend, comma separated value formatted .txt files are simple and efficient ways of going about this process without 
overcomplicating things and enabling more room for error. We can even use headers in this format to further organize our data even more.

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
Now that we've covered the basics of how to implement Round Robin in Java, we can now address how to run this implementation:

1.) Click on the Round Robin.jar file to execute the program.

2.) Input the path the csv file you wish to use, by default this program comes with a default CSV file already linked,
just copy the Resources/data.txt from the placeholder text without quotations, press the clear button directly below the input bar and paste it back into
the input bar. Or you can just copy the path of the file you want to use, or just click import and then browse your system for the file
which is the recommended method of going about this.

3.) Press the validate button below the input bar to validate that the file is indeed linked correctly and that is in the CSV format.

4.) After linking the file and validating it we now go to the time quantum value input section and simply drag the slider to whichever value
we'd like, by default the value is set to 15 which is the middle value of the slider. It's recommended you use lower values below '10' for 
better results, anything above that results in uncertain analysis depending on the data input.

5.) After you set the time quantum value you simply press start simulation and sit back while everything is displayed in the event log on the right

6.) Once the simulation is finished you have the options of: saving the contents of the event log to a file named 'RRSimEventLog.txt' that will
be automatically saved to your desktop, exiting the program, clearing the event log, and or restarting the simulation using different criteria or the same
criteria depending on what you want, it's up to you.

*And this concludes the tutorial on how to run and simulate the Round Robin CPU scheduling algorithm implemented in Java* 
*And this also concludes the minor walkthrough on cpu scheduling and Round Robin, if you're interested in this topic even more
I recommend exploring more operating systems based ideas and looking at other scheduling algorithms*

-Thank you for reading! - Justin Cook / Jcook03266
///////////////////////////////////////////////////////////////////////////////////////////////////////////////




  
 
 
 
 
 
 
 
 
 
 
 