package SpongeCity.EvaluationPlatform.DBAccess.Interface;

import SpongeCity.EvaluationPlatform.DBAccess.Model.DiWeight;

import java.util.List;

/**
 * Created by saber on 2016/3/2.
 */
public interface IWeight {
    List<DiWeight> getWeightListByMeasureId(int measureId);
}
