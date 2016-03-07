package SpongeCity.EvaluationPlatform.Core.BLL;

import SpongeCity.EvaluationPlatform.Core.model.AreaModel;
import SpongeCity.EvaluationPlatform.DBAccess.DataAccess.AreaOperation;
import SpongeCity.EvaluationPlatform.DBAccess.Model.DiArea;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2016/2/22.
 */
public class AreaBLL {
    private List<DiArea> diAreas = new ArrayList<DiArea>();
    //arg:di_measure的id字段
    public List<AreaModel> getAreaHierarchy(int measureId) {
        try {
            diAreas = getAreaListByTaxId(measureId);
            if (diAreas != null && diAreas.size() > 0) {
                List<AreaModel> areas = new ArrayList<AreaModel>();

                //获取区域
                List<AreaModel> regions = getRegionAreaList();
                areas.addAll(regions);

                //获取地段
                List<AreaModel> sections = new ArrayList<AreaModel>();
                for (AreaModel region : regions) {
                    List<DiArea> tempAreaList = new ArrayList<DiArea>();
                    tempAreaList = getAreaByParentId(region.getTid());
                    for (DiArea tempArea : tempAreaList) {
                        AreaModel areaModel = new AreaModel();
                        areaModel = convertAreaModel(tempArea);
                        areaModel.setHierarchy(2);
                        areaModel.setRegionName(region.getRegionName());
                        areaModel.setSectionName(tempArea.getName());
                        sections.add(areaModel);
                    }
                }
                areas.addAll(sections);

                //获取单项措施
                List<AreaModel> devices = new ArrayList<AreaModel>();
                for (AreaModel section : sections) {
                    List<DiArea> tempAreaList = new ArrayList<DiArea>();
                    tempAreaList = getAreaByParentId(section.getTid());
                    for (DiArea tempArea : tempAreaList) {
                        AreaModel areaModel = new AreaModel();
                        areaModel = convertAreaModel(tempArea);
                        areaModel.setHierarchy(3);
                        areaModel.setRegionName(section.getRegionName());
                        areaModel.setSectionName(section.getSectionName());
                        areaModel.setDeviceName(tempArea.getName());
                        devices.add(areaModel);
                    }
                }
                areas.addAll(devices);

                return areas;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    private List<DiArea> getAreaListByTaxId(int measureId) {
        List<DiArea> areas = new ArrayList<DiArea>();
        try {
            AreaOperation ao = new AreaOperation();
            areas = ao.getAreaListByMeasureId(measureId);
            return areas;
        } catch (Exception e) {
            return null;
        }
    }

    private List<AreaModel> getRegionAreaList() {
        List<AreaModel> areas = new ArrayList<AreaModel>();
        try {
            for (DiArea diArea : diAreas) {
                if (diArea.getParentId() == 0) {
                    AreaModel area = new AreaModel();
                    area = convertAreaModel(diArea);
                    area.setName(diArea.getName());
                    area.setRegionName(diArea.getName());
                    area.setHierarchy(1);
                    areas.add(area);
                }
            }
            return areas;
        } catch (Exception e) {
            return null;
        }
    }

    private AreaModel convertAreaModel(DiArea diArea) {
        AreaModel result = new AreaModel();
        result.setDatastatus(diArea.getDatastatus());
        result.setId(diArea.getId());
        result.setMid(diArea.getMid());
        result.setTid(diArea.getTid());
        result.setName(diArea.getName());
        result.setParentId(diArea.getParentId());
        return result;
    }

    private List<DiArea> getAreaByParentId(int tid) {
        List<DiArea> result = new ArrayList<DiArea>();
        for (DiArea item : diAreas) {
            if (item.getParentId() == tid) {
                result.add(item);
            }
        }
        return result;
    }
}
