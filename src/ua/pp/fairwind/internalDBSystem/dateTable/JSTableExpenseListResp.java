package ua.pp.fairwind.internalDBSystem.dateTable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

/**
 * Created by Сергей on 21.07.2015.
 */
public class JSTableExpenseListResp<T> {
    private JSTableExpenseResult Result;

    private Collection<T> Records;

    private long TotalRecordCount;

    private String Message;

    public JSTableExpenseListResp(JSTableExpenseResult result, String message) {
        Result = result;
        Message = message;
    }

    public JSTableExpenseListResp(Collection<T> records, int totalRecordCount) {
        Result = JSTableExpenseResult.OK;
        Records = records;
        TotalRecordCount = totalRecordCount;
        Message="OK";
    }

    public JSTableExpenseListResp(Page<T> page) {
        Result = JSTableExpenseResult.OK;
        Records = page.getContent();
        TotalRecordCount = page.getTotalElements();
        Message="OK";
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
    @JsonProperty("Records")
    public Collection<T> getRecords() {
        return Records;
    }

    public void setRecords(Collection<T> records) {
        Records = records;
    }
    @JsonSerialize
    @JsonProperty("TotalRecordCount")
    public long getTotalRecordCount() {
        return TotalRecordCount;
    }

    public void setTotalRecordCount(long totalRecordCount) {
        TotalRecordCount = totalRecordCount;
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
