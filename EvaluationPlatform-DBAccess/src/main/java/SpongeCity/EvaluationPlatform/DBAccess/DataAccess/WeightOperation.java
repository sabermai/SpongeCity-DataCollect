package SpongeCity.EvaluationPlatform.DBAccess.DataAccess;

import SpongeCity.EvaluationPlatform.DBAccess.Common.MybatisSqlConnection;
import SpongeCity.EvaluationPlatform.DBAccess.Common.SqlConnection;
import SpongeCity.EvaluationPlatform.DBAccess.Interface.IWeight;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiWeight;
import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/3/2.
 */
public class WeightOperation {
    public List<DiWeight> getWeightListByMeasureId(int measureId) throws Exception {
        List<DiWeight> weights = new ArrayList<DiWeight>();
        SqlSession session = MybatisSqlConnection.getSession();
        try {
            IWeight iWeight = session.getMapper(IWeight.class);
            weights = iWeight.getWeightListByMeasureId(measureId);
            return weights;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    public int insertWeightData(List<DiWeight> weights) throws Exception {
        try {
            Connection conn = SqlConnection.getConnection();
            Statement stmt = conn.createStatement();
            String strValue = "";
            for (DiWeight weight : weights) {
                StringBuilder sb = new StringBuilder();
                sb.append("(" + weight.getMid() + ",")
                        .append(weight.getAid() + ",")
                        .append(weight.getWeight() + ",1),");
                strValue += sb.toString();
            }
            String sql = "insert into di_weight (mid,aid,weight,datastatus) values " + strValue.substring(0, strValue.lastIndexOf(',') - 1);
            return stmt.executeUpdate(sql);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public int updateWeightData(List<DiWeight> weights) throws Exception {
        try {
            Connection conn = SqlConnection.getConnection();
            Statement stmt = conn.createStatement();
            int count = 0;
            for (DiWeight weight : weights) {
                count += stmt.executeUpdate("update di_weight set weight =" + weight.getWeight() + " where id = " + weight.getId() + ";");
            }
            return count;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
