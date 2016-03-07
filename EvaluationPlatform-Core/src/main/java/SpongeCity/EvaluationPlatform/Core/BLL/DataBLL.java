package SpongeCity.EvaluationPlatform.Core.BLL;

import SpongeCity.EvaluationPlatform.Core.model.*;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.RuleOperation;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.WeightOperation;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiAreaRule;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiTimeRule;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiWeight;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by saber on 2016/3/3.
 */
public class DataBLL {
    public void dataImport(String filename, MeasureDataModel measureDataModel) throws Exception {
        //保存日志信息，日志datastatus为0，导入完成后置为1
        LogBLL logBLL = new LogBLL();
        int logId = logBLL.saveFileUploadLog(filename, measureDataModel.getMid());

        //region 加载运算规则及权重、区域信息
        RuleOperation ro = new RuleOperation();
        WeightOperation wo = new WeightOperation();
        AreaBLL areaBLL = new AreaBLL();
        List<DiTimeRule> diTimeRules = ro.getTimeRuleListByMeasureId(measureDataModel.getMid());
        List<DiAreaRule> diAreaRules = ro.getAreaRuleListByMeasureId(measureDataModel.getMid());
        List<DiWeight> diWeights = wo.getWeightListByMeasureId(measureDataModel.getMid());
        List<AreaModel> areaModels = areaBLL.getAreaHierarchy(measureDataModel.getMid());
        int grain = diTimeRules.get(0).getGrain();
        int grainnumber = diTimeRules.get(0).getGrainnumber();
        //endregion

        //region 加载指标字段信息
        MeasureBLL measureBLL = new MeasureBLL();
        List<ParamModel> paramModels = measureBLL.getParamsByMeasureId(measureDataModel.getMid());
        //endregion

        //region 按区域分组
        List<DataModel> originalData = measureDataModel.getDataModels();
        Map<String, List<DataModel>> originalDataMap = new HashMap<String, List<DataModel>>();
        for (AreaModel areaModel : areaModels) {
            originalDataMap.put(areaModel.getName(), new ArrayList<DataModel>());
        }
        for (DataModel dataModel : originalData) {
            if (originalDataMap.containsKey(dataModel.getArea())) {
                originalDataMap.get(dataModel.getArea()).add(dataModel);
            }
        }
        //endregion

        //region 按时间维度运算
        List<DataModel> timeDatas = new ArrayList<DataModel>();
        for (AreaModel areaModel : areaModels) {
            List<DataModel> sortDataList = new ArrayList<DataModel>(originalDataMap.get(areaModel.getName()));
            if (sortDataList != null && sortDataList.size() > 0) {
                sortDataList = sortList(sortDataList);
                Date firstDate = sortDataList.get(0).getSamplingtime();
                Date lastDate = sortDataList.get(sortDataList.size() - 1).getSamplingtime();

                Date currentDate = firstDate;
                while (currentDate.before(lastDate)) {
                    DataModel dm = new DataModel();
                    Map<String, ParamDataModel> paramDataMap = new HashMap<String, ParamDataModel>();
                    dm.setArea(areaModel.getName());
                    dm.setSamplingtime(currentDate);
                    dm.setData(paramDataMap);

                    Date nextDate = getNextDateTime(currentDate, grain, grainnumber);
                    Map<String, List<ParamDataModel>> paramDataList = new HashMap<String, List<ParamDataModel>>();
                    for (DataModel dataModel : sortDataList) {
                        if (dataModel.getSamplingtime().after(currentDate) && dataModel.getSamplingtime().before(nextDate)) {
                            for (String key : dataModel.getData().keySet()) {
                                if (!paramDataList.containsKey(key)) {
                                    List<ParamDataModel> list = new ArrayList<ParamDataModel>();
                                    list.add(dataModel.getData().get(key));
                                    paramDataList.put(key, list);
                                } else {
                                    paramDataList.get(key).add(dataModel.getData().get(key));
                                }
                            }
                        }
                    }

                    for (ParamModel paramModel : paramModels) {
                        ParamDataModel data = new ParamDataModel();
                        DiTimeRule timeRule = getTimeRuleByParamId(diTimeRules, paramModel.getId());
                        List<ParamDataModel> dataList = paramDataList.get(paramModel.getDisplayname());
                        if (paramModel.isCalculable()) {
                            data = paramDataCalculate(dataList,timeRule);
                        } else {
                            data.setIsCalculable(dataList.get(0).isCalculable());
                            data.setParamDisplayName(dataList.get(0).getParamDisplayName());
                            data.setParamName(dataList.get(0).getParamName());
                            data.setValue(dataList.get(0).getValue());
                        }
                        paramDataMap.put(paramModel.getDisplayname(),data);
                    }
                    timeDatas.add(dm);
                    currentDate = nextDate;
                }
            }
        }
        //endregion

        //region 按地理维度运算

        //endregion

        //更新日志的datastatus字段为1
        logBLL.updateFileUploadLog(logId);
    }

    //保存数据,统一通过DataOperation类，方法名为save+数据库表明+data
    private void saveData(Map<String, String> params) throws Exception {
        Class c = Class.forName("SpongeCity.EvaluationPlatform.DBAccess.DataAccess.DataOperation");
        Object o = c.newInstance();
        Method m = c.getMethod("save" + params.get("TableName") + "Data", new Class[]{Map.class});
        Object[] arguments = new Object[]{params};
        m.invoke(o, arguments);
    }

    //按采样时间排序
    private List<DataModel> sortList(List<DataModel> list) {
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((DataModel) o1).getSamplingtime().compareTo(((DataModel) o2).getSamplingtime());
            }
        });
        return list;
    }

    private int getCalendarGrain(int grain) {
        int timeSpan = 0;
        switch (grain) {
            case 0:
                timeSpan = Calendar.YEAR;
                break;
            case 1:
                timeSpan = Calendar.MONTH;
                break;
            case 2:
                timeSpan = Calendar.DATE;
                break;
            case 3:
                timeSpan = Calendar.HOUR;
                break;
            case 4:
                timeSpan = Calendar.MINUTE;
                break;
        }
        return timeSpan;
    }

    private Date getNextDateTime(Date currentDateTime, int grain, int grainnumber) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDateTime);
        calendar.add(getCalendarGrain(grain), grainnumber);
        return calendar.getTime();
    }

    private DiTimeRule getTimeRuleByParamId(List<DiTimeRule> diTimeRules, int paramId) {
        DiTimeRule timeRule = new DiTimeRule();
        for (DiTimeRule diTimeRule : diTimeRules) {
            if (diTimeRule.getPid() == paramId) {
                timeRule = diTimeRule;
                break;
            }
        }
        return timeRule;
    }

    private ParamDataModel paramDataCalculate(List<ParamDataModel> dataList, DiTimeRule timeRule) {
        ParamDataModel result = new ParamDataModel();
        result.setIsCalculable(dataList.get(0).isCalculable());
        result.setParamDisplayName(dataList.get(0).getParamDisplayName());
        result.setParamName(dataList.get(0).getParamName());
        double value = 0;
        switch (timeRule.getRule()) {
            case 0:
                value = dataList.get(0).getValue();
                break;
            case 1:
                for (ParamDataModel paramDataModel : dataList) {
                    value += paramDataModel.getValue();
                }
                break;
            case 2:
                for (ParamDataModel paramDataModel : dataList) {
                    value += paramDataModel.getValue();
                }
                value = value / dataList.size();
                break;
            case 3:
                value = dataList.get(0).getValue();
                for (ParamDataModel paramDataModel : dataList) {
                    if (paramDataModel.getValue() > value)
                        value = paramDataModel.getValue();
                }
                break;
            case 4:
                value = dataList.get(0).getValue();
                for (ParamDataModel paramDataModel : dataList) {
                    if (paramDataModel.getValue() < value)
                        value = paramDataModel.getValue();
                }
                break;
        }
        result.setValue(value);
        return result;
    }
}
