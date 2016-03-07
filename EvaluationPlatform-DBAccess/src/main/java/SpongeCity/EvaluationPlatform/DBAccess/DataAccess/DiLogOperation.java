package SpongeCity.EvaluationPlatform.DBAccess.DataAccess;

import SpongeCity.EvaluationPlatform.DBAccess.Common.SqlConnection;
import SpongeCity.EvaluationPlatform.DBAccess.Interface.IDiLog;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiLog;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by saber on 2016/2/22.
 */
public class DiLogOperation {
    public List<DiLog> getPageDivisionDiLogList(int pageIndex, int pageSize) throws Exception {
        List<DiLog> diLogs = new ArrayList<DiLog>();
        SqlSession session = SqlConnection.getSession();
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
        SqlSession session = SqlConnection.getSession();
        try {
            IDiLog iDiLog = session.getMapper(IDiLog.class);
            return iDiLog.getDiLogCount();
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    public boolean saveData(Map params){
        return true;
    }
}
