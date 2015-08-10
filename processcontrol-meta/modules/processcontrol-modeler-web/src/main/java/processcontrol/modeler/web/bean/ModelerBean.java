package processcontrol.modeler.web.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import processcontrol.core.json.model.BPMNModel;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("modelerBean")
@Scope("session")
public class ModelerBean implements Serializable {

	@Resource
	private ApplicationContext applicationContext;

	private static final long serialVersionUID = 1L;

	private String prozessName;
	
	private String prozessInJson;

	public void doCommand(){		
		ObjectMapper mapper = new ObjectMapper();
		try {
			BPMNModel bpmnModel = mapper.readValue(prozessInJson, BPMNModel.class);
			System.out.println(bpmnModel.getNodeDataArray().get(0).getCategory());
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
