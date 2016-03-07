package SpongeCity.EvaluationPlatform.Core.BLL;

import SpongeCity.EvaluationPlatform.Core.model.MeasureModel;
import SpongeCity.EvaluationPlatform.Core.model.ParamModel;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.MeasureOperation;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.ParamOperation;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiMeasure;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/3/2.
 */
public class MeasureBLL {
    public List<ParamModel> getParamsByMeasureId(int measureId) {
        try {
            List<ParamModel> paramModels = new ArrayList<ParamModel>();
            ParamOperation po = new ParamOperation();
            List<DiParam> diParams = po.getParamListByMeasureId(measureId);
            for (DiParam diParam : diParams) {
                ParamModel paramModel = convertParamModel(diParam);
                paramModels.add(paramModel);
            }
            return paramModels;
        } catch (Exception ex) {
            return null;
        }
    }

    public MeasureModel getMeasureByMeasuerId(int measureId){
        try {
            MeasureModel measureModel = new MeasureModel();
            MeasureOperation mo = new MeasureOperation();
            DiMeasure diMeasure = mo.getDiMeasureById(measureId);
            measureModel = convertMeasureModel(diMeasure);
            return measureModel;
        } catch (Exception ex) {
            return null;
        }
    }

    private ParamModel convertParamModel(DiParam diParam) {
        ParamModel paramModel = new ParamModel();
        paramModel.setId(diParam.getId());
        paramModel.setDisplayname(diParam.getColumn_name());
        paramModel.setIsCalculable(diParam.getIsCalculable() == 1 ? true : false);
        paramModel.setMid(diParam.getMid());
        paramModel.setName(diParam.getName());
        return paramModel;
    }

    private MeasureModel convertMeasureModel(DiMeasure diMeasure){
        MeasureModel measureModel = new MeasureModel();
        measureModel.setName(diMeasure.getName());
        measureModel.setDi_tid(diMeasure.getDi_tid());
        measureModel.setId(diMeasure.getId());
        measureModel.setTable_name(diMeasure.getTable_name());
        measureModel.setTid(diMeasure.getTid());
        return measureModel;
    }
}
