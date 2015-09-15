package ua.pp.fairwind.internalDBSystem.dateTable;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ������ on 21.07.2015.
 */
public class FormSort {
    public static Sort formSortFromSortDescription(String[][] sortdescription){
        if(sortdescription!=null && sortdescription.length>0) {
            ArrayList<Sort.Order> listorder = new ArrayList<>();
            for (String[] oneDescription : sortdescription) {
                if (oneDescription != null) {
                    if (oneDescription.length > 1) {
                        switch (oneDescription[0]) {
                            case "ASC":
                                listorder.add(new Sort.Order(Sort.Direction.ASC, oneDescription[1]));
                            case "DESC":
                                listorder.add(new Sort.Order(Sort.Direction.DESC, oneDescription[1]));
                            default:
                                listorder.add(new Sort.Order(Sort.Direction.ASC, oneDescription[1]));
                        }
                    } else if (oneDescription.length == 1) {
                        listorder.add(new Sort.Order(Sort.Direction.ASC, oneDescription[0]));
                    }
                    if (listorder.size() > 0) {
                        return new Sort(listorder.toArray(new Sort.Order[listorder.size()]));
                    }
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public static Sort formSortFromSortDescription(String sortdescription){

        if(sortdescription!=null && !sortdescription.isEmpty()){
            sortdescription=sortdescription.trim();
            if(sortdescription.contains(",")) {
                String[] sortorders = sortdescription.split(",");
                ArrayList<Sort.Order> listorder=new ArrayList<>();
                for(String sortorder:sortorders){
                    Sort.Order order=formOrder(sortdescription);
                    if(order!=null){
                        listorder.add(order);
                    }
                }
                if(listorder.size()>0){
                    return new Sort(listorder.toArray(new Sort.Order[listorder.size()]));
                }
            } else {
                Sort.Order order=formOrder(sortdescription);
                if(order!=null){
                    return new Sort(order);
                }
            }

        }
        return null;
    }



    public static Sort.Order formOrder(String orderDeclaration){
        if(orderDeclaration==null || orderDeclaration.isEmpty()) return null;
        String[] delaration=orderDeclaration.trim().split(" ");
        if(delaration==null || delaration.length!=2){
            return null;
        } else {
            switch (delaration[1]){
                case "ASC" :return new Sort.Order(Sort.Direction.ASC ,delaration[0]);
                case "DESC":return new Sort.Order(Sort.Direction.DESC,delaration[0]);
                default:
                    return new Sort.Order(Sort.Direction.ASC ,delaration[0]);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(formSortFromSortDescription("filesTypeName%20ASC"));
        System.out.println(formSortFromSortDescription("filesTypeName ASC"));
    }

    public static Long getLongFromString(String str){
        if(str==null || str.isEmpty()) return null;
        try {
            return new Long(str);
        } catch (NumberFormatException e){
            return null;
        }
    }

    public static Set<Long> getIdFromString(String s){
        if(s==null || s.length()==0)return null;
        String[] ids=s.split(",");
        if(ids==null | ids.length==0)return null;
        Set<Long> set=new HashSet<>();
        for (String id:ids){
            try {
                Long val=new Long(id);
                set.add(val);
            }catch (NumberFormatException e){
                //do nothing
            }
        }
        if(set.size()==0)return null;
        return set;
    }
}
