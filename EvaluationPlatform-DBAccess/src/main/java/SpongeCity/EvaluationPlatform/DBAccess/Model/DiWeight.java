package SpongeCity.EvaluationPlatform.DBAccess.Model;

/**
 * Created by saber on 2016/3/2.
 */
public class DiWeight {
    private int id;
    private int mid;
    private int aid;
    private int weight;
    private int datastatus;
    private String areaname;

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
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

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getDatastatus() {
        return datastatus;
    }

    public void setDatastatus(int datastatus) {
        this.datastatus = datastatus;
    }
}
