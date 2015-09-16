package ua.pp.fairwind.internalDBSystem.datamodel.proxy;

/**
 * Created by Сергей on 16.09.2015.
 */
public class DonutData {
    final private long value;
    final private String label;

    public DonutData(long value, String label) {
        this.value = value;
        this.label = label;
    }

    public long getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
