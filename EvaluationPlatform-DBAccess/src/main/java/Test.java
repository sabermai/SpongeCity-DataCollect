import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.TaxonomyOperation;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiTaxonomy;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by saber on 2016/2/22.
 */
public class Test {
    public static void main(String[] args) {
        try {
            Class c = Class.forName("SpongeCity.EvaluationPlatform.DBAccess.DataAccess.DiLogOperation");
            Object o = c.newInstance();
            Method m = c.getMethod("saveData", new Class[]{Map.class});
            Map<String, String> map = new HashMap<String, String>();
            Object[] arguments = new Object[]{map};
            m.invoke(o, arguments);
        } catch (Exception ex) {
            System.out.print("error");
        }
    }
}
