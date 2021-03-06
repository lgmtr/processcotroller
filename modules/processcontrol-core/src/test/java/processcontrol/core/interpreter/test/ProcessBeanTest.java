package processcontrol.core.interpreter.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import processcontrol.core.interpreter.ProcessBean;
import processcontrol.core.interpreter.ProcessVariable;
import processcontrol.core.json.model.BPMNModel;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProcessBeanTest {

	@Test
	public void processBeanTestSimple() throws JsonParseException, JsonMappingException, IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		ObjectMapper mapper = new ObjectMapper();
		BPMNModel bpmnModel = mapper.readValue(classLoader.getResourceAsStream("test_files/example_process.json"), BPMNModel.class);
		Map<String, ProcessVariable> processVariables = new HashMap<String, ProcessVariable>();
		ProcessVariable processVariable = new ProcessVariable();
		processVariable.setValue(new Boolean(false));
		processVariable.setClassOfValue(Boolean.class);
		processVariables.put("complex", processVariable);
		ProcessBean processBean = new ProcessBean(bpmnModel, processVariables);
		processBean.start();
	}

	@Test
	public void processBeanTestComplex() throws JsonParseException, JsonMappingException, IOException, InterruptedException {
		ClassLoader classLoader = getClass().getClassLoader();
		ObjectMapper mapper = new ObjectMapper();
		BPMNModel bpmnModel = mapper.readValue(classLoader.getResourceAsStream("test_files/example_process.json"), BPMNModel.class);
		Map<String, ProcessVariable> processVariables = new HashMap<String, ProcessVariable>();
		ProcessVariable processVariable = new ProcessVariable();
		processVariable.setValue(new Boolean(true));
		processVariable.setClassOfValue(Boolean.class);
		processVariables.put("complex", processVariable);
		ProcessBean processBean = new ProcessBean(bpmnModel, processVariables);
		processBean.start();
	}
}
