package SpongeCity.EvaluationPlatform.Core.model;

import java.util.Date;
import java.util.Map;

/**
 * Created by saber on 2016/3/2.
 * 表示Excel中的一行数据
 */
public class DataModel {
    private String area;
    private Date samplingtime;
    private Map<String, ParamDataModel> data;

    public Map<String, ParamDataModel> getData() {
        return data;
    }

    public void setData(Map<String, ParamDataModel> data) {
        this.data = data;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getSamplingtime() {
        return samplingtime;
    }

    public void setSamplingtime(Date samplingtime) {
        this.samplingtime = samplingtime;
    }
}
