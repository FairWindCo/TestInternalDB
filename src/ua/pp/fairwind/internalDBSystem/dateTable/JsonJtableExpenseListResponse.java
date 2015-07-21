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
public class JsonJtableExpenseListResponse {
    
    private String Result;
    
    private List<JsonJTableExpenseBean> Records;
    
    private int TotalRecordCount;
    
    private String Message;

    public JsonJtableExpenseListResponse(String Result, List<JsonJTableExpenseBean> Records,int TotalRecordCount) {
        this.Result = Result;
        this.Records = Records;
        this.TotalRecordCount = TotalRecordCount;
    }

    public JsonJtableExpenseListResponse(String Result, String Message) {
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

    @JsonProperty("Records")
    public List<JsonJTableExpenseBean> getRecords() {
        return Records;
    }

    public void setRecords(List<JsonJTableExpenseBean> Records) {
        this.Records = Records;
    }

    @JsonProperty("TotalRecordCount")
    public int getTotalRecordCount() {
        return TotalRecordCount;
    }

    public void setTotalRecordCount(int TotalRecordCount) {
        this.TotalRecordCount = TotalRecordCount;
    } 

    @JsonProperty("Message")
    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }  
}
