package cn.demo.quickdemo.ui.register.bean;

import java.io.Serializable;

public class PerfectInfoBean implements Serializable {

    //真实姓名
    private String  realName;
    //职业  是医生还是其他  传ID不用传Name   1为医生 2为其他
    private int profession;
    //医院organizationID  如果是自己添加的医院 传0
    private int orgId;
    //医院的名称  可能是自己添加的医院也可能是选的  根据orgid判断即可
    private String  orgName;
    //医院所属的省份  如果是自己添加的医院 传0
    private int provinceId;
    //医院所属的市   如果是自己添加的医院 传0
    private int cityId;
    //科室  传ID不传Name
    private int deptId;

    //职称  传cid不传Name  如职称是其他 传  1000045
    private String  profLevelCID;
    //单位名称 如果是职业选医生  传空就行
    private String  unitName;
    //部门 如果是职业选医生  传空就行
    private String  dept;
    //职位 如果是职业选医生  传空就行
    private String position;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getProfession() {
        return profession;
    }

    public void setProfession(int profession) {
        this.profession = profession;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getProfLevelCID() {
        return profLevelCID;
    }

    public void setProfLevelCID(String profLevelCID) {
        this.profLevelCID = profLevelCID;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
