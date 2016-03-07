package SpongeCity.EvaluationPlatform.Core.model;

import java.util.List;

/**
 * Created by saber on 2016/3/2.
 * 表示Excel中一个sheet的信息
 */
public class MeasureDataModel {
    //指标名称
    private String measuername;
    private int mid;
    //对应的数据库表名称
    private String dbtablename;
    //该sheet的所有数据对象
    private List<DataModel> dataModels;

    public String getMeasuername() {
        return measuername;
    }

    public void setMeasuername(String measuername) {
        this.measuername = measuername;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getDbtablename() {
        return dbtablename;
    }

    public void setDbtablename(String dbtablename) {
        this.dbtablename = dbtablename;
    }

    public List<DataModel> getDataModels() {
        return dataModels;
    }

    public void setDataModels(List<DataModel> dataModels) {
        this.dataModels = dataModels;
    }
}
