package SpongeCity.EvaluationPlatform.DBAccess.Interface;

import SpongeCity.EvaluationPlatform.DBAccess.Model.DiLog;

import java.util.List;
import java.util.Map;

/**
 * Created by saber on 2016/2/22.
 */
public interface IDiLog {
    List<DiLog> getPageDivisionDiLogList(Map params);
    Integer getDiLogCount();
}
