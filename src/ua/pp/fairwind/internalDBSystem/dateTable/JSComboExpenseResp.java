package ua.pp.fairwind.internalDBSystem.dateTable;



import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.domain.Page;

import java.util.Collection;

/**
 * Created by ������ on 21.07.2015.
 */
public class JSComboExpenseResp<T> {
    private Collection<T> result;
    final private Long cnt_whole;
    final private String message;

    public JSComboExpenseResp(Collection<T> result, String message) {
        this.result = result;
        this.message = message;
        if(result!=null){
            cnt_whole=(long)result.size();
        } else {
            cnt_whole=null;
        }
    }

    public JSComboExpenseResp(Collection<T> result) {
        this(result,null);
    }

    public JSComboExpenseResp(Page<T> page) {
        result = page.getContent();
        cnt_whole = page.getTotalElements();
        message="OK";
    }

    @JsonSerialize
    @JsonProperty("result")
    public Collection<T> getResult() {
        return result;
    }


    @JsonSerialize
    @JsonProperty("cnt_whole")
    public Long getCnt_whole() {
        return cnt_whole;
    }

    @JsonSerialize
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

}
