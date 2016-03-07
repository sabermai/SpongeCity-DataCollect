package SpongeCity.EvaluationPlatform.DBAccess.Model;

/**
 * Created by saber on 2016/2/22.
 */
public class DiParam {
    private int id;
    private int mid;
    private String name;
    private String column_name;
    private int isCalculable;

    public int getIsCalculable() {
        return isCalculable;
    }

    public void setIsCalculable(int isCalculable) {
        this.isCalculable = isCalculable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }
}
