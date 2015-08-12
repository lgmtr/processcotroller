
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
    "from",
    "to",
    "fromPort",
    "toPort",
    "points",
    "visible",
    "text",
    "category"
})
public class LinkDataArray {

    @JsonProperty("from")
    private Integer from;
    @JsonProperty("to")
    private Integer to;
    @JsonProperty("fromPort")
    private String fromPort;
    @JsonProperty("toPort")
    private String toPort;
    @JsonProperty("points")
    private List<Double> points = new ArrayList<Double>();
    @JsonProperty("visible")
    private Boolean visible;
    @JsonProperty("text")
    private String text;
    @JsonProperty("category")
    private String category;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The from
     */
    @JsonProperty("from")
    public Integer getFrom() {
        return from;
    }

    /**
     * 
     * @param from
     *     The from
     */
    @JsonProperty("from")
    public void setFrom(Integer from) {
        this.from = from;
    }

    /**
     * 
     * @return
     *     The to
     */
    @JsonProperty("to")
    public Integer getTo() {
        return to;
    }

    /**
     * 
     * @param to
     *     The to
     */
    @JsonProperty("to")
    public void setTo(Integer to) {
        this.to = to;
    }

    /**
     * 
     * @return
     *     The fromPort
     */
    @JsonProperty("fromPort")
    public String getFromPort() {
        return fromPort;
    }

    /**
     * 
     * @param fromPort
     *     The fromPort
     */
    @JsonProperty("fromPort")
    public void setFromPort(String fromPort) {
        this.fromPort = fromPort;
    }

    /**
     * 
     * @return
     *     The toPort
     */
    @JsonProperty("toPort")
    public String getToPort() {
        return toPort;
    }

    /**
     * 
     * @param toPort
     *     The toPort
     */
    @JsonProperty("toPort")
    public void setToPort(String toPort) {
        this.toPort = toPort;
    }

    /**
     * 
     * @return
     *     The points
     */
    @JsonProperty("points")
    public List<Double> getPoints() {
        return points;
    }

    /**
     * 
     * @param points
     *     The points
     */
    @JsonProperty("points")
    public void setPoints(List<Double> points) {
        this.points = points;
    }

    /**
     * 
     * @return
     *     The visible
     */
    @JsonProperty("visible")
    public Boolean getVisible() {
        return visible;
    }

    /**
     * 
     * @param visible
     *     The visible
     */
    @JsonProperty("visible")
    public void setVisible(Boolean visible) {
        this.visible = visible;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
