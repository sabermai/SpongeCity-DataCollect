package SpongeCity.EvaluationPlatform.DBAccess.Interface;

import SpongeCity.EvaluationPlatform.DBAccess.Model.DiMeasure;

import java.util.List;

/**
 * Created by saber on 2016/2/22.
 */
public interface IMeasure {
    List<DiMeasure> getDiMeasureListByDiTaxId(int diTaxonomyId);

    DiMeasure getDiMeasureById(int measureId);
}
