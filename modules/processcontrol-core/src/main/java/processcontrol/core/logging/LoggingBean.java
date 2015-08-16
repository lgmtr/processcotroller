package processcontrol.core.logging;

import java.io.Serializable;
import java.util.LinkedList;

import org.springframework.stereotype.Service;

@Service
public class LoggingBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public class Log {
		private String logTime;
		private String commandText;
		private String thread;
		
		public Log(String time, String commandText, String thread) {
			this.logTime = time;
			this.commandText = commandText;
			this.thread = thread;
		}
		
		public String getLogTime() {
			return logTime;
		}
		public void setLogTime(String logTime) {
			this.logTime = logTime;
		}
		public String getCommandText() {
			return commandText;
		}
		public void setCommandText(String commandText) {
			this.commandText = commandText;
		}
		public String getThread() {
			return thread;
		}
		public void setThread(String thread) {
			this.thread = thread;
		}
	}
	
	private LinkedList<Log> logList = new LinkedList<Log>();

	public LinkedList<Log> getLogList() {
		return logList;
	}

	public void addLogList(Log log) {
		this.logList.addLast(log);
		if(logList.size() > 50){
			do{
				logList.removeFirst();
			}while(logList.size()<40);
		}
	}
	
	
	
}
