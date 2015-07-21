/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.fairwind.internalDBSystem.dateTable;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author sanathko
 */
public class JsonJTableExpenseOptionsBean {
    private String DisplayText;
    
    private String Value;

    public JsonJTableExpenseOptionsBean(String DisplayText, String Value) {
        this.DisplayText = DisplayText;
        this.Value = Value;
    }

    @JsonProperty("DisplayText")
    public String getDisplayText() {
        return DisplayText;
    }

    public void setDisplayText(String DisplayText) {
        this.DisplayText = DisplayText;
    }

    @JsonProperty("Value")
    public String getValue() {
        return Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }
}
