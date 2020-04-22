//import java.util.concurrent.Semaphore;

public class Process extends Thread {

	public int processID;
	ProcessState status = ProcessState.New;
	public boolean suspended;

	public Process(int m) {
		processID = m;
		suspended=false;
	}
	public boolean isSuspended()
	{
		return suspended;
	}

	@Override
	public void run() {
		this.setProcessState(this, ProcessState.Running);
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
	public void setSuspended(boolean value)
	{
		suspended=value;
		
	}

	private void process1() {

		OperatingSystem.writeFile.semWait(this);
		OperatingSystem.printText("Enter File Name: ");
		OperatingSystem.writeFile.semPost();
		
		OperatingSystem.readFile.semWait(this);
		OperatingSystem.print.semWait(this);
		OperatingSystem.printText(OperatingSystem.readFile(OperatingSystem.TakeInput()));
		OperatingSystem.readFile.semPost();
		OperatingSystem.print.semPost();

		setProcessState(this, ProcessState.Terminated);
	}

	private void process2() {

		OperatingSystem.print.semWait(this);
		OperatingSystem.printText("Enter File Name: ");
		OperatingSystem.print.semPost();

		OperatingSystem.textInput.semWait(this);
		String filename = OperatingSystem.TakeInput();
		OperatingSystem.textInput.semPost();

		OperatingSystem.print.semWait(this);
		OperatingSystem.printText("Enter Data: ");
		OperatingSystem.print.semPost();

		OperatingSystem.textInput.semWait(this);
		String data = OperatingSystem.TakeInput();
		OperatingSystem.textInput.semPost();

		OperatingSystem.writeFile.semWait(this);
		OperatingSystem.writefile(filename, data);
		OperatingSystem.writeFile.semPost();

		setProcessState(this, ProcessState.Terminated);
	}

	private void process3() {
		int x = 0;
		while (x < 301) {
			OperatingSystem.print.semWait(this);
			OperatingSystem.printText(x + "\n");
			OperatingSystem.print.semPost();
			
			x++;
		}
		setProcessState(this, ProcessState.Terminated);
	}

	private void process4() {

		int x = 500;
		while (x < 1001) {
			OperatingSystem.print.semWait(this);
			OperatingSystem.printText(x + "\n");
			OperatingSystem.print.semPost();

			x++;
		}
		setProcessState(this, ProcessState.Terminated);
	}

	private void process5() {

		OperatingSystem.print.semWait(this);
		OperatingSystem.printText("Enter LowerBound: ");
		OperatingSystem.print.semPost();

		OperatingSystem.textInput.semWait(this);
		String lower = OperatingSystem.TakeInput();
		OperatingSystem.textInput.semPost();
		
		OperatingSystem.print.semWait(this);
		OperatingSystem.printText("Enter UpperBound: ");
		OperatingSystem.print.semPost();
		
		OperatingSystem.textInput.semWait(this);
		String upper = OperatingSystem.TakeInput();
		OperatingSystem.textInput.semPost();

		int lowernbr = Integer.parseInt(lower);
		int uppernbr = Integer.parseInt(upper);
		String data = "";

		while (lowernbr <= uppernbr) {
			data += lowernbr++ + "\n";
		}
		
		OperatingSystem.writeFile.semWait(this);
		OperatingSystem.writefile("P5.txt", data);
		OperatingSystem.writeFile.semPost();
		
		setProcessState(this, ProcessState.Terminated);
	}

	public static void setProcessState(Process p, ProcessState s) {
		p.status = s;
		
		
	}

	public static ProcessState getProcessState(Process p) {
		return p.status;
	}
}
