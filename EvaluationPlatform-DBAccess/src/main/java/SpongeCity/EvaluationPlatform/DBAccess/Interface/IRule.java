package SpongeCity.EvaluationPlatform.DBAccess.Interface;

import SpongeCity.EvaluationPlatform.DBAccess.Model.DiAreaRule;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiTimeRule;

import java.util.List;

/**
 * Created by saber on 2016/2/22.
 */
public interface IRule {
    List<DiAreaRule> getAreaRuleListByMeasureId(int measureId);
    List<DiTimeRule> getTimeRuleListByMeasureId(int measureId);
}
