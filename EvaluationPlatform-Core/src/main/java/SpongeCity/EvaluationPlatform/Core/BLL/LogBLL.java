package SpongeCity.EvaluationPlatform.Core.BLL;

import SpongeCity.EvaluationPlatform.Core.model.LogModel;
import SpongeCity.EvaluationPlatform.Core.model.MeasureModel;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.DiLogOperation;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.MeasureOperation;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by saber on 2016/3/14.
 */
public class LogBLL {
    public List<LogModel> getPageDivisionDiLogList(int pageIndex, int pageSize) {
        try {
            DiLogOperation logOperation = new DiLogOperation();
            List<LogModel> logs = new ArrayList<LogModel>();
            List<DiLog> diLogs = logOperation.getPageDivisionDiLogList(pageIndex, pageSize);
            for (DiLog diLog : diLogs) {
                logs.add(convertLogModel(diLog));
            }
            return logs;
        } catch (Exception e) {
            return null;
        }
    }

    public int getLogCount(){
        DiLogOperation logOperation = new DiLogOperation();
        try {
            return logOperation.getDiLogCount();
        } catch (Exception e) {
            return -1;
        }
    }

    private LogModel convertLogModel(DiLog diLog) {
        LogModel logModel = new LogModel();
        logModel.setId(diLog.getId());
        logModel.setFilename(diLog.getFile_name());
        Date importTime = diLog.getImport_time();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        logModel.setImporttime(sdf.format(importTime));
        logModel.setMeasurename(diLog.getMeasure_name());
        logModel.setTablename(diLog.getTable_name());
        logModel.setTaxname(diLog.getTaxonomy_name());
        return logModel;
    }
}
