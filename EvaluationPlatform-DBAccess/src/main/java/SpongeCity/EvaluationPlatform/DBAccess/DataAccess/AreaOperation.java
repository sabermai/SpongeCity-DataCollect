package SpongeCity.EvaluationPlatform.DBAccess.DataAccess;

import SpongeCity.EvaluationPlatform.DBAccess.Common.MybatisSqlConnection;
import SpongeCity.EvaluationPlatform.DBAccess.Common.SqlConnection;
import SpongeCity.EvaluationPlatform.DBAccess.Interface.IArea;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiArea;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiAreaRule;
import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/2/22.
 */
public class AreaOperation {
    public List<DiArea> getAreaListByMeasureId(int measureId) throws Exception {
        List<DiArea> areas = new ArrayList<DiArea>();
        SqlSession session = MybatisSqlConnection.getSession();
        try {
            IArea iArea = session.getMapper(IArea.class);
            areas = iArea.getAreaListByMeasureId(measureId);
            return areas;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }
}
