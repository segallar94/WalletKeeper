package cl.usm.inf.walletkeeper.structs;

/**
 * Created by rescar on 26-10-17.
 */

public class Category implements Comparable<Category>{
    private int id;
    private String name;
    private int resId;

    public Category(int id, String name, int resID){
        this.id = id;
        this.name = name;
        this.resId = resID;
    }

    public String getName() {
        return name;
    }

    public int getResId() {
        return resId;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return resId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;   //If objects equal, is OK
        if (o instanceof Category) {
            Category that = (Category) o;
            return (name == that.name);
        }
        return false;
    }

    @Override
    public int compareTo(Category o) {
        return id > o.id ? +1 : id < o.id ? -1 : 0;
    }
}
