package controller;

import SpongeCity.EvaluationPlatform.Core.BLL.DataBLL;
import SpongeCity.EvaluationPlatform.Core.BLL.LogBLL;
import SpongeCity.EvaluationPlatform.Core.BLL.MeasureBLL;
import SpongeCity.EvaluationPlatform.Core.model.LogModel;
import SpongeCity.EvaluationPlatform.Core.model.MeasureModel;
import SpongeCity.EvaluationPlatform.Core.model.TaxModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/3/14.
 */
@Controller
@RequestMapping(value = "/dataimport")
public class DataImportController {
    private final int pageSize = 10;

    @RequestMapping(value = {"/index", "/", ""}, method = RequestMethod.GET)
    public ModelAndView Index() {
        ModelAndView modelAndView = new ModelAndView("/dataimport/index");
        MeasureBLL measureBLL = new MeasureBLL();
        List<TaxModel> taxList = measureBLL.getTaxonomyList();

        LogBLL logBLL = new LogBLL();
        List<LogModel> logList = logBLL.getPageDivisionDiLogList(1, pageSize);
        int logCount = logBLL.getLogCount();
        modelAndView.addObject("taxs", taxList);
        modelAndView.addObject("logs", logList);
        modelAndView.addObject("logPageCount", (int)Math.ceil((float)logCount / pageSize));
        return modelAndView;
    }

    @RequestMapping(value = {"/deletedata"}, method = RequestMethod.POST)
    @ResponseBody
    public int deletData(int logId, String tableName) {
        try {
            DataBLL dataBLL = new DataBLL();
            return dataBLL.deleteData(logId, tableName);
        } catch (Exception ex) {
            return -1;
        }
    }
}
