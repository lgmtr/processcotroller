package processcontrol.modeler.web.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import processcontrol.core.interpreter.ProcessBean;
import processcontrol.core.interpreter.ProcessVariable;
import processcontrol.core.json.model.BPMNModel;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("modelerBean")
@Scope("session")
public class ModelerBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Resource
	private ApplicationContext applicationContext;
	
	private String prozessName;
	
	private String prozessInJson;
	
	private BPMNModel bpmnModel;
	
	public void simple(){
		save();
		Map<String, ProcessVariable> processVariables = new HashMap<String, ProcessVariable>();
		ProcessVariable processVariable = new ProcessVariable();
		processVariable.setValue(new Boolean(false));
		processVariable.setClassOfValue(Boolean.class);
		processVariables.put("complex", processVariable);
		ProcessBean processBean = new ProcessBean(bpmnModel, processVariables);
		processBean.start();
	}
	
	public void complex(){
		save();
		Map<String, ProcessVariable> processVariables = new HashMap<String, ProcessVariable>();
		ProcessVariable processVariable = new ProcessVariable();
		processVariable.setValue(new Boolean(true));
		processVariable.setClassOfValue(Boolean.class);
		processVariables.put("complex", processVariable);
		ProcessBean processBean = new ProcessBean(bpmnModel, processVariables);
		processBean.start();
	}
	
	public void save(){		
		ObjectMapper mapper = new ObjectMapper();
		try {
			bpmnModel = mapper.readValue(prozessInJson, BPMNModel.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getProzessName() {
		return prozessName;
	}

	public void setProzessName(String prozessName) {
		this.prozessName = prozessName;
	}

	public String getProzessInJson() {
		return prozessInJson;
	}

	public void setProzessInJson(String prozessInJson) {
		this.prozessInJson = prozessInJson;
	}

}
