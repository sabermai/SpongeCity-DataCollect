package SpongeCity.EvaluationPlatform.DBAccess.DataAccess;

import SpongeCity.EvaluationPlatform.DBAccess.Common.MybatisSqlConnection;
import SpongeCity.EvaluationPlatform.DBAccess.Interface.ITaxonomy;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiTaxonomy;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/2/22.
 */
public class TaxonomyOperation {
    //获取所有指标分类
    public List<DiTaxonomy> getAllTaxonomy() throws Exception {
        List<DiTaxonomy> taxonomies = new ArrayList<DiTaxonomy>();
        SqlSession session = MybatisSqlConnection.getSession();
        try {
            ITaxonomy iTaxonomy = session.getMapper(ITaxonomy.class);
            taxonomies = iTaxonomy.getAllDiTaxonomy();
            return taxonomies;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    public DiTaxonomy getTaxonomyById(int id) throws Exception {
        DiTaxonomy taxonomy = new DiTaxonomy();
        SqlSession session = MybatisSqlConnection.getSession();
        try {
            ITaxonomy iTaxonomy = session.getMapper(ITaxonomy.class);
            taxonomy = iTaxonomy.getTaxonomyById(id);
            return taxonomy;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }
}
