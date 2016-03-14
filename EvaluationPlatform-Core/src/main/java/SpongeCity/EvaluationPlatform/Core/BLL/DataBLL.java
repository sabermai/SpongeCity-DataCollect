package SpongeCity.EvaluationPlatform.Core.BLL;

import SpongeCity.EvaluationPlatform.Core.model.*;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.DataOperation;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.DiLogOperation;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.RuleOperation;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.WeightOperation;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiAreaRule;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiTimeRule;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiWeight;

import java.util.*;

/**
 * Created by saber on 2016/3/3.
 */
public class DataBLL {
    public void dataImport(String filename, MeasureDataModel measureDataModel) throws Exception {
        //保存日志信息，日志datastatus为0，导入完成后置为1
        DiLogOperation logOperation = new DiLogOperation();
        int logId = logOperation.saveLog(filename, measureDataModel.getMid());

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
        for (DataModel dataModel : originalData) {
            Map<String, Integer> map = getAreaId(areaModels, dataModel);
            dataModel.setT_id(map.get("t_id"));
            dataModel.setParentId(map.get("parentId"));
        }
        Map<Integer, List<DataModel>> originalDataMap = new HashMap<Integer, List<DataModel>>();
        for (AreaModel areaModel : areaModels) {
            originalDataMap.put(areaModel.getTid(), new ArrayList<DataModel>());
        }
        for (DataModel dataModel : originalData) {
            if (originalDataMap.containsKey(dataModel.getT_id())) {
                originalDataMap.get(dataModel.getT_id()).add(dataModel);
            }
        }
        //endregion

        //region 按时间维度运算
        List<DataModel> timeDatas = new ArrayList<DataModel>();
        for (AreaModel areaModel : areaModels) {
            List<DataModel> sortDataList = new ArrayList<DataModel>(originalDataMap.get(areaModel.getTid()));
            if (sortDataList != null && sortDataList.size() > 0) {
                sortDataList = sortList(sortDataList);
                Date firstDate = sortDataList.get(0).getSamplingtime();
                Date lastDate = sortDataList.get(sortDataList.size() - 1).getSamplingtime();

                Date startTime = getSamplingTime(diTimeRules.get(0), firstDate);
                Date endTime = getSamplingTime(diTimeRules.get(0), lastDate);
                Date currentDate = startTime;
                while (!currentDate.after(endTime)) {
                    DataModel dm = new DataModel();
                    Map<String, ParamDataModel> paramDataMap = new HashMap<String, ParamDataModel>();
                    dm.setT_id(areaModel.getId());
                    dm.setRegion(areaModel.getRegionName());
                    dm.setSection(areaModel.getSectionName());
                    dm.setDevice(areaModel.getDeviceName());
                    dm.setSamplingtime(currentDate);
                    dm.setData(paramDataMap);

                    Date nextDate = getNextDateTime(currentDate, grain, grainnumber);
                    Map<String, List<ParamDataModel>> paramDataList = new HashMap<String, List<ParamDataModel>>();
                    for (DataModel dataModel : sortDataList) {
                        if (!dataModel.getSamplingtime().before(currentDate) && dataModel.getSamplingtime().before(nextDate)) {
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
                            data = paramDataCalculate(dataList, timeRule);
                        } else {
                            data.setIsCalculable(dataList.get(0).isCalculable());
                            data.setParamDisplayName(dataList.get(0).getParamDisplayName());
                            data.setParamName(dataList.get(0).getParamName());
                            data.setValue(dataList.get(0).getValue());
                        }
                        paramDataMap.put(paramModel.getDisplayname(), data);
                    }
                    timeDatas.add(dm);
                    currentDate = nextDate;
                }
            }
        }
        //endregion

        //region 按地理维度运算
        List<DataModel> areaDatas = new ArrayList<DataModel>();
        List<DataModel> sortDatas = new ArrayList<DataModel>(timeDatas);
        List<DataModel> dataToCalc = new ArrayList<DataModel>();
        Date samplingTime = new Date();
        for (DataModel sortData : sortDatas) {
            if (dataToCalc.size() == 0) {
                samplingTime = sortData.getSamplingtime();
                dataToCalc.add(sortData);
            } else {
                if (sortData.getSamplingtime() == samplingTime) {
                    dataToCalc.add(sortData);
                } else {
                    areaDatas.addAll(calculateOnArea(dataToCalc, paramModels, diAreaRules, diWeights, areaModels));
                    dataToCalc.clear();
                    dataToCalc.add(sortData);
                    samplingTime = sortData.getSamplingtime();
                }
            }
        }
        //endregion

        //region save data
        List<DataModel> dataToSave = new ArrayList<DataModel>();
        dataToSave.addAll(originalData);
        dataToSave.addAll(timeDatas);
        dataToSave.addAll(areaDatas);
        int result = saveData(measureDataModel.getDbtablename(), paramModels, dataToSave);
        //endregion

        //更新日志的datastatus字段为1
        logOperation.updateLog(logId);
    }

    public void deleteData(int logId, String tableName) throws Exception {
        DataOperation dataOperation = new DataOperation();
        dataOperation.deleteData(logId,tableName);
    }

    //region private method
    //保存数据,统一通过DataOperation类，方法名为save+数据库表明+data
    private int saveData(String tableName, List<ParamModel> paramModels, List<DataModel> datas) throws Exception {
        /*Class c = Class.forName("SpongeCity.EvaluationPlatform.DBAccess.DataAccess.DataOperation");
        Object o = c.newInstance();
        Method m = c.getMethod("save" + params.get("TableName") + "Data", new Class[]{Map.class});
        Object[] arguments = new Object[]{params};
        m.invoke(o, arguments);*/
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("table", tableName);

        String params = "";
        for (ParamModel param : paramModels) {
            params += param.getName() + ",";
        }
        arguments.put("params", params.substring(0, params.lastIndexOf(',') - 1));

        List<String> values = new ArrayList<String>();
        for (DataModel data : datas) {
            String value = "";
            for (ParamModel paramModel : paramModels) {
                value += data.getData().get(paramModel.getDisplayname()).toString() + ",";
            }
            values.add(value.substring(0, value.lastIndexOf(',') - 1));
        }
        arguments.put("values", values);
        DataOperation dataOperation = new DataOperation();
        return dataOperation.saveImportData(arguments);
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

    private Date getSamplingTime(DiTimeRule timeRule, Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        Date result = new Date();
        Calendar temp = Calendar.getInstance();
        int grainNum = timeRule.getGrainnumber();
        switch (timeRule.getGrain()) {
            case 0:
                temp.set(calendar.get(Calendar.YEAR), 1, 1);
                result = temp.getTime();
                break;
            case 1:
                int month = calendar.get(Calendar.MONTH);
                temp.set(calendar.get(Calendar.YEAR), month + 1 - month % grainNum, 1);
                result = temp.getTime();
                break;
            case 2:
                int day = calendar.get(Calendar.DATE);
                temp.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), day + 1 - day % grainNum);
                result = temp.getTime();
                break;
            case 3:
                int hour = calendar.get(Calendar.HOUR);
                temp.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), hour + 1 - hour % grainNum, 0);
                result = temp.getTime();
                break;
            case 4:
                int minute = calendar.get(Calendar.MINUTE);
                temp.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR), minute + 1 - minute % grainNum);
                result = temp.getTime();
                break;
        }
        return result;
    }

    private List<DataModel> calculateOnArea(List<DataModel> dataToCalc, List<ParamModel> paramModels, List<DiAreaRule> areaRules, List<DiWeight> diWeights, List<AreaModel> areaModels) {
        List<DataModel> result = new ArrayList<DataModel>();
        List<DataModel> sectionDatas = new ArrayList<DataModel>();
        List<DataModel> regionDatas = new ArrayList<DataModel>();

        List<AreaModel> regions = new ArrayList<AreaModel>();
        List<AreaModel> sections = new ArrayList<AreaModel>();
        List<AreaModel> devices = new ArrayList<AreaModel>();
        for (AreaModel areaModel : areaModels) {
            if (areaModel.getHierarchy() == 1) {
                regions.add(areaModel);
            } else if (areaModel.getHierarchy() == 2) {
                sections.add(areaModel);
            } else {
                devices.add(areaModel);
            }
        }

        for (AreaModel section : sections) {
            sectionDatas.add(areaDataOperation(section, dataToCalc, paramModels, areaRules, diWeights));
        }
        for (AreaModel region : regions) {
            regionDatas.add(areaDataOperation(region, sectionDatas, paramModels, areaRules, diWeights));
        }

        result.addAll(sectionDatas);
        result.addAll(regionDatas);
        return result;
    }

    private DataModel areaDataOperation(AreaModel areaModel, List<DataModel> dataToCalc, List<ParamModel> paramModels, List<DiAreaRule> areaRules, List<DiWeight> diWeights) {
        DataModel data = new DataModel();
        data.setRegion(areaModel.getName());
        data.setRegion(areaModel.getRegionName());
        data.setSection(areaModel.getSectionName());
        data.setDevice(areaModel.getDeviceName());
        data.setParentId(areaModel.getParentId());
        data.setT_id(areaModel.getTid());
        data.setSamplingtime(dataToCalc.get(0).getSamplingtime());

        List<DataModel> datas = new ArrayList<DataModel>();
        for (DataModel dataModel : dataToCalc) {
            if (dataModel.getParentId() == areaModel.getTid()) {
                datas.add(dataModel);
            }
        }
        data.setData(areaDataCalculate(areaRules, getWeightByArea(diWeights, areaModel.getId()), datas, paramModels));
        return data;
    }

    private DiAreaRule getAreaRuleByParamId(List<DiAreaRule> diAreaRules, int paramId) {
        DiAreaRule areaRule = new DiAreaRule();
        for (DiAreaRule diAreaRule : diAreaRules) {
            if (diAreaRule.getPid() == paramId) {
                areaRule = diAreaRule;
                break;
            }
        }
        return areaRule;
    }

    private Map<String, ParamDataModel> areaDataCalculate(List<DiAreaRule> diAreaRules, DiWeight weight, List<DataModel> datas, List<ParamModel> paramModels) {
        Map<String, ParamDataModel> map = new HashMap<String, ParamDataModel>();
        for (ParamModel paramModel : paramModels) {
            int operateType = getAreaRuleByParamId(diAreaRules, paramModel.getId()).getRule();
            ParamDataModel paramDataModel = new ParamDataModel();
            paramDataModel.setParamName(paramModel.getName());
            paramDataModel.setParamDisplayName(paramModel.getDisplayname());
            paramDataModel.setIsCalculable(paramModel.isCalculable());
            double value = 0;
            switch (operateType) {
                case 0:
                    map = datas.get(0).getData();
                    break;
                case 1:
                    for (DataModel data : datas) {
                        value += data.getData().get(paramModel.getDisplayname()).getValue();
                    }
                    paramDataModel.setValue(value);
                    map.put(paramModel.getDisplayname(), paramDataModel);
                    break;
                case 2:
                    //加权平均
                    int weightSum = 0;
                    for (DataModel data : datas) {
                        value += data.getData().get(paramModel.getDisplayname()).getValue() * weight.getWeight();
                        weightSum += weight.getWeight();
                    }
                    paramDataModel.setValue(value / weightSum);
                    map.put(paramModel.getDisplayname(), paramDataModel);
                    break;
                case 3:
                    value = datas.get(0).getData().get(paramModel.getDisplayname()).getValue();
                    for (DataModel data : datas) {
                        double temp = data.getData().get(paramModel.getDisplayname()).getValue();
                        if (value < temp) {
                            value = temp;
                        }
                    }
                    paramDataModel.setValue(value);
                    map.put(paramModel.getDisplayname(), paramDataModel);
                    break;
                case 4:
                    value = datas.get(0).getData().get(paramModel.getDisplayname()).getValue();
                    for (DataModel data : datas) {
                        double temp = data.getData().get(paramModel.getDisplayname()).getValue();
                        if (value > temp) {
                            value = temp;
                        }
                    }
                    paramDataModel.setValue(value);
                    map.put(paramModel.getDisplayname(), paramDataModel);
                    break;
            }

        }
        return map;
    }

    private DiWeight getWeightByArea(List<DiWeight> weights, int areaId) {
        DiWeight weight = new DiWeight();
        for (DiWeight item : weights) {
            if (item.getAid() == areaId) {
                weight = item;
                break;
            }
        }
        return weight;
    }

    private Map<String, Integer> getAreaId(List<AreaModel> areaModels, DataModel dataModel) {
        Map<String, Integer> areaMap = new HashMap<String, Integer>();
        for (AreaModel areaModel : areaModels) {
            if (areaModel.getRegionName().equalsIgnoreCase(dataModel.getRegion())
                    && areaModel.getSectionName().equalsIgnoreCase(dataModel.getSection())
                    && areaModel.getDeviceName().equalsIgnoreCase(dataModel.getDevice())) {
                areaMap.put("t_Id", areaModel.getTid());
                areaMap.put("parentId", areaModel.getParentId());
                break;
            }
        }
        return areaMap;
    }
    //endregion
}