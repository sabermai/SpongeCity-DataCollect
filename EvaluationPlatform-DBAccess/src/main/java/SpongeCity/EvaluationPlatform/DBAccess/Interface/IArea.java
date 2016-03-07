package SpongeCity.EvaluationPlatform.DBAccess.Interface;

import SpongeCity.EvaluationPlatform.DBAccess.Model.DiArea;

import java.util.List;

/**
 * Created by saber on 2016/2/22.
 */
public interface IArea {
    List<DiArea> getAreaListByMeasureId(int measureId);
}
