package SpongeCity.EvaluationPlatform.DBAccess.DataAccess;

import SpongeCity.EvaluationPlatform.DBAccess.Common.SqlConnection;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiAreaRule;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiTimeRule;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiWeight;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by saber on 2016/3/3.
 */
public class DataOperation {
    public int saveImportData(Map<String, Object> params) throws Exception {
        try {
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/assess", "root", "Passw0rd");
            Connection conn = SqlConnection.getConnection();
            Statement stmt = conn.createStatement();
            String tableName = params.get("table").toString();
            String strParams = params.get("params").toString();
            List<String> strValues = (List<String>) params.get("values");
            String valueParam = "";
            for (String strValue : strValues) {
                valueParam += "(" + strValue + "),";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("insert into ").append(tableName).append(" (").append(strParams).append(") values ")
                    .append(valueParam.substring(0, valueParam.lastIndexOf(',') - 1));
            return stmt.executeUpdate(sb.toString());
        } catch (Exception ex) {
            throw ex;
        }
    }

    public int deleteData(int logId, String tableName) throws Exception {
        try {
            Connection conn = SqlConnection.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "delete " + tableName + " where uploadid = " + logId;
            return stmt.executeUpdate(sql);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
