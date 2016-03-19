package controller;

import DataOperation.ExcelReader;
import SpongeCity.EvaluationPlatform.Core.BLL.DataBLL;
import SpongeCity.EvaluationPlatform.Core.BLL.LogBLL;
import SpongeCity.EvaluationPlatform.Core.BLL.MeasureBLL;
import SpongeCity.EvaluationPlatform.Core.model.LogModel;
import SpongeCity.EvaluationPlatform.Core.model.MeasureModel;
import SpongeCity.EvaluationPlatform.Core.model.TaxModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by saber on 2016/3/14.
 */
@Controller
@RequestMapping(value = {"", "/", "/dataimport"})
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
        modelAndView.addObject("logPageCount", (int) Math.ceil((float) logCount / pageSize));
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

    @RequestMapping("/fileupload")
    public ModelAndView springUpload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request, int mid) throws IOException {
        CommonsMultipartResolver mutilpartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (mutilpartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> it = multiRequest.getFileNames();
            while (it.hasNext()) {
                MultipartFile fileDetail = multiRequest.getFile(it.next());
                if (fileDetail != null && fileDetail.getSize() > 0) {
                    String fileName = fileDetail.getOriginalFilename();
                    //String path = this.getClass().getClassLoader().getResource("").getPath().replace("classes", "UploadFiles").replace("WEB-INF/", "") + fileName;
                    File localFile = new File(fileName);
                    fileDetail.transferTo(localFile);
                    ExcelReader excelReader = new ExcelReader(localFile, mid);
                    Thread thread = new Thread(excelReader);
                    thread.start();
                }
            }
        }
        return Index();
    }
}
