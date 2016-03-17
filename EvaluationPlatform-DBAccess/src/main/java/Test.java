import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.DataOperation;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.MeasureOperation;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.TaxonomyOperation;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiMeasure;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiTaxonomy;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by saber on 2016/2/22.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        MeasureOperation operation = new MeasureOperation();
        DiMeasure measure = operation.getDiMeasureById(3);
    }
}
