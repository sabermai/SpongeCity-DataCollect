package SpongeCity.EvaluationPlatform.Core.model;

import java.util.List;

/**
 * Created by saber on 2016/3/2.
 * ��ʾExcel��һ��sheet����Ϣ
 */
public class MeasureDataModel {
    //ָ������
    private String measuername;
    private int mid;
    //��Ӧ�����ݿ������
    private String dbtablename;
    //��sheet���������ݶ���
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
