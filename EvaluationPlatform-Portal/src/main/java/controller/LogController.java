package controller;

import SpongeCity.EvaluationPlatform.Core.BLL.LogBLL;
import SpongeCity.EvaluationPlatform.Core.model.LogModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/3/14.
 */
public class LogController {
    public void getUploadLogList(int pageIndex, int pageSize){
        List<LogModel> logs = new ArrayList<LogModel>();
        LogBLL logBLL=new LogBLL();
        logs = logBLL.getPageDivisionDiLogList(pageIndex,pageSize);
    }
}
