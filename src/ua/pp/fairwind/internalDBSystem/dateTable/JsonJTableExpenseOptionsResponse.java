/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.fairwind.internalDBSystem.dateTable;


import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author sanathko
 */
public class JsonJTableExpenseOptionsResponse {
    
    private String Result;
    
    private List<JsonJTableExpenseOptionsBean> Options;
    
    private String Message;

    public JsonJTableExpenseOptionsResponse(String Result, List<JsonJTableExpenseOptionsBean> Options) {
        this.Result = Result;
        this.Options = Options;
    }

    public JsonJTableExpenseOptionsResponse(String Result, String Message) {
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

    @JsonProperty("Options")
    public List<JsonJTableExpenseOptionsBean> getOptions() {
        return Options;
    }

    public void setOptions(List<JsonJTableExpenseOptionsBean> Options) {
        this.Options = Options;
    }

    @JsonProperty("Message")
    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
    
}
