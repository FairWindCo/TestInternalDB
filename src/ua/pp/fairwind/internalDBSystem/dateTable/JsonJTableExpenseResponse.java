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
public class JsonJTableExpenseResponse {
    
    private String Result;
    
    private JsonJTableExpenseBean Records;
    
    private String Message;
    
    public JsonJTableExpenseResponse(){}

    public JsonJTableExpenseResponse(String Result) {
        this.Result = Result;
    }

    public JsonJTableExpenseResponse(String Result, JsonJTableExpenseBean Records) {
        this.Result = Result;
        this.Records = Records;
    }

    public JsonJTableExpenseResponse(String Result, String Message) {
        this.Result = Result;
        this.Message = Message;
    }

    @JsonProperty("Result")
    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    @JsonProperty("Record")
    public JsonJTableExpenseBean getRecords() {
        return Records;
    }

    public void setRecords(JsonJTableExpenseBean Records) {
        this.Records = Records;
    }   

    @JsonProperty("Message")
    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

}
