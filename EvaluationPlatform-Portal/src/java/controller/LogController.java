package controller;

import SpongeCity.EvaluationPlatform.Core.BLL.LogBLL;
import SpongeCity.EvaluationPlatform.Core.model.LogModel;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/3/14.
 */
@Controller
@RequestMapping(value = "/log")
public class LogController {
    private final int pageSize = 10;

    @RequestMapping(value = {"/index", "/", ""}, method = RequestMethod.GET)
    @ResponseBody
    public String getUploadLogList(int pageIndex) {
        List<LogModel> logs = new ArrayList<LogModel>();
        LogBLL logBLL = new LogBLL();
        logs = logBLL.getPageDivisionDiLogList(pageIndex, pageSize);
        JSONArray jsonArray = JSONArray.fromObject(logs);
        return jsonArray.toString();
    }
}
