package SpongeCity.EvaluationPlatform.DBAccess.DataAccess;

import SpongeCity.EvaluationPlatform.DBAccess.Common.MybatisSqlConnection;
import SpongeCity.EvaluationPlatform.DBAccess.Common.SqlConnection;
import SpongeCity.EvaluationPlatform.DBAccess.Interface.IRule;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiAreaRule;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiTimeRule;
import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/2/22.
 */
public class RuleOperation {
    public List<DiAreaRule> getAreaRuleListByMeasureId(int measureId) throws Exception {
        List<DiAreaRule> rules = new ArrayList<DiAreaRule>();
        SqlSession session = MybatisSqlConnection.getSession();
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
        SqlSession session = MybatisSqlConnection.getSession();
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

    public int insertTimeRuleData(List<DiTimeRule> timeRules) throws Exception {
        try {
            Connection conn = SqlConnection.getConnection();
            Statement stmt = conn.createStatement();
            String strValue = "";
            for (DiTimeRule timeRule : timeRules) {
                StringBuilder sb = new StringBuilder();
                sb.append("(" + timeRule.getPid() + ",")
                        .append(timeRule.getRule() + ",")
                        .append(timeRule.getGrainnumber() + ",")
                        .append(timeRule.getGrain() + ",1),");
                strValue += sb.toString();
            }
            String sql = "insert into di_timerule (pid,rule,grainnumber,grain,datastatus) values " + strValue.substring(0, strValue.lastIndexOf(',') - 1);
            return stmt.executeUpdate(sql);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public int insertAreaRuleData(List<DiAreaRule> areaRules) throws Exception {
        try {
            Connection conn = SqlConnection.getConnection();
            Statement stmt = conn.createStatement();
            String strValue = "";
            for (DiAreaRule areaRule : areaRules) {
                StringBuilder sb = new StringBuilder();
                sb.append("(" + areaRule.getPid() + ",")
                        .append(areaRule.getRule() + ",1),");
                strValue += sb.toString();
            }
            String sql = "insert into di_arearule (pid,rule,datastatus) values " + strValue.substring(0, strValue.lastIndexOf(',') - 1);
            return stmt.executeUpdate(sql);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public int updateTimeRule(List<DiTimeRule> timeRules) throws Exception {
        try {
            Connection conn = SqlConnection.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "";
            for (DiTimeRule timeRule : timeRules) {
                StringBuilder sb = new StringBuilder();
                sb.append("update di_timerule set rule =" + timeRule.getRule() + ",")
                        .append("grainumber = " + timeRule.getGrainnumber() + ",")
                        .append("grain = " + timeRule.getGrain() + " where id = " + timeRule.getId() + ";");
                sql += sb.toString();
            }
            return stmt.executeUpdate(sql);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public int updateAreaRule(List<DiAreaRule> areaRules) throws Exception {
        try {
            Connection conn = SqlConnection.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "";
            for (DiAreaRule areaRule : areaRules) {
                StringBuilder sb = new StringBuilder();
                sb.append("update di_arearule set rule =" + areaRule.getRule() + " where id = " + areaRule.getId() + ";");
                sql += sb.toString();
            }
            return stmt.executeUpdate(sql);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
