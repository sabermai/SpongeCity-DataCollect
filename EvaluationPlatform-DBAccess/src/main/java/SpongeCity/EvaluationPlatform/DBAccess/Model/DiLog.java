package SpongeCity.EvaluationPlatform.DBAccess.Model;

import java.util.Date;

/**
 * Created by saber on 2016/2/22.
 */
public class DiLog {
    private int id;
    private Date import_time;
    private String file_name;
    private int mid;
    private String taxonomy_name;
    private String measure_name;
    private int status;
    private int datastatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getImport_time() {
        return import_time;
    }

    public void setImport_time(Date import_time) {
        this.import_time = import_time;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getTaxonomy_name() {
        return taxonomy_name;
    }

    public void setTaxonomy_name(String taxonomy_name) {
        this.taxonomy_name = taxonomy_name;
    }

    public String getMeasure_name() {
        return measure_name;
    }

    public void setMeasure_name(String measure_name) {
        this.measure_name = measure_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDatastatus() {
        return datastatus;
    }

    public void setDatastatus(int datastatus) {
        this.datastatus = datastatus;
    }
}
