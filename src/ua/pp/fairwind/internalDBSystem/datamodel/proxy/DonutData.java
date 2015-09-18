package ua.pp.fairwind.internalDBSystem.datamodel.proxy;

/**
 * Created by Сергей on 16.09.2015.
 */
public class DonutData {
    final private long value;
    final private String label;
    final private long id;

    public DonutData(long value, String label,long id) {
        this.value = value;
        this.label = label;
        this.id=id;
    }

    public long getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public long getId() {
        return id;
    }
}
