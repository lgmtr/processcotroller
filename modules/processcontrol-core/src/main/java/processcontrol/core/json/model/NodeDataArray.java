
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
    "category",
    "item",
    "key",
    "loc",
    "text",
    "eventType",
    "eventDimension",
    "group",
    "taskType",
    "boundaryEventArray",
    "isGroup",
    "color",
    "size",
    "gatewayType"
})
public class NodeDataArray {

    @JsonProperty("category")
    private String category;
    @JsonProperty("item")
    private String item;
    @JsonProperty("key")
    private Integer key;
    @JsonProperty("loc")
    private String loc;
    @JsonProperty("text")
    private String text;
    @JsonProperty("eventType")
    private Integer eventType;
    @JsonProperty("eventDimension")
    private Integer eventDimension;
    @JsonProperty("group")
    private Integer group;
    @JsonProperty("taskType")
    private Integer taskType;
    @JsonProperty("boundaryEventArray")
    private List<Object> boundaryEventArray = new ArrayList<Object>();
    @JsonProperty("isGroup")
    private Boolean isGroup;
    @JsonProperty("color")
    private String color;
    @JsonProperty("size")
    private String size;
    @JsonProperty("gatewayType")
    private Integer gatewayType;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The category
     */
    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     The category
     */
    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 
     * @return
     *     The item
     */
    @JsonProperty("item")
    public String getItem() {
        return item;
    }

    /**
     * 
     * @param item
     *     The item
     */
    @JsonProperty("item")
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * 
     * @return
     *     The key
     */
    @JsonProperty("key")
    public Integer getKey() {
        return key;
    }

    /**
     * 
     * @param key
     *     The key
     */
    @JsonProperty("key")
    public void setKey(Integer key) {
        this.key = key;
    }

    /**
     * 
     * @return
     *     The loc
     */
    @JsonProperty("loc")
    public String getLoc() {
        return loc;
    }

    /**
     * 
     * @param loc
     *     The loc
     */
    @JsonProperty("loc")
    public void setLoc(String loc) {
        this.loc = loc;
    }

    /**
     * 
     * @return
     *     The text
     */
    @JsonProperty("text")
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 
     * @return
     *     The eventType
     */
    @JsonProperty("eventType")
    public Integer getEventType() {
        return eventType;
    }

    /**
     * 
     * @param eventType
     *     The eventType
     */
    @JsonProperty("eventType")
    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    /**
     * 
     * @return
     *     The eventDimension
     */
    @JsonProperty("eventDimension")
    public Integer getEventDimension() {
        return eventDimension;
    }

    /**
     * 
     * @param eventDimension
     *     The eventDimension
     */
    @JsonProperty("eventDimension")
    public void setEventDimension(Integer eventDimension) {
        this.eventDimension = eventDimension;
    }

    /**
     * 
     * @return
     *     The group
     */
    @JsonProperty("group")
    public Integer getGroup() {
        return group;
    }

    /**
     * 
     * @param group
     *     The group
     */
    @JsonProperty("group")
    public void setGroup(Integer group) {
        this.group = group;
    }

    /**
     * 
     * @return
     *     The taskType
     */
    @JsonProperty("taskType")
    public Integer getTaskType() {
        return taskType;
    }

    /**
     * 
     * @param taskType
     *     The taskType
     */
    @JsonProperty("taskType")
    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    /**
     * 
     * @return
     *     The boundaryEventArray
     */
    @JsonProperty("boundaryEventArray")
    public List<Object> getBoundaryEventArray() {
        return boundaryEventArray;
    }

    /**
     * 
     * @param boundaryEventArray
     *     The boundaryEventArray
     */
    @JsonProperty("boundaryEventArray")
    public void setBoundaryEventArray(List<Object> boundaryEventArray) {
        this.boundaryEventArray = boundaryEventArray;
    }

    /**
     * 
     * @return
     *     The isGroup
     */
    @JsonProperty("isGroup")
    public Boolean getIsGroup() {
        return isGroup;
    }

    /**
     * 
     * @param isGroup
     *     The isGroup
     */
    @JsonProperty("isGroup")
    public void setIsGroup(Boolean isGroup) {
        this.isGroup = isGroup;
    }

    /**
     * 
     * @return
     *     The color
     */
    @JsonProperty("color")
    public String getColor() {
        return color;
    }

    /**
     * 
     * @param color
     *     The color
     */
    @JsonProperty("color")
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * 
     * @return
     *     The size
     */
    @JsonProperty("size")
    public String getSize() {
        return size;
    }

    /**
     * 
     * @param size
     *     The size
     */
    @JsonProperty("size")
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * 
     * @return
     *     The gatewayType
     */
    @JsonProperty("gatewayType")
    public Integer getGatewayType() {
        return gatewayType;
    }

    /**
     * 
     * @param gatewayType
     *     The gatewayType
     */
    @JsonProperty("gatewayType")
    public void setGatewayType(Integer gatewayType) {
        this.gatewayType = gatewayType;
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
