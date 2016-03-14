package SpongeCity.EvaluationPlatform.Core.BLL;

import SpongeCity.EvaluationPlatform.Core.model.LogModel;
import SpongeCity.EvaluationPlatform.Core.model.MeasureModel;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.DiLogOperation;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.MeasureOperation;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiLog;

import java.util.ArrayList;
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
        logModel.setImporttime(diLog.getImport_time());

        MeasureBLL measureBLL = new MeasureBLL();
        MeasureModel measure = measureBLL.getMeasureByMeasuerId(diLog.getMid());
        logModel.setMeasurename(measure.getName());
        logModel.setTablename(measure.getTable_name());
        logModel.setTaxname(measure.getTax_name());
        return logModel;
    }
}
