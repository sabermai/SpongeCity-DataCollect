package DataOperation;

import SpongeCity.EvaluationPlatform.Core.BLL.MeasureBLL;
import SpongeCity.EvaluationPlatform.Core.model.MeasureModel;
import SpongeCity.EvaluationPlatform.Core.model.ParamModel;
import SpongeCity.EvaluationPlatform.Core.model.DataModel;
import SpongeCity.EvaluationPlatform.Core.model.ParamDataModel;
import SpongeCity.EvaluationPlatform.Core.model.MeasureDataModel;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by saber on 2016/3/2.
 */
public class ExcelReader {
    public void getExcelData(File file, int measureId) {
        MeasureDataModel measureDataModel = new MeasureDataModel();
        List<DataModel> dataModels = new ArrayList<DataModel>();
        Map<Integer, ParamModel> columnModels = new HashMap<Integer, ParamModel>();
        //获取数据库表字段
        List<ParamModel> paramModels = new ArrayList<ParamModel>();
        MeasureBLL measureBLL = new MeasureBLL();
        paramModels = measureBLL.getParamsByMeasureId(measureId);
        //获取指标信息
        MeasureModel measureModel = measureBLL.getMeasureByMeasuerId(measureId);
        measureDataModel.setDbtablename(measureModel.getTable_name());
        measureDataModel.setMeasuername(measureModel.getName());
        measureDataModel.setMid(measureId);
        measureDataModel.setTid(measureModel.getTid());

        Workbook book = getWorkbook(file);
        if (book == null) {
            return;
        }

        for (int i = 0; i < book.getNumberOfSheets(); i++) {
            Sheet sheet = book.getSheetAt(i);
            boolean isFirstRow = true;
            for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
                //region 处理一行数据
                Row row = sheet.getRow(j);
                if (row == null) {
                    if (isFirstRow) {
                        //获取列头对象列表
                        for (int k = 0; k < row.getPhysicalNumberOfCells(); k++) {
                            Cell cell = row.getCell(k);
                            if (cell != null) {
                                for (ParamModel paramModel : paramModels) {
                                    if (paramModel.getDisplayname().trim().equals(cell.getStringCellValue().trim())) {
                                        columnModels.put(k, paramModel);
                                        break;
                                    }
                                }
                            }
                        }
                        isFirstRow = false;
                    } else {
                        //获取数据列
                        DataModel dataModel = new DataModel();
                        Map<String, ParamDataModel> paramMap = new HashMap<String, ParamDataModel>();
                        for (int k = 0; k < row.getPhysicalNumberOfCells(); k++) {
                            Cell cell = row.getCell(k);
                            ParamModel param = columnModels.get(k);
                            if (cell != null) {
                                if (param.getDisplayname().trim().equals("区域")) {
                                    dataModel.setRegion(cell.getStringCellValue());
                                } else if (param.getDisplayname().trim().equals("地段")) {
                                    dataModel.setSection(cell.getStringCellValue());
                                } else if (param.getDisplayname().trim().equals("单项措施")) {
                                    dataModel.setDevice(cell.getStringCellValue());
                                } else if (param.getDisplayname().trim().equals("采样时间")) {
                                    dataModel.setSamplingtime(cell.getDateCellValue());
                                } else {
                                    ParamDataModel paramDataModel = new ParamDataModel();
                                    paramDataModel.setParamDisplayName(param.getDisplayname());
                                    paramDataModel.setParamName(param.getName());
                                    paramDataModel.setValue(cell.getNumericCellValue());
                                    paramDataModel.setIsCalculable(param.isCalculable());
                                    paramMap.put(param.getDisplayname(), paramDataModel);
                                }
                            }
                        }
                        dataModel.setData(paramMap);
                        dataModels.add(dataModel);
                    }
                }
                //endregion
            }
        }
        measureDataModel.setDataModels(dataModels);
    }

    //get the excel workbook(version:2003 or 2007)
    private Workbook getWorkbook(File file) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            if (POIXMLDocument.hasOOXMLHeader(in)) {
                return new XSSFWorkbook(in);
            } else {
                return new HSSFWorkbook(in);
            }
        } catch (IOException e) {
            return null;
        }
    }
}
