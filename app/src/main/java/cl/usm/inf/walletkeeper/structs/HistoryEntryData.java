package cl.usm.inf.walletkeeper.structs;

/**
 * Created by rescar on 07-10-17.
 */

public class HistoryEntryData {
    Float val;
    String desc;
    int cat;

    public HistoryEntryData(Float value, String desc, int cat){
        this.val = value;
        this.desc = desc;
        this.cat = cat;
    }

    public String getValue() {
        return val.toString();
    }

    public String getDescription() {
        return desc;
    }
}
