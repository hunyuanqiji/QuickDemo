package cn.demo.quickdemo.ui.account.bean;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Went_Gone on 2017/8/24.
 */

public class LoginBean implements Serializable {
    /**
     *  [
     *         {
     *             "username": "jintuo1",
     *             "realname": "金测试一",
     *             "platformList": [
     *                 {
     *                     "platformUri": "https://xhyydevelopapp.mdruby.cn",
     *                     "weChatUrl": "https://wxdev.mdruby.cn/wxH5Api/interface",
     *                     "platfromAccessKey": "42b891cd",
     *                     "platfromUserId": "311",
     *                     "platformName": "协和营养开发测试平台",
     *                     "token": "A78AE54AF15B01F418620E8A94ABAB1D3439E03E39035F5F37A7B85599A072A098C055A43C685132E92D7415AAA8DC338D0E16B9D246BAD03222D181AB56EE572A070E4A58DBBC66DA0963A29D00D1AB19D90EA05F7A07723EC80271673BCB6A34631164B5283E2AAF29FC7EF48A48ACD361515CCFF89A3E1412773C97EE3F74A30284DACDDC27201397A3F54D62504ECAB0419D27ED748D86FB805FB8EA711B0D3A45050B96C738B4E4F09AB2CC927870538220B5FB800ACEF7E7735D86C7ACEF5ED258C76E87D1C0A45D612EBABCFF904EDB3362F65DFF09652C851E3E32D356A53E60DC9AB53684292F298B2D7ABF9A1B0ED31BF29AC7C7B505D8FF7EF8D8B73ABA52644282BB67C4539BCEC00EC57561482F2E10A2980F5BBE521883A6BB272AD9FDCEA9984A9853330FD22D43304F0FCAD03C0A78A6BD4E14D6FFC09B283B59A0644A368124338A2E0DA999A7743CA9131680D13E43D80C8D84C3B08399C58DFAE2BECB1A4EC47C38EDF70B415A27B9E9E281AB96F64125621EB7E606B244B8689E7EBF5BBCBCB21EEB5FD76FF5A27CAA0C200E87D6E94F851D3E17EA2CEE8ACA6EED2687C82E5AC956DF1FF338943C1FDF2FB3412D09667F498BE3B90AE14A744592A948FDCEEBE09AED9CA82BED6FC8D55457EE947777B28B41612FFDEC0FE8BA0C64B5E8B68D7274E5824B08DD733461AD2DE2FE4EFD78C3185737049972D146F31A353414B4825CF4AF17708605B9EA037E2BB02C775356BC6DE780",
     *                     "status": 0
     *                 }
     *             ]
     *         }
     *     ]
     */

    private String realname;
    private String username;
    private List<PlatformlistBean> platformList;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<PlatformlistBean> getPlatformlist() {
        return platformList;
    }

    public void setPlatformlist(List<PlatformlistBean> platformList) {
        this.platformList = platformList;
    }

    public static class PlatformlistBean implements Serializable{
        /**
         "platformUri": "https://xhyydevelopapp.mdruby.cn",
         *                     "weChatUrl": "https://wxdev.mdruby.cn/wxH5Api/interface",
         *                     "platfromAccessKey": "42b891cd",
         *                     "platfromUserId": "311",
         *                     "platformName": "协和营养开发测试平台",
         *                     "token": "A78AE54AF15B01F418620E8A94ABAB1D3439E03E39035F5F37A7B85599A072A098C055A43C685132E92D7415AAA8DC338D0E16B9D246BAD03222D181AB56EE572A070E4A58DBBC66DA0963A29D00D1AB19D90EA05F7A07723EC80271673BCB6A34631164B5283E2AAF29FC7EF48A48ACD361515CCFF89A3E1412773C97EE3F74A30284DACDDC27201397A3F54D62504ECAB0419D27ED748D86FB805FB8EA711B0D3A45050B96C738B4E4F09AB2CC927870538220B5FB800ACEF7E7735D86C7ACEF5ED258C76E87D1C0A45D612EBABCFF904EDB3362F65DFF09652C851E3E32D356A53E60DC9AB53684292F298B2D7ABF9A1B0ED31BF29AC7C7B505D8FF7EF8D8B73ABA52644282BB67C4539BCEC00EC57561482F2E10A2980F5BBE521883A6BB272AD9FDCEA9984A9853330FD22D43304F0FCAD03C0A78A6BD4E14D6FFC09B283B59A0644A368124338A2E0DA999A7743CA9131680D13E43D80C8D84C3B08399C58DFAE2BECB1A4EC47C38EDF70B415A27B9E9E281AB96F64125621EB7E606B244B8689E7EBF5BBCBCB21EEB5FD76FF5A27CAA0C200E87D6E94F851D3E17EA2CEE8ACA6EED2687C82E5AC956DF1FF338943C1FDF2FB3412D09667F498BE3B90AE14A744592A948FDCEEBE09AED9CA82BED6FC8D55457EE947777B28B41612FFDEC0FE8BA0C64B5E8B68D7274E5824B08DD733461AD2DE2FE4EFD78C3185737049972D146F31A353414B4825CF4AF17708605B9EA037E2BB02C775356BC6DE780",
         *                     "status": 0
         */
        private String platformUri;
        private String weChatUrl;
        private String platfromAccessKey;
        private String platfromUserId;
        private String platformName;
        private String token;
        private int status;
        private String platfromRoleType;
        private String realName;
        private String weChatName;
        private int fullInfo;
        private String signPage;        // 入组协议URL
        private int IsQRCode;//  是否有二维码 0无1有
        public int getIsQRCode() {
            return IsQRCode;
        }

        public void setIsQRCode(int isQRCode) {
            IsQRCode = isQRCode;
        }



        public int getFullInfo() {
            return fullInfo;
        }

        public void setFullInfo(int fullInfo) {
            this.fullInfo = fullInfo;
        }

        public String getPlatformUri() {
            return platformUri;
        }

        public void setPlatformUri(String platformUri) {
            this.platformUri = platformUri;
        }

        public String getWeChatUrl() {
            return weChatUrl;
        }

        public void setWeChatUrl(String weChatUrl) {
            this.weChatUrl = weChatUrl;
        }

        public String getPlatfromAccessKey() {
            return platfromAccessKey;
        }

        public void setPlatfromAccessKey(String platfromAccessKey) {
            this.platfromAccessKey = platfromAccessKey;
        }

        public String getPlatfromUserId() {
            return platfromUserId;
        }

        public void setPlatfromUserId(String platfromUserId) {
            this.platfromUserId = platfromUserId;
        }

        public String getPlatformName() {
            return platformName;
        }

        public void setPlatformName(String platformName) {
            this.platformName = platformName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPlatfromRoleType() {
            return platfromRoleType;
        }

        public void setPlatfromRoleType(String platfromRoleType) {
            this.platfromRoleType = platfromRoleType;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getWeChatName() {
            return weChatName;
        }

        public void setWeChatName(String weChatName) {
            this.weChatName = weChatName;
        }

        public String getSignPage() {
            return signPage;
        }

        public void setSignPage(String signPage) {
            this.signPage = signPage;
        }
    }
}
