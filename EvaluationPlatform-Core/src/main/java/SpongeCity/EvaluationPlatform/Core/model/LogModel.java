package SpongeCity.EvaluationPlatform.Core.model;

import java.util.Date;

/**
 * Created by saber on 2016/3/14.
 */
public class LogModel {
    private int id;
    private String importtime;
    private String filename;
    private String tablename;
    private String taxname;
    private String measurename;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImporttime() {
        return importtime;
    }

    public void setImporttime(String importtime) {
        this.importtime = importtime;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getTaxname() {
        return taxname;
    }

    public void setTaxname(String taxname) {
        this.taxname = taxname;
    }

    public String getMeasurename() {
        return measurename;
    }

    public void setMeasurename(String measurename) {
        this.measurename = measurename;
    }
}
