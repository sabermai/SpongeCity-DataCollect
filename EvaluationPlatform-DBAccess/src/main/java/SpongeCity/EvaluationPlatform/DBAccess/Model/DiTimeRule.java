package SpongeCity.EvaluationPlatform.DBAccess.Model;

/**
 * Created by saber on 2016/2/22.
 */
public class DiTimeRule {
    private int id;
    private int pid;
    private int grain;
    private int grainnumber;
    private int mid;
    private String name;
    private String column_name;
    private int rule;
    private int datastatus;

    public int getGrainnumber() {
        return grainnumber;
    }

    public void setGrainnumber(int grainnumber) {
        this.grainnumber = grainnumber;
    }

    public int getGrain() {
        return grain;
    }

    public void setGrain(int grain) {
        this.grain = grain;
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

    public int getRule() {
        return rule;
    }

    public void setRule(int rule) {
        this.rule = rule;
    }

    public int getDatastatus() {
        return datastatus;
    }

    public void setDatastatus(int datastatus) {
        this.datastatus = datastatus;
    }
}
