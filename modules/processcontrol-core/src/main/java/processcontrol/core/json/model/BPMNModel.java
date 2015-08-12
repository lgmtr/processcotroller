
package processcontrol.core.json.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "class",
    "copiesArrays",
    "copiesArrayObjects",
    "linkFromPortIdProperty",
    "linkToPortIdProperty",
    "modelData",
    "nodeDataArray",
    "linkDataArray"
})
public class BPMNModel {

    @JsonProperty("class")
    private String _class;
    @JsonProperty("copiesArrays")
    private Boolean copiesArrays;
    @JsonProperty("copiesArrayObjects")
    private Boolean copiesArrayObjects;
    @JsonProperty("linkFromPortIdProperty")
    private String linkFromPortIdProperty;
    @JsonProperty("linkToPortIdProperty")
    private String linkToPortIdProperty;
    @JsonProperty("modelData")
    private ModelData modelData;
    @JsonProperty("nodeDataArray")
    private List<NodeDataArray> nodeDataArray = new ArrayList<NodeDataArray>();
    @JsonProperty("linkDataArray")
    private List<LinkDataArray> linkDataArray = new ArrayList<LinkDataArray>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The _class
     */
    @JsonProperty("class")
    public String getClass_() {
        return _class;
    }

    /**
     * 
     * @param _class
     *     The class
     */
    @JsonProperty("class")
    public void setClass_(String _class) {
        this._class = _class;
    }

    /**
     * 
     * @return
     *     The copiesArrays
     */
    @JsonProperty("copiesArrays")
    public Boolean getCopiesArrays() {
        return copiesArrays;
    }

    /**
     * 
     * @param copiesArrays
     *     The copiesArrays
     */
    @JsonProperty("copiesArrays")
    public void setCopiesArrays(Boolean copiesArrays) {
        this.copiesArrays = copiesArrays;
    }

    /**
     * 
     * @return
     *     The copiesArrayObjects
     */
    @JsonProperty("copiesArrayObjects")
    public Boolean getCopiesArrayObjects() {
        return copiesArrayObjects;
    }

    /**
     * 
     * @param copiesArrayObjects
     *     The copiesArrayObjects
     */
    @JsonProperty("copiesArrayObjects")
    public void setCopiesArrayObjects(Boolean copiesArrayObjects) {
        this.copiesArrayObjects = copiesArrayObjects;
    }

    /**
     * 
     * @return
     *     The linkFromPortIdProperty
     */
    @JsonProperty("linkFromPortIdProperty")
    public String getLinkFromPortIdProperty() {
        return linkFromPortIdProperty;
    }

    /**
     * 
     * @param linkFromPortIdProperty
     *     The linkFromPortIdProperty
     */
    @JsonProperty("linkFromPortIdProperty")
    public void setLinkFromPortIdProperty(String linkFromPortIdProperty) {
        this.linkFromPortIdProperty = linkFromPortIdProperty;
    }

    /**
     * 
     * @return
     *     The linkToPortIdProperty
     */
    @JsonProperty("linkToPortIdProperty")
    public String getLinkToPortIdProperty() {
        return linkToPortIdProperty;
    }

    /**
     * 
     * @param linkToPortIdProperty
     *     The linkToPortIdProperty
     */
    @JsonProperty("linkToPortIdProperty")
    public void setLinkToPortIdProperty(String linkToPortIdProperty) {
        this.linkToPortIdProperty = linkToPortIdProperty;
    }

    /**
     * 
     * @return
     *     The modelData
     */
    @JsonProperty("modelData")
    public ModelData getModelData() {
        return modelData;
    }

    /**
     * 
     * @param modelData
     *     The modelData
     */
    @JsonProperty("modelData")
    public void setModelData(ModelData modelData) {
        this.modelData = modelData;
    }

    /**
     * 
     * @return
     *     The nodeDataArray
     */
    @JsonProperty("nodeDataArray")
    public List<NodeDataArray> getNodeDataArray() {
        return nodeDataArray;
    }

    /**
     * 
     * @param nodeDataArray
     *     The nodeDataArray
     */
    @JsonProperty("nodeDataArray")
    public void setNodeDataArray(List<NodeDataArray> nodeDataArray) {
        this.nodeDataArray = nodeDataArray;
    }

    /**
     * 
     * @return
     *     The linkDataArray
     */
    @JsonProperty("linkDataArray")
    public List<LinkDataArray> getLinkDataArray() {
        return linkDataArray;
    }

    /**
     * 
     * @param linkDataArray
     *     The linkDataArray
     */
    @JsonProperty("linkDataArray")
    public void setLinkDataArray(List<LinkDataArray> linkDataArray) {
        this.linkDataArray = linkDataArray;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
