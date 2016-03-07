package SpongeCity.EvaluationPlatform.DBAccess.DataAccess;

import SpongeCity.EvaluationPlatform.DBAccess.Common.SqlConnection;
import SpongeCity.EvaluationPlatform.DBAccess.Interface.IWeight;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiLog;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiWeight;
import org.apache.ibatis.session.SqlSession;

import javax.imageio.event.IIOWriteProgressListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/3/2.
 */
public class WeightOperation {
    public List<DiWeight> getWeightListByMeasureId(int measureId) throws Exception {
        List<DiWeight> weights = new ArrayList<DiWeight>();
        SqlSession session = SqlConnection.getSession();
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
}
