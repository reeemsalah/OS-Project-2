import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class OperatingSystem {

	public static ArrayList<Thread> ProcessTable;

	// Semaphores for each resource, initially they are available
	public static MySemaphore readFile = new MySemaphore();
	public static MySemaphore writeFile = new MySemaphore();
	public static MySemaphore print = new MySemaphore();
	public static MySemaphore textInput = new MySemaphore();
	// counter for the number of active processes
	public static int activeProcess = 0;

	// system calls:
	// 1- Read from File
	@SuppressWarnings("unused")
	public static String readFile(String name) {
		String Data = "";
		File file = new File(name);
		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				Data += scan.nextLine() + "\n";
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return Data;
	}

	// 2- Write into file
	@SuppressWarnings("unused")
	public static void writefile(String name, String data) {
		try {
			BufferedWriter BW = new BufferedWriter(new FileWriter(name));
			BW.write(data);
			BW.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	// 3- print to console
	@SuppressWarnings("unused")
	public static void printText(String text) {

		System.out.println(text);

	}

	// 4- take input

	@SuppressWarnings("unused")
	public static String TakeInput() {
		Scanner in = new Scanner(System.in);
		String data = in.nextLine();
		return data;

	}

	private static void createProcess(int processID) {
		Process p = new Process(processID);
		ProcessTable.add(p);
		Process.setProcessState(p, ProcessState.Ready);
		// p.start();

	}

	/**
	 * Scheduler is an algorithm used to schedule between processes to ensure that
	 * all processes get to execute according to FCFS (First Come first Serve)
	 */
	public static void scheduler() {
		while (!ProcessTable.isEmpty()) {

			Process p = (Process) ProcessTable.get(0);

		if (p.isSuspended()) {
				System.out.println("Process " + p.processID + " resumes");
				p.setSuspended(false);
				p.resume();
				p.setProcessState(p, ProcessState.Running);
			} else {
				System.out.println("Process " + p.processID + " starts");
				p.start();
		}
				while (true) {
					if (Process.getProcessState(p) == ProcessState.Waiting) {
						System.out.println("Process " + p.processID + " is suspended");
						
						ProcessTable.remove(p);
						break;
					}
					if (Process.getProcessState(p) == ProcessState.Terminated) {
						System.out.println("Process " + p.processID + " finishes");

						ProcessTable.remove(p);
						break;
					}

				}
			

		}

	}

	public static void main(String[] args) {
		ProcessTable = new ArrayList<Thread>();

		createProcess(1);
		createProcess(2);
		createProcess(3);
		createProcess(4);
		createProcess(5);

		System.out.println(ProcessTable);
		scheduler();

	}
}
