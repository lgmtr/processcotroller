package processcontrol.core.interpreter.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import processcontrol.core.interpreter.ProcessBean;
import processcontrol.core.interpreter.ProcessVariable;
import processcontrol.core.json.model.BPMNModel;

public class ProcessBeanTest {
	
	@Test
	public void processBeanTestSimple() throws JsonParseException, JsonMappingException, IOException{
		ClassLoader classLoader = getClass().getClassLoader();
		ObjectMapper mapper = new ObjectMapper();
		BPMNModel bpmnModel = mapper.readValue(classLoader.getResourceAsStream("test_files/example_process.json"), BPMNModel.class);
		Map<String, ProcessVariable> processVariables = new HashMap<String, ProcessVariable>();;
		ProcessVariable processVariable = new ProcessVariable();
		processVariable.setValue(new Boolean(false));
		processVariable.setClassOfValue(Boolean.class);
		processVariables.put("complex", processVariable);
		Thread t = new Thread(new ProcessBean(bpmnModel, processVariables));
		t.start();
	}
	
	@Test
	public void processBeanTestComplex() throws JsonParseException, JsonMappingException, IOException{
		ClassLoader classLoader = getClass().getClassLoader();
		ObjectMapper mapper = new ObjectMapper();
		BPMNModel bpmnModel = mapper.readValue(classLoader.getResourceAsStream("test_files/example_process.json"), BPMNModel.class);
		Map<String, ProcessVariable> processVariables = new HashMap<String, ProcessVariable>();;
		ProcessVariable processVariable = new ProcessVariable();
		processVariable.setValue(new Boolean(true));
		processVariable.setClassOfValue(Boolean.class);
		processVariables.put("complex", processVariable);
		Thread t = new Thread(new ProcessBean(bpmnModel, processVariables));
		t.start();
	}
	
	@Test
	public void processBeanStressTestComplex() throws JsonParseException, JsonMappingException, IOException, InterruptedException{
		ClassLoader classLoader = getClass().getClassLoader();
		ObjectMapper mapper = new ObjectMapper();
		BPMNModel bpmnModel = mapper.readValue(classLoader.getResourceAsStream("test_files/example_process.json"), BPMNModel.class);
		Map<String, ProcessVariable> processVariables = new HashMap<String, ProcessVariable>();;
		ProcessVariable processVariable = new ProcessVariable();
		processVariable.setValue(new Boolean(true));
		processVariable.setClassOfValue(Boolean.class);
		processVariables.put("complex", processVariable);
		Thread[] t = new Thread[100];
 		for(int i = 0; i <= 99; i++){
 			t[i] = new Thread(new ProcessBean(bpmnModel, processVariables));
 			t[i].start();
 		}
 		for(int i = 0; i <= 99; i++){
 			t[i].join();
 		}
	}

}
