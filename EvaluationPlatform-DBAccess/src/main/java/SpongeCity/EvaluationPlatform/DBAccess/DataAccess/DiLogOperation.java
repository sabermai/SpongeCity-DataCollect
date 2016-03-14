package SpongeCity.EvaluationPlatform.DBAccess.DataAccess;

import SpongeCity.EvaluationPlatform.DBAccess.Common.MybatisSqlConnection;
import SpongeCity.EvaluationPlatform.DBAccess.Common.SqlConnection;
import SpongeCity.EvaluationPlatform.DBAccess.Interface.IDiLog;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiLog;
import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by saber on 2016/2/22.
 */
public class DiLogOperation {
    public List<DiLog> getPageDivisionDiLogList(int pageIndex, int pageSize) throws Exception {
        List<DiLog> diLogs = new ArrayList<DiLog>();
        SqlSession session = MybatisSqlConnection.getSession();
        try {
            Map<String, Integer> params = new HashMap<String, Integer>();
            params.put("pageSize", pageSize);
            params.put("itemIndex", (pageIndex - 1) * pageSize);
            IDiLog iDiLog = session.getMapper(IDiLog.class);
            diLogs = iDiLog.getPageDivisionDiLogList(params);
            return diLogs;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    public int getDiLogCount() throws Exception {
        SqlSession session = MybatisSqlConnection.getSession();
        try {
            IDiLog iDiLog = session.getMapper(IDiLog.class);
            return iDiLog.getDiLogCount();
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    public int saveLog(String fileName, int measuerId) throws Exception {
        try {
            Connection conn = SqlConnection.getConnection();
            Statement stmt = conn.createStatement();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(new Date());
            String sql = "insert into di_log (import_time,file_name,mid,status,datastatus) values ('" + date + "','" + fileName + "'," + measuerId + ",0,0)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                return id;
            } else {
                return -1;
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public int updateLog(int id) throws Exception {
        try {
            Connection conn = SqlConnection.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "update di_log set status = 1, datastatus = 1 where id = " + id;
            return stmt.executeUpdate(sql);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
