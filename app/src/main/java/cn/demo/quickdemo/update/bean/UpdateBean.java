package cn.demo.quickdemo.update.bean;

/**
 * Created by ningjiaqi on 2018/11/22.
 **/
public class UpdateBean {
    private String des;
    private boolean force_update;
    private boolean ios_review;
    private String version;
    private int version_code;
    private String download_url;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isForce_update() {
        return force_update;
    }

    public void setForce_update(boolean force_update) {
        this.force_update = force_update;
    }

    public boolean isIos_review() {
        return ios_review;
    }

    public void setIos_review(boolean ios_review) {
        this.ios_review = ios_review;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }
}
