package ua.pp.fairwind.internalDBSystem.dateTable;



import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.domain.Page;

import java.util.Collection;

/**
 * Created by ������ on 21.07.2015.
 */
public class JSSelectExpenseResp<T> {
    final private Collection<T> items;
    final private Long total_count;
    final private String message;
    final boolean incomplete_results;

    public JSSelectExpenseResp(Collection<T> items, String message,boolean incomplete_results) {
        this.items = items;
        this.message = message;
        if(items!=null){
            total_count=(long)items.size();
        } else {
            total_count=null;
        }
        this.incomplete_results=incomplete_results;
    }

    public JSSelectExpenseResp(String message) {
        this.items = null;
        this.message = message;
        total_count=null;
        incomplete_results=false;
    }

    public JSSelectExpenseResp(Collection<T> result) {
        this(result,null,false);
    }

    public JSSelectExpenseResp(Page<T> page) {
        items = page.getContent();
        total_count = page.getTotalElements();
        message="OK";
        if(page.getTotalPages()>1) {
            incomplete_results = true;
        } else {
            incomplete_results = false;
        }
    }

    @JsonSerialize
    @JsonProperty("items")
    public Collection<T> getItems() {
        return items;
    }


    @JsonSerialize
    @JsonProperty("total_count")
    public Long getTotal_count() {
        return total_count;
    }

    @JsonSerialize
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

}
