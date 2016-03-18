package SpongeCity.EvaluationPlatform.Core.BLL;

import SpongeCity.EvaluationPlatform.Core.model.AreaModel;
import SpongeCity.EvaluationPlatform.Core.model.AreaRuleModel;
import SpongeCity.EvaluationPlatform.Core.model.TimeRuleModel;
import SpongeCity.EvaluationPlatform.Core.model.WeightModel;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.RuleOperation;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.WeightOperation;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiAreaRule;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiTimeRule;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiWeight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/3/14.
 */
public class RuleBLL {
    //region ∂¡»°
    public List<TimeRuleModel> getTimeRuleListByMeasureId(int measureId) {
        try {
            List<TimeRuleModel> timeRules = new ArrayList<TimeRuleModel>();
            RuleOperation ruleOperation = new RuleOperation();
            List<DiTimeRule> diTimeRules = ruleOperation.getTimeRuleListByMeasureId(measureId);
            for (DiTimeRule diTimeRule : diTimeRules) {
                timeRules.add(convertTimeRuleModel(diTimeRule));
            }
            return timeRules;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<AreaRuleModel> getAreaRuleListByMeasureId(int measureId) {
        try {
            List<AreaRuleModel> areaRules = new ArrayList<AreaRuleModel>();
            RuleOperation ruleOperation = new RuleOperation();
            List<DiAreaRule> diAreaRules = ruleOperation.getAreaRuleListByMeasureId(measureId);
            for (DiAreaRule diAreaRule : diAreaRules) {
                areaRules.add(convertAreaRuleModel(diAreaRule));
            }
            return areaRules;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<WeightModel> getWeightListByMeasuerId(int measureId, int taxId) {
        try {
            List<WeightModel> weights = new ArrayList<WeightModel>();
            List<DiWeight> diWeights = new ArrayList<DiWeight>();
            WeightOperation weightOperation = new WeightOperation();
            AreaBLL areaBLL = new AreaBLL();
            List<AreaModel> areas = areaBLL.getAreaHierarchy(measureId);
            diWeights = weightOperation.getWeightListByMeasureId(measureId);
            for (DiWeight diWeight : diWeights) {
                AreaModel area = getAreaModelByAreaId(areas, diWeight.getAid());
                weights.add(convertWeightModel(diWeight, area));
            }
            return weights;
        } catch (Exception ex) {
            return null;
        }
    }

    private TimeRuleModel convertTimeRuleModel(DiTimeRule diTimeRule) {
        TimeRuleModel timeRuleModel = new TimeRuleModel();
        timeRuleModel.setId(diTimeRule.getId());
        timeRuleModel.setColumn_name(diTimeRule.getColumn_name());
        timeRuleModel.setGrain(diTimeRule.getGrain());
        timeRuleModel.setGrainnumber(diTimeRule.getGrainnumber());
        timeRuleModel.setMid(diTimeRule.getMid());
        timeRuleModel.setParamname(diTimeRule.getName());
        timeRuleModel.setPid(diTimeRule.getPid());
        timeRuleModel.setRule(diTimeRule.getRule());
        return timeRuleModel;
    }

    private AreaRuleModel convertAreaRuleModel(DiAreaRule diAreaRule) {
        AreaRuleModel areaRuleModel = new AreaRuleModel();
        areaRuleModel.setRule(diAreaRule.getRule());
        areaRuleModel.setPid(diAreaRule.getPid());
        areaRuleModel.setParam_name(diAreaRule.getName());
        areaRuleModel.setColumn_name(diAreaRule.getColumn_name());
        areaRuleModel.setMid(diAreaRule.getMid());
        areaRuleModel.setId(diAreaRule.getId());
        return areaRuleModel;
    }

    private WeightModel convertWeightModel(DiWeight diWeight, AreaModel area) {
        WeightModel weightModel = new WeightModel();
        weightModel.setMid(diWeight.getMid());
        weightModel.setId(diWeight.getId());
        weightModel.setAid(diWeight.getAid());
        weightModel.setAreaname(diWeight.getAreaname());
        weightModel.setWeight(diWeight.getWeight());
        weightModel.setRegion(area.getRegionName() == null ? "" : area.getRegionName());
        weightModel.setSection(area.getSectionName() == null ? "" : area.getSectionName());
        weightModel.setDevice(area.getDeviceName() == null ? "" : area.getDeviceName());
        return weightModel;
    }

    private AreaModel getAreaModelByAreaId(List<AreaModel> areas, int areaId) {
        AreaModel areaModel = new AreaModel();
        for (AreaModel area : areas) {
            if (area.getId() == areaId) {
                areaModel = area;
                break;
            }
        }
        return areaModel;
    }
    //endregion

    //region –¥»Î
    public int updateTimeRuleData(List<TimeRuleModel> timeRules) {
        try {
            List<DiTimeRule> diTimeRules = new ArrayList<DiTimeRule>();
            for (TimeRuleModel timeRule : timeRules) {
                diTimeRules.add(convertDiTimeRule(timeRule));
            }
            RuleOperation operation = new RuleOperation();
            return operation.updateTimeRule(diTimeRules);
        } catch (Exception e) {
            return -1;
        }
    }

    public int updateAreaRuleData(List<AreaRuleModel> areaRules) {
        try {
            List<DiAreaRule> diAreaRules = new ArrayList<DiAreaRule>();
            for (AreaRuleModel areaRule : areaRules) {
                diAreaRules.add(convertDiAreaRule(areaRule));
            }
            RuleOperation operation = new RuleOperation();
            return operation.updateAreaRule(diAreaRules);
        } catch (Exception e) {
            return -1;
        }
    }

    public int updateWeightData(List<WeightModel> weights) {
        try {
            List<DiWeight> diWeights = new ArrayList<DiWeight>();
            for (WeightModel weight : weights) {
                diWeights.add(convertDiWeigth(weight));
            }
            WeightOperation operation = new WeightOperation();
            return operation.updateWeightData(diWeights);
        } catch (Exception ex) {
            return -1;
        }
    }

    private DiTimeRule convertDiTimeRule(TimeRuleModel timeRuleModel) {
        DiTimeRule diTimeRule = new DiTimeRule();
        diTimeRule.setPid(timeRuleModel.getPid());
        diTimeRule.setRule(timeRuleModel.getRule());
        diTimeRule.setGrain(timeRuleModel.getGrain());
        diTimeRule.setGrainnumber(timeRuleModel.getGrainnumber());
        return diTimeRule;
    }

    private DiAreaRule convertDiAreaRule(AreaRuleModel areaRuleModel) {
        DiAreaRule diAreaRule = new DiAreaRule();
        diAreaRule.setPid(areaRuleModel.getPid());
        diAreaRule.setRule(areaRuleModel.getRule());
        return diAreaRule;
    }

    private DiWeight convertDiWeigth(WeightModel weightModel) {
        DiWeight diWeight = new DiWeight();
        diWeight.setId(weightModel.getId());
        diWeight.setWeight(weightModel.getWeight());
        return diWeight;
    }
    //endregion
}
