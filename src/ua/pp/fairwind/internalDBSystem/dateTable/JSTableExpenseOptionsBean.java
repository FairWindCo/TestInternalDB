/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.fairwind.internalDBSystem.dateTable;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author sanathko
 */
public class JSTableExpenseOptionsBean {
    private String DisplayText;

    private String Value;

    public JSTableExpenseOptionsBean(Long value,String DisplayText) {
        this.DisplayText = DisplayText;
        this.Value = value!=null?value.toString():null;
    }

    public JSTableExpenseOptionsBean(String value,String DisplayText) {
        this.DisplayText = DisplayText;
        this.Value = value;
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
