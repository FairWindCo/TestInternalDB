package ua.pp.fairwind.internalDBSystem.dateTable;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;

/**
 * Created by Сергей on 21.07.2015.
 */
public class FormSort {
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
}
