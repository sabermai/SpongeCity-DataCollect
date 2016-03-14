package SpongeCity.EvaluationPlatform.Core.model;

/**
 * Created by saber on 2016/3/14.
 */
public class TimeRuleModel {
    private int id;
    private int pid;
    private int grain;
    private int grainnumber;
    private int mid;
    private String paramname;
    private String column_name;
    private int rule;

    public String getParamname() {
        return paramname;
    }

    public void setParamname(String paramname) {
        this.paramname = paramname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getGrain() {
        return grain;
    }

    public void setGrain(int grain) {
        this.grain = grain;
    }

    public int getGrainnumber() {
        return grainnumber;
    }

    public void setGrainnumber(int grainnumber) {
        this.grainnumber = grainnumber;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public int getRule() {
        return rule;
    }

    public void setRule(int rule) {
        this.rule = rule;
    }
}
