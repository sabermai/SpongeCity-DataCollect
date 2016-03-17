package model;

/**
 * Created by saber on 2016/3/17.
 */
public class ParamRuleModel {
    private int id;
    private int mid;
    //字段名称
    private String name;
    //显示的中文名称
    private String displayname;

    //区域维度运算规则
    private int areaRuleId;
    private int areaRule;

    //时间维度运算规则
    private int timeRuleId;
    private int grain;
    private int grainnumber;
    private int timeRule;

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

    public int getAreaRuleId() {
        return areaRuleId;
    }

    public void setAreaRuleId(int areaRuleId) {
        this.areaRuleId = areaRuleId;
    }

    public int getAreaRule() {
        return areaRule;
    }

    public void setAreaRule(int areaRule) {
        this.areaRule = areaRule;
    }

    public int getTimeRuleId() {
        return timeRuleId;
    }

    public void setTimeRuleId(int timeRuleId) {
        this.timeRuleId = timeRuleId;
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

    public int getTimeRule() {
        return timeRule;
    }

    public void setTimeRule(int timeRule) {
        this.timeRule = timeRule;
    }
}
