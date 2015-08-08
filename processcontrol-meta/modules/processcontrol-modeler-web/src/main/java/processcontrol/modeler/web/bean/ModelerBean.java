package processcontrol.modeler.web.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.diagram.ConnectEvent;
import org.primefaces.event.diagram.ConnectionChangeEvent;
import org.primefaces.event.diagram.DisconnectEvent;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import processcontrol.modeler.web.diagram.model.DiagramElement;

@Component("modelerBean")
@Scope("session")
public class ModelerBean implements Serializable {

	@Resource
	private ApplicationContext applicationContext;

	private static final long serialVersionUID = 1L;

	private DefaultDiagramModel model;

	private List<DiagramElement> possibleSourceElements;

	private boolean startElementIsDisabled = false;

	private boolean otherElementsAreDisabled = true;

	private boolean suspendEvent;
	
	private String prozessName;
	
	private String prozessInJson;

	private int[] position = { 5, 20 };

	public DiagramModel getModel() {
		return model;
	}
	
	public void doCommand(){
		System.out.println("filename: " + prozessName);
		System.out.println(prozessInJson);
	}

	public void addStart() {
		model = new DefaultDiagramModel();
		model.setMaxConnections(-1);

		model.getDefaultConnectionOverlays().add(new ArrowOverlay(20, 20, 1, 1));
		StraightConnector connector = new StraightConnector();
		connector.setPaintStyle("{strokeStyle:'#98AFC7', lineWidth:2}");
		connector.setHoverPaintStyle("{strokeStyle:'#5C738B'}");
		model.setDefaultConnector(connector);

		Element startElement = new Element(new DiagramElement("Start", "start.png"), String.valueOf(position[0]) + "em",
				String.valueOf(position[1]) + "em");
		position[0] += 10;
		startElement.setDraggable(true);
		EndPoint endPointStart = createDotEndPoint(EndPointAnchor.RIGHT);
		endPointStart.setSource(true);
		startElement.addEndPoint(endPointStart);
		model.addElement(startElement);

		startElementIsDisabled = true;
		otherElementsAreDisabled = false;
		RequestContext.getCurrentInstance().update("@form");
	}

	public void addEnd() {
		Element endElement = new Element(new DiagramElement("End", "end.png"), String.valueOf(position[0]) + "em",
				String.valueOf(position[1]) + "em");
		endElement.setDraggable(true);
		EndPoint endPointStart = createDotEndPoint(EndPointAnchor.LEFT);
		endPointStart.setTarget(true);
		endElement.addEndPoint(endPointStart);
		model.addElement(endElement);
		startElementIsDisabled = true;
		otherElementsAreDisabled = true;
		RequestContext.getCurrentInstance().update("@form");
	}

	public void newModel() {
		startElementIsDisabled = false;
		otherElementsAreDisabled = true;
		model = new DefaultDiagramModel();
		model.setMaxConnections(-1);

		model.getDefaultConnectionOverlays().add(new ArrowOverlay(20, 20, 1, 1));
		StraightConnector connector = new StraightConnector();
		connector.setPaintStyle("{strokeStyle:'#98AFC7', lineWidth:2}");
		connector.setHoverPaintStyle("{strokeStyle:'#5C738B'}");
		model.setDefaultConnector(connector);

		RequestContext.getCurrentInstance().update("@form");
	}

	private EndPoint createDotEndPoint(EndPointAnchor anchor) {
		DotEndPoint endPoint = new DotEndPoint(anchor);
		endPoint.setScope("network");
		endPoint.setTarget(true);
		endPoint.setStyle("{fillStyle:'#98AFC7'}");
		endPoint.setHoverStyle("{fillStyle:'#5C738B'}");

		return endPoint;
	}

	public void onConnect(ConnectEvent event) {
		if (!suspendEvent) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Connected", "From " + event.getSourceElement().getData()
					+ " To " + event.getTargetElement().getData());

			FacesContext.getCurrentInstance().addMessage(null, msg);

			RequestContext.getCurrentInstance().update("@form:msgs");
		} else {
			suspendEvent = false;
		}
	}

	public void onDisconnect(DisconnectEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Disconnected", "From " + event.getSourceElement().getData()
				+ " To " + event.getTargetElement().getData());

		FacesContext.getCurrentInstance().addMessage(null, msg);

		RequestContext.getCurrentInstance().update("@form:msgs");
	}

	public void onConnectionChange(ConnectionChangeEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Connection Changed", "Original Source:"
				+ event.getOriginalSourceElement().getData() + ", New Source: " + event.getNewSourceElement().getData()
				+ ", Original Target: " + event.getOriginalTargetElement().getData() + ", New Target: "
				+ event.getNewTargetElement().getData());

		FacesContext.getCurrentInstance().addMessage(null, msg);

		RequestContext.getCurrentInstance().update("@form:msgs");
		suspendEvent = true;
	}

	public void setPossibleSourceElements(List<DiagramElement> possibleSourceElements) {
		this.possibleSourceElements = possibleSourceElements;
	}

	public boolean isStartElementIsDisabled() {
		return startElementIsDisabled;
	}

	public void setStartElementIsDisabled(boolean startElementIsDisabled) {
		this.startElementIsDisabled = startElementIsDisabled;
	}

	public boolean isOtherElementsAreDisabled() {
		return otherElementsAreDisabled;
	}

	public void setOtherElementsAreDisabled(boolean otherElementsAreDisabled) {
		this.otherElementsAreDisabled = otherElementsAreDisabled;
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
