/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.fairwind.internalDBSystem.dateTable;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 * @author sanathko
 */
public class JSTableOptionsResponse<T> {

    private JSTableExpenseResult Result;

    private List<T> Options;

    private String Message;

    public JSTableOptionsResponse(List<T> Options) {
        this.Result = JSTableExpenseResult.OK;
        this.Options = Options;
    }

    public JSTableOptionsResponse(String Message) {
        this.Result = JSTableExpenseResult.ERROR;
        this.Message = Message;
    }
    @JsonSerialize
    @JsonProperty("Result")
    public JSTableExpenseResult getResult() {
        return Result;
    }

    public void setResult(JSTableExpenseResult Result) {
        this.Result = Result;
    }
    @JsonSerialize
    @JsonProperty("Options")
    public List<T> getOptions() {
        return Options;
    }

    public void setOptions(List<T> Options) {
        this.Options = Options;
    }
    @JsonSerialize
    @JsonProperty("Message")
    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
    
}
