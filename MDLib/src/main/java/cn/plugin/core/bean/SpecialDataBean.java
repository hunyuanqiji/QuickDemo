package cn.plugin.core.bean;

import java.io.Serializable;
import java.util.List;


/**
 * data返回的数据为特殊格式，特殊处理下
 * Created by yy on 2018/11/8.
 **/
public class SpecialDataBean<T> implements Serializable {
    /**
     * {"total":31,"list":[{"GroupID":1078,"GroupName":"患者组20181021-3","GroupTypeCID":"10005003","OrgID":63,"Description":"缺陷测试","CreateBy":286,"CreateTime":"2018-10-21T10:00:20.757","UpdateBy":null,"UpdateTime":null,"GroupCID":null,"state":0,"PatientList":[{"Id":15957,"Name":"肖山","Gender":"不明","Age":"不详","Active":"2018-08-27 16:23:39","MainDiagnosis":null},{"Id":15958,"Name":"Hvjh","Gender":"不明","Age":"不详","Active":"2018-09-11 17:21:34","MainDiagnosis":null},{"Id":16016,"Name":"12323","Gender":"男","Age":"不详","Active":"2018-11-07 13:23:27","MainDiagnosis":null}]},{"GroupID":1077,"GroupName":"患者组20181021-2","GroupTypeCID":"10005003","OrgID":63,"Description":"缺陷测试","CreateBy":286,"CreateTime":"2018-10-21T09:59:14.967","UpdateBy":null,"UpdateTime":null,"GroupCID":null,"state":0,"PatientList":[{"Id":16016,"Name":"12323","Gender":"男","Age":"不详","Active":"2018-11-07 13:23:27","MainDiagnosis":null}]}]}]}
     */

    private int total;
    private List<T> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
