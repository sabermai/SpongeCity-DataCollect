package SpongeCity.EvaluationPlatform.Core.model;

/**
 * Created by saber on 2016/2/22.
 */
public class AreaModel {
    private int id;
    private int mid;
    private int m_tid;
    private int tid;
    private int datastatus;
    private int hierarchy;
    private int parentId;
    private String name;
    private String regionName;
    private String sectionName;
    private String deviceName;

    public int getM_tid() {
        return m_tid;
    }

    public void setM_tid(int m_tid) {
        this.m_tid = m_tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getDatastatus() {
        return datastatus;
    }

    public void setDatastatus(int datastatus) {
        this.datastatus = datastatus;
    }

    public int getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(int hierarchy) {
        this.hierarchy = hierarchy;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
