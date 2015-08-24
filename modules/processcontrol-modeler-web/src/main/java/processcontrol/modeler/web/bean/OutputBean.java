package processcontrol.modeler.web.bean;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("outputBean")
@Scope("session")
public class OutputBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
}
