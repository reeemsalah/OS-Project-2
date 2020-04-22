import java.util.LinkedList;
import java.util.Queue;

public class MySemaphore {
	public int count;
	public Queue<Thread> blockedQ;

	public MySemaphore() {
		count = 1;
		blockedQ = new LinkedList<Thread>();
	}

	/**
	 * 
	 * @param p the process that wants to access the take the semaphore
	 */
	public void semWait(Process p) {
		
		if(count==1)
		{
			count--;
			
		}
		else
		{
			p.setProcessState(p, ProcessState.Waiting);
			blockedQ.add(p);
		}

	}

	/**
	 * 
	 * @param p the process that wants to post the semaphore
	 */
	public void semPost() {
		
		if (!blockedQ.isEmpty()) {
			Process p = (Process) blockedQ.remove();
			OperatingSystem.ProcessTable.add(p);

		}
		else
		{
			count=1;
		}

	}
}
