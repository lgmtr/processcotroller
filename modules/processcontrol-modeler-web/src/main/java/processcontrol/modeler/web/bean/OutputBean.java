package processcontrol.modeler.web.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("outputBean")
@ManagedBean
@Scope("request")
public class OutputBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
}
