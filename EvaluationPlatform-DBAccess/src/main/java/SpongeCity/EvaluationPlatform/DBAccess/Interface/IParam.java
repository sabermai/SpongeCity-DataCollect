package SpongeCity.EvaluationPlatform.DBAccess.Interface;

import SpongeCity.EvaluationPlatform.DBAccess.Model.DiParam;

import java.util.List;

/**
 * Created by saber on 2016/2/22.
 */
public interface IParam {
    List<DiParam> getParamListByMeasureId(int measureId);
}
