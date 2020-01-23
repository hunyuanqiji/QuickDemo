package cn.demo.quickdemo.ui.register.bean;

import java.util.List;

public class CityBean {


    /**
     * ProvinceID : 137.0
     * ProvinceName : 北京市
     * CityList : [{"CityID":1,"CityName":"东城区"},{"CityID":2,"CityName":"西城区"},{"CityID":5,
     * "CityName":"朝阳区"},{"CityID":6,"CityName":"丰台区"},{"CityID":7,"CityName":"石景山区"},
     * {"CityID":8,"CityName":"海淀区"},{"CityID":9,"CityName":"门头沟区"},{"CityID":10,
     * "CityName":"房山区"},{"CityID":11,"CityName":"通州区"},{"CityID":12,"CityName":"顺义区"},
     * {"CityID":13,"CityName":"昌平区"},{"CityID":14,"CityName":"大兴区"},{"CityID":15,
     * "CityName":"怀柔区"},{"CityID":16,"CityName":"平谷区"},{"CityID":17,"CityName":"密云县"},
     * {"CityID":18,"CityName":"延庆县"}]
     */

    private int ProvinceID;
    private String ProvinceName;
    private List<CityListBean> CityList;

    public int getProvinceID() {
        return ProvinceID;
    }

    public void setProvinceID(int ProvinceID) {
        this.ProvinceID = ProvinceID;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String ProvinceName) {
        this.ProvinceName = ProvinceName;
    }

    public List<CityListBean> getCityList() {
        return CityList;
    }

    public void setCityList(List<CityListBean> CityList) {
        this.CityList = CityList;
    }

    public static class CityListBean {
        /**
         * CityID : 1.0
         * CityName : 东城区
         */

        private int CityID;
        private String CityName;

        public int getCityID() {
            return CityID;
        }

        public void setCityID(int CityID) {
            this.CityID = CityID;
        }

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String CityName) {
            this.CityName = CityName;
        }
    }
}
