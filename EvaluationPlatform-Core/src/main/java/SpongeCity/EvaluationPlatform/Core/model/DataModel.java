package SpongeCity.EvaluationPlatform.Core.model;

import java.util.Date;
import java.util.Map;

/**
 * Created by saber on 2016/3/2.
 * 表示Excel中的一行数据
 */
public class DataModel {
    private String region;
    private String section;
    private String device;
    private int t_id;
    private int parentId;
    private Date samplingtime;
    private Map<String, ParamDataModel> data;

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Map<String, ParamDataModel> getData() {
        return data;
    }

    public void setData(Map<String, ParamDataModel> data) {
        this.data = data;
    }

    public Date getSamplingtime() {
        return samplingtime;
    }

    public void setSamplingtime(Date samplingtime) {
        this.samplingtime = samplingtime;
    }
}
