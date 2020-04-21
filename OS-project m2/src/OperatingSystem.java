import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class OperatingSystem {

	public static ArrayList<Thread> ProcessTable;
	// Scheduling algorithm: FCFS
	private static Queue<Thread> readyQ;
	// Blocked Queues foe each resource
	private static Queue<Thread> blockedQReadFile;
	private static Queue<Thread> blockedQWriteFile;
	private static Queue<Thread> blockedQPrint;
	private static Queue<Thread> blockedQTextInput;
	// Semaphores for each resource, initially they are available
	private static int semReadFile = 1;
	private static int semWriteFile = 1;
	private static int semPrint = 1;
	private static int semTextInput = 1;
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
		readyQ.add(p);
		Process.setProcessState(p, ProcessState.Ready);
		// p.start();

	}

	// Semaphores
	/**
	 * 
	 * @param p the process that wants to access the take the semaphore
	 */
	public static void semPrintWait(Process p) {
		if (semPrint == 1)
			semPrint--;
		else
			blockedQPrint.add(p);

	}

	public static void semReadFiletWait(Process p) {
		if (semReadFile == 1)
			semReadFile--;
		else
			blockedQReadFile.add(p);

	}

	public static void semWriteFileWait(Process p) {
		if (semWriteFile == 1)
			semWriteFile--;
		else
			blockedQWriteFile.add(p);

	}

	public static void semTextInputWait(Process p) {
		if (semTextInput == 1)
			semTextInput--;
		else
			blockedQTextInput.add(p);
	}

	/**
	 * 
	 * @param p the process that wants to post the semaphore
	 */
	public static void semPrintPost() {
		semPrint++;
		
		if (!blockedQPrint.isEmpty())
			readyQ.add(blockedQPrint.remove());
	}

	public static void semReadFilePost() {
		semReadFile++;
		
		if (!blockedQReadFile.isEmpty())
			readyQ.add(blockedQReadFile.remove());
	}

	public static void semWriteFilePost() {
		semWriteFile++;
		if (!blockedQWriteFile.isEmpty())
			readyQ.add(blockedQWriteFile.remove());
	}

	public static void semTextInputPost() {
		semTextInput++;
		if (!blockedQTextInput.isEmpty())
			readyQ.add(blockedQTextInput.remove());
	}

	/**
	 * Scheduler is an algorithm used to schedule between processes to ensure that
	 * all processes get to execute according to FCFS (First Come first Serve)
	 */
	public static void scheduler() {
		while (!readyQ.isEmpty() ) {
			Process p=(Process)readyQ.remove();
			p.run();

		}

	}

	public static void main(String[] args) {
		ProcessTable = new ArrayList<Thread>();
		readyQ = new LinkedList<Thread>();
		blockedQPrint = new LinkedList<Thread>();
		blockedQReadFile = new LinkedList<Thread>();
		blockedQTextInput = new LinkedList<Thread>();
		blockedQWriteFile = new LinkedList<Thread>();
		createProcess(1);
		createProcess(2);
		createProcess(3);
		createProcess(4);
		createProcess(5);
		scheduler();

	}
}
