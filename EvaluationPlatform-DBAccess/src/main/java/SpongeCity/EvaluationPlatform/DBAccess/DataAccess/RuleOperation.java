package SpongeCity.EvaluationPlatform.DBAccess.DataAccess;

import SpongeCity.EvaluationPlatform.DBAccess.Common.SqlConnection;
import SpongeCity.EvaluationPlatform.DBAccess.Interface.IParam;
import SpongeCity.EvaluationPlatform.DBAccess.Interface.IRule;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiAreaRule;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiTimeRule;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/2/22.
 */
public class RuleOperation {
    public List<DiAreaRule> getAreaRuleListByMeasureId(int measureId) throws Exception {
        List<DiAreaRule> rules = new ArrayList<DiAreaRule>();
        SqlSession session = SqlConnection.getSession();
        try {
            IRule iRule = session.getMapper(IRule.class);
            rules = iRule.getAreaRuleListByMeasureId(measureId);
            return rules;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    public List<DiTimeRule> getTimeRuleListByMeasureId(int measureId) throws Exception {
        List<DiTimeRule> rules = new ArrayList<DiTimeRule>();
        SqlSession session = SqlConnection.getSession();
        try {
            IRule iRule = session.getMapper(IRule.class);
            rules = iRule.getTimeRuleListByMeasureId(measureId);
            return rules;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }
}
