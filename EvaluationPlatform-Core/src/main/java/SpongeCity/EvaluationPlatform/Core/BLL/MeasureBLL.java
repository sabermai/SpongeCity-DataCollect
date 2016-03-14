package SpongeCity.EvaluationPlatform.Core.BLL;

import SpongeCity.EvaluationPlatform.Core.model.*;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.MeasureOperation;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.ParamOperation;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.TaxonomyOperation;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.WeightOperation;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiMeasure;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiParam;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiTaxonomy;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiWeight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/3/2.
 */
public class MeasureBLL {
    //region ∂¡»°
    public List<MeasureModel> getMeasureList(int taxId) {
        try {
            List<MeasureModel> measureList = new ArrayList<MeasureModel>();
            MeasureOperation measureOperation = new MeasureOperation();
            List<DiMeasure> diMeasures = measureOperation.getDiMeasureListByDiTaxId(taxId);
            TaxonomyOperation taxonomyOperation = new TaxonomyOperation();
            DiTaxonomy diTaxonomy = taxonomyOperation.getTaxonomyById(taxId);
            for (DiMeasure diMeasure : diMeasures) {
                MeasureModel measure = new MeasureModel();
                measure = convertMeasureModel(diMeasure, diTaxonomy);
                measureList.add(measure);
            }
            return measureList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<TaxModel> getTaxonomyList() {
        try {
            List<TaxModel> taxList = new ArrayList<TaxModel>();
            TaxonomyOperation taxonomyOperation = new TaxonomyOperation();
            List<DiTaxonomy> diTaxonomies = taxonomyOperation.getAllTaxonomy();
            for (DiTaxonomy diTax : diTaxonomies) {
                TaxModel taxModel = new TaxModel();
                taxModel = convertTaxModel(diTax);
                taxList.add(taxModel);
            }
            return taxList;
        } catch (Exception ex) {
            return null;
        }
    }

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

    public MeasureModel getMeasureByMeasuerId(int measureId) {
        try {
            MeasureModel measureModel = new MeasureModel();
            MeasureOperation mo = new MeasureOperation();
            DiMeasure diMeasure = mo.getDiMeasureById(measureId);
            TaxonomyOperation taxonomyOperation = new TaxonomyOperation();
            DiTaxonomy diTaxonomy = taxonomyOperation.getTaxonomyById(diMeasure.getTid());
            measureModel = convertMeasureModel(diMeasure, diTaxonomy);
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

    private MeasureModel convertMeasureModel(DiMeasure diMeasure, DiTaxonomy diTaxonomy) {
        try {
            MeasureModel measureModel = new MeasureModel();
            measureModel.setName(diMeasure.getName());
            measureModel.setDi_tid(diMeasure.getDi_tid());
            measureModel.setId(diMeasure.getId());
            measureModel.setTable_name(diMeasure.getTable_name());
            measureModel.setTid(diMeasure.getTid());
            measureModel.setTax_name(diTaxonomy.getName());
            return measureModel;
        } catch (Exception ex) {
            return null;
        }
    }

    private TaxModel convertTaxModel(DiTaxonomy diTaxonomy) {
        TaxModel taxModel = new TaxModel();
        taxModel.setId(diTaxonomy.getId());
        taxModel.setName(diTaxonomy.getName());
        taxModel.setVid(diTaxonomy.getVid());
        return taxModel;
    }
    //endregion
}
