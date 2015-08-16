package processcontrol.modeler.web.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import processcontrol.core.logging.LoggingBean;
import processcontrol.core.logging.LoggingBean.Log;

@Component("outputBean")
@Scope("session")
public class OutputBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Resource
	private LoggingBean loggingBean;
	
	public List<Log> getLog(){
		return loggingBean.getLogList();
	}

}
