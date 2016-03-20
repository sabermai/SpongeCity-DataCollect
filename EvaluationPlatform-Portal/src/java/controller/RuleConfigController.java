package controller;

import SpongeCity.EvaluationPlatform.Core.BLL.MeasureBLL;
import SpongeCity.EvaluationPlatform.Core.BLL.RuleBLL;
import SpongeCity.EvaluationPlatform.Core.model.*;
import model.MeasureWeightJsonModel;
import model.ParamRuleJsonModel;
import model.ParamRuleModel;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/3/16.
 */
@Controller
@RequestMapping(value = "/ruleconfig")
public class RuleConfigController {
    @RequestMapping(value = {"/index", "/", ""}, method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/ruleconfig/index");
        MeasureBLL measureBLL = new MeasureBLL();
        List<TaxModel> taxList = measureBLL.getTaxonomyList();
        List<MeasureModel> measureList = new ArrayList<MeasureModel>();
        TaxModel tax = taxList.get(0);
        measureList = measureBLL.getMeasureList(tax.getId());
        MeasureModel measure = measureList.get(0);
        List<ParamModel> paramList = getParams(measure.getId());
        RuleBLL ruleBLL = new RuleBLL();
        List<TimeRuleModel> timeRules = getTimeRules(measure.getId());
        List<AreaRuleModel> areaRules = getAreaRules(measure.getId());
        List<WeightModel> weights = ruleBLL.getWeightListByMeasuerId(measure.getId(), measure.getTid());
        List<ParamRuleModel> paramRules = convertParamRuleModel(paramList, timeRules, areaRules);
        JSONArray jsonParamRules = JSONArray.fromObject(paramRules);
        //JSONArray jsonWeight = JSONArray.fromObject(weights);
        modelAndView.addObject("taxs", taxList);
        modelAndView.addObject("measures", measureList);
        modelAndView.addObject("paramList", paramList);
        modelAndView.addObject("paramRuleList", jsonParamRules.toString());
        modelAndView.addObject("weightrules", weights);
        return modelAndView;
    }


    @RequestMapping(value = "/weights", method = RequestMethod.GET)
    public ModelAndView getWeights(int measureId) {
        ModelAndView modelAndView = new ModelAndView("/ruleconfig/weight");
        RuleBLL ruleBLL = new RuleBLL();
        MeasureBLL measureBLL = new MeasureBLL();
        MeasureModel measure = measureBLL.getMeasureByMeasuerId(measureId);
        List<WeightModel> weights = ruleBLL.getWeightListByMeasuerId(measureId, measure.getTid());
        modelAndView.addObject("weightrules", weights);
        return modelAndView;
    }

    @RequestMapping(value = "/paramrules", method = RequestMethod.GET)
    public ModelAndView getParamRules(int measureId) {
        ModelAndView modelAndView = new ModelAndView("/ruleconfig/paramrules");
        List<ParamModel> params = getParams(measureId);
        List<ParamRuleModel> paramRules = convertParamRuleModel(params, getTimeRules(measureId), getAreaRules(measureId));
        JSONArray jsonArray = JSONArray.fromObject(paramRules);
        modelAndView.addObject("paramlist", params);
        modelAndView.addObject("paramrules", jsonArray.toString());
        return modelAndView;
    }

    @RequestMapping(value = "/updaterules", method = RequestMethod.POST)
    @ResponseBody
    public void updateParamRules(@RequestBody List<ParamRuleJsonModel> paramRules) {
        try {
            RuleBLL ruleBLL = new RuleBLL();
            List<AreaRuleModel> areaRuleModels = new ArrayList<AreaRuleModel>();
            List<TimeRuleModel> timeRuleModels = new ArrayList<TimeRuleModel>();
            for (ParamRuleJsonModel paramRule : paramRules) {
                AreaRuleModel areaRule = new AreaRuleModel();
                TimeRuleModel timeRule = new TimeRuleModel();
                areaRule.setPid(paramRule.getPid());
                areaRule.setRule(paramRule.getAreaRule());
                timeRule.setPid(paramRule.getPid());
                timeRule.setRule(paramRule.getTimeRule());
                timeRule.setGrain(paramRule.getGrain());
                timeRule.setGrainnumber(paramRule.getGrainnumber());
                areaRuleModels.add(areaRule);
                timeRuleModels.add(timeRule);
            }
            ruleBLL.updateAreaRuleData(areaRuleModels);
            ruleBLL.updateTimeRuleData(timeRuleModels);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/updateweights", method = RequestMethod.POST)
    @ResponseBody
    public void updateWeights(@RequestBody List<MeasureWeightJsonModel> weights) {
        try {
            RuleBLL ruleBLL = new RuleBLL();
            List<WeightModel> weightModels = new ArrayList<WeightModel>();
            for (MeasureWeightJsonModel weight : weights) {
                WeightModel weightModel = new WeightModel();
                weightModel.setId(weight.getId());
                weightModel.setWeight(weight.getWeight());
                weightModels.add(weightModel);
            }
            ruleBLL.updateWeightData(weightModels);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private List<ParamRuleModel> convertParamRuleModel(List<ParamModel> paramList, List<TimeRuleModel> timeRuleList, List<AreaRuleModel> areaRuleList) {
        List<ParamRuleModel> paramRuleList = new ArrayList<ParamRuleModel>();
        for (ParamModel paramModel : paramList) {
            ParamRuleModel paramRule = new ParamRuleModel();
            TimeRuleModel timeRule = new TimeRuleModel();
            AreaRuleModel areaRule = new AreaRuleModel();
            int pid = paramModel.getId();
            for (TimeRuleModel timeRuleModel : timeRuleList) {
                if (timeRuleModel.getPid() == pid) {
                    timeRule = timeRuleModel;
                    break;
                }
            }
            for (AreaRuleModel areaRuleModel : areaRuleList) {
                if (areaRuleModel.getPid() == pid) {
                    areaRule = areaRuleModel;
                    break;
                }
            }
            paramRule.setId(paramModel.getId());
            paramRule.setMid(paramModel.getMid());
            paramRule.setName(paramModel.getName());
            paramRule.setDisplayname(paramModel.getDisplayname());
            paramRule.setAreaRuleId(areaRule.getId());
            paramRule.setAreaRule(areaRule.getRule());
            paramRule.setTimeRuleId(timeRule.getId());
            paramRule.setTimeRule(timeRule.getRule());
            paramRule.setGrain(timeRule.getGrain());
            paramRule.setGrainnumber(timeRule.getGrainnumber());
            paramRuleList.add(paramRule);
        }
        return paramRuleList;
    }

    private List<TimeRuleModel> getTimeRules(int measureId) {
        RuleBLL ruleBLL = new RuleBLL();
        List<TimeRuleModel> timeRules = ruleBLL.getTimeRuleListByMeasureId(measureId);
        return timeRules;
    }

    private List<AreaRuleModel> getAreaRules(int measureId) {
        RuleBLL ruleBLL = new RuleBLL();
        List<AreaRuleModel> areaRules = ruleBLL.getAreaRuleListByMeasureId(measureId);
        return areaRules;
    }

    private List<ParamModel> getParams(int measureId) {
        MeasureBLL measureBLL = new MeasureBLL();
        List<ParamModel> paramList = measureBLL.getParamsByMeasureId(measureId);
        return paramList;
    }
}
