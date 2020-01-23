package cn.demo.quickdemo.ui.register.bean;

public class HospitalListBean {

    /**
     * organizationName : 北京协和医院
     * organizationId : 15132.0
     * provinceId : 137.0
     * cityId : 1.0
     */

    private String organizationName;
    private int organizationId;
    private int provinceId;
    private int cityId;

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
