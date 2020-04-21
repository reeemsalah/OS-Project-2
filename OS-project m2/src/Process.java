//import java.util.concurrent.Semaphore;

public class Process extends Thread {

	public int processID;
	ProcessState status = ProcessState.New;

	public Process(int m) {
		processID = m;
	}

	@Override
	public void run() {
		this.setProcessState(this, ProcessState.Running);
		System.out.println("Process "+this.processID+" is"+this.status);
		switch (processID) {
		case 1:
			process1();
			break;
		case 2:
			process2();
			break;
		case 3:
			process3();
			break;
		case 4:
			process4();
			break;
		case 5:
			process5();
			break;
		}

	}

	private void process1() {

		OperatingSystem.semPrintWait(this);
		OperatingSystem.printText("Enter File Name: ");
		OperatingSystem.semPrintPost();

		OperatingSystem.semReadFiletWait(this);
		OperatingSystem.semPrintWait(this);
		OperatingSystem.printText(OperatingSystem.readFile(OperatingSystem.TakeInput()));
		OperatingSystem.semReadFilePost();
		OperatingSystem.semPrintPost();

		setProcessState(this, ProcessState.Terminated);
	}

	private void process2() {

		OperatingSystem.semPrintWait(this);
		OperatingSystem.printText("Enter File Name: ");
		OperatingSystem.semPrintPost();

		OperatingSystem.semTextInputWait(this);
		String filename = OperatingSystem.TakeInput();
		OperatingSystem.semTextInputPost();

		OperatingSystem.semPrintWait(this);
		OperatingSystem.printText("Enter Data: ");
		OperatingSystem.semPrintPost();

		OperatingSystem.semTextInputWait(this);
		String data = OperatingSystem.TakeInput();
		OperatingSystem.semTextInputPost();

		OperatingSystem.semWriteFileWait(this);
		OperatingSystem.writefile(filename, data);
		OperatingSystem.semWriteFilePost();

		setProcessState(this, ProcessState.Terminated);
	}

	private void process3() {
		int x = 0;
		while (x < 301) {
			OperatingSystem.semPrintWait(this);
			OperatingSystem.printText(x + "\n");
			OperatingSystem.semPrintPost();
			
			x++;
		}
		setProcessState(this, ProcessState.Terminated);
	}

	private void process4() {

		int x = 500;
		while (x < 1001) {
			OperatingSystem.semPrintWait(this);
			OperatingSystem.printText(x + "\n");
			OperatingSystem.semPrintPost();

			x++;
		}
		setProcessState(this, ProcessState.Terminated);
	}

	private void process5() {

		OperatingSystem.semPrintWait(this);
		OperatingSystem.printText("Enter LowerBound: ");
		OperatingSystem.semPrintPost();

		OperatingSystem.semTextInputWait(this);
		String lower = OperatingSystem.TakeInput();
		OperatingSystem.semTextInputPost();
		
		OperatingSystem.semPrintWait(this);
		OperatingSystem.printText("Enter UpperBound: ");
		OperatingSystem.semPrintPost();
		
		OperatingSystem.semTextInputWait(this);
		String upper = OperatingSystem.TakeInput();
		OperatingSystem.semTextInputPost();

		int lowernbr = Integer.parseInt(lower);
		int uppernbr = Integer.parseInt(upper);
		String data = "";

		while (lowernbr <= uppernbr) {
			data += lowernbr++ + "\n";
		}
		
		OperatingSystem.semWriteFileWait(this);
		OperatingSystem.writefile("P5.txt", data);
		OperatingSystem.semWriteFilePost();
		
		setProcessState(this, ProcessState.Terminated);
	}

	public static void setProcessState(Process p, ProcessState s) {
		p.status = s;
		if (s == ProcessState.Terminated) {
			OperatingSystem.ProcessTable.remove(OperatingSystem.ProcessTable.indexOf(p));
		}
		
	}

	public static ProcessState getProcessState(Process p) {
		return p.status;
	}
}
