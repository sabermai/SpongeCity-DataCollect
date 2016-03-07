package SpongeCity.EvaluationPlatform.DBAccess.DataAccess;

import SpongeCity.EvaluationPlatform.DBAccess.Common.SqlConnection;
import SpongeCity.EvaluationPlatform.DBAccess.Interface.IMeasure;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiMeasure;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/2/22.
 */
public class MeasureOperation {
    //通过指标分类Id获取指标列表
    public List<DiMeasure> getDiMeasureListByDiTaxId(int diTaxonomyId) throws Exception {
        List<DiMeasure> measures = new ArrayList<DiMeasure>();
        SqlSession session = SqlConnection.getSession();
        try {
            IMeasure iMeasure = session.getMapper(IMeasure.class);
            measures = iMeasure.getDiMeasureListByDiTaxId(diTaxonomyId);
            return measures;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    public DiMeasure getDiMeasureById(int measuerId) throws Exception {
        DiMeasure measure = new DiMeasure();
        SqlSession session = SqlConnection.getSession();
        try {
            IMeasure iMeasure = session.getMapper(IMeasure.class);
            measure = iMeasure.getDiMeasureById(measuerId);
            return measure;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }
}
