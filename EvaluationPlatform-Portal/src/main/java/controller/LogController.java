package controller;

import SpongeCity.EvaluationPlatform.Core.BLL.LogBLL;
import SpongeCity.EvaluationPlatform.Core.model.LogModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/3/14.
 */
@Controller
@RequestMapping(value = "/log")
public class LogController {
    @RequestMapping(value={"/index","/",""})
    public String getUploadLogList(int pageIndex, int pageSize){
        List<LogModel> logs = new ArrayList<LogModel>();
        LogBLL logBLL=new LogBLL();
        logs = logBLL.getPageDivisionDiLogList(pageIndex,pageSize);
        return "/dataimport/index";
    }
}
