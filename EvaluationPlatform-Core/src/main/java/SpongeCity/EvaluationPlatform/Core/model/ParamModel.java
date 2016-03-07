package SpongeCity.EvaluationPlatform.Core.model;

/**
 * Created by saber on 2016/3/2.
 */
public class ParamModel {
    private int id;
    private int mid;
    //字段名称
    private String name;
    //显示的中文名称
    private String displayname;
    //是否为可以计算的字段
    private boolean isCalculable;

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

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public boolean isCalculable() {
        return isCalculable;
    }

    public void setIsCalculable(boolean isCalculable) {
        this.isCalculable = isCalculable;
    }
}
