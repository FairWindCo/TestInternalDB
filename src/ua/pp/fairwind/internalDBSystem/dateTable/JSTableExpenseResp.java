package ua.pp.fairwind.internalDBSystem.dateTable;



import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * Created by Сергей on 21.07.2015.
 */
public class JSTableExpenseResp<T> {
    private JSTableExpenseResult Result;
    private T Records;


    private String Message;

    public JSTableExpenseResp(String message) {
        Result = JSTableExpenseResult.ERROR;
        Message = message;
    }

    public JSTableExpenseResp(JSTableExpenseResult result, String message) {
        Result = result;
        Message = message;
    }

    public JSTableExpenseResp(T record) {
        Result = JSTableExpenseResult.OK;
        Records = record;
    }

    @JsonSerialize
    @JsonProperty("Result")
    public JSTableExpenseResult getResult() {
        return Result;
    }

    public void setResult(JSTableExpenseResult result) {
        Result = result;
    }

    @JsonSerialize
    @JsonProperty("Record")
    public T getRecord() {
        return Records;
    }

    public void setRecords(T record) {
        Records = record;
    }
    @JsonSerialize
    @JsonProperty("Message")
    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
