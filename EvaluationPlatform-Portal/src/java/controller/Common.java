package controller;

import SpongeCity.EvaluationPlatform.Core.BLL.MeasureBLL;
import SpongeCity.EvaluationPlatform.Core.model.MeasureModel;
import SpongeCity.EvaluationPlatform.Core.model.TaxModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/3/14.
 */
public class Common {
    public List<TaxModel> getAllTax(){
        MeasureBLL measureBLL = new MeasureBLL();
        List<TaxModel> taxList = new ArrayList<TaxModel>();
        taxList = measureBLL.getTaxonomyList();
        return taxList;
    }

    public List<MeasureModel> getMeasureListByTaxId(int taxId){
        MeasureBLL measureBLL = new MeasureBLL();
        List<MeasureModel> measureList = new ArrayList<MeasureModel>();
        measureList = measureBLL.getMeasureList(taxId);
        return measureList;
    }
}
