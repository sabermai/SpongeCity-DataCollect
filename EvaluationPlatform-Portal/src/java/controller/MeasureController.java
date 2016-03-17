package controller;

import SpongeCity.EvaluationPlatform.Core.BLL.MeasureBLL;
import SpongeCity.EvaluationPlatform.Core.model.MeasureModel;
import SpongeCity.EvaluationPlatform.Core.model.ParamModel;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/3/14.
 */
@Controller
@RequestMapping(value = "/measures")
public class MeasureController {
    @RequestMapping(value = "/measureByTax", method = RequestMethod.GET)
    @ResponseBody
    public String getMeasureListByTaxId(int taxId) {
        MeasureBLL measureBLL = new MeasureBLL();
        List<MeasureModel> measureList = new ArrayList<MeasureModel>();
        measureList = measureBLL.getMeasureList(taxId);
        JSONArray jsonArray = JSONArray.fromObject(measureList);
        return jsonArray.toString();
    }

    @RequestMapping(value = "/params", method = RequestMethod.GET)
    @ResponseBody
    public String getParamListByMeasureId(int measureId) {
        MeasureBLL measureBLL = new MeasureBLL();
        List<ParamModel> paramList = new ArrayList<ParamModel>();
        paramList = measureBLL.getParamsByMeasureId(measureId);
        JSONArray jsonArray = JSONArray.fromObject(paramList);
        return jsonArray.toString();
    }
}
