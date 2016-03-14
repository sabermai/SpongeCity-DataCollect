package SpongeCity.EvaluationPlatform.DBAccess.DataAccess;

import SpongeCity.EvaluationPlatform.DBAccess.Common.MybatisSqlConnection;
import SpongeCity.EvaluationPlatform.DBAccess.Interface.IParam;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiParam;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/2/22.
 */
public class ParamOperation {
    public List<DiParam> getParamListByMeasureId(int measureId) throws Exception {
        List<DiParam> params = new ArrayList<DiParam>();
        SqlSession session = MybatisSqlConnection.getSession();
        try {
            IParam iParam = session.getMapper(IParam.class);
            params = iParam.getParamListByMeasureId(measureId);
            return params;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }
}
