package SpongeCity.EvaluationPlatform.Core.model;

/**
 * Created by saber on 2016/3/2.
 */
public class MeasureModel {
    private int id;
    private int tid;
    private int di_tid;
    private String name;
    private String table_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getDi_tid() {
        return di_tid;
    }

    public void setDi_tid(int di_tid) {
        this.di_tid = di_tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }
}
