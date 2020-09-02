
package com.machine.input;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "outlets",
    "total_items_quantity",
    "beverages"
})
public class Machine {

    @JsonProperty("outlets")
    private Outlets outlets;
    
    @JsonProperty("total_items_quantity")
    private TotalItemsQuantity totalItemsQuantity;
    @JsonProperty("beverages")
    private Beverages beverages;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("outlets")
    public Outlets getOutlets() {
        return outlets;
    }

    @JsonProperty("outlets")
    public void setOutlets(Outlets outlets) {
        this.outlets = outlets;
    }

    @JsonProperty("total_items_quantity")
    public TotalItemsQuantity getTotalItemsQuantity() {
        return totalItemsQuantity;
    }

    @JsonProperty("total_items_quantity")
    public void setTotalItemsQuantity(TotalItemsQuantity totalItemsQuantity) {
        this.totalItemsQuantity = totalItemsQuantity;
    }

    @JsonProperty("beverages")
    public Beverages getBeverages() {
        return beverages;
    }

    @JsonProperty("beverages")
    public void setBeverages(Beverages beverages) {
        this.beverages = beverages;
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
