package com.fh.entity.zxzq;

/**
 * Created by Administrator on 2016/11/15 0015.
 */
public class DoRegisterVo {

        private String userName;
        private String passWord;
        private String mobile;
        private String inviter;
        private String email;
        private String address;
        private String name;
        private String sex;
        private String idcard;
        private String bankName;
        private String bankcard;
        private String area;
        private String code;
    private String teamSign;

        private String cardType;
    private String network_team;
    private String network_id;
    private String member_sn;

    public DoRegisterVo() {
    }

    public DoRegisterVo(String userName, String passWord, String mobile, String inviter, String email, String address, String name, String sex, String idcard, String bankName, String bankcard, String area, String cardType,String network_id,String network_team,String member_sn) {
        this.userName = userName;
        this.passWord = passWord;
        this.mobile = mobile;
        this.inviter = inviter;
        this.email = email;
        this.address = address;
        this.name = name;
        this.sex = sex;
        this.idcard = idcard;
        this.bankName = bankName;
        this.bankcard = bankcard;
        this.area = area;
        this.cardType = cardType;
        this.network_id = network_id;
        this.network_team = network_team;
        this.member_sn = member_sn;
    }


    public String getMember_sn() {
        return member_sn;
    }

    public void setMember_sn(String member_sn) {
        this.member_sn = member_sn;
    }

    public String getNetwork_team() {
        return network_team;
    }

    public void setNetwork_team(String network_team) {
        this.network_team = network_team;
    }

    public String getNetwork_id() {
        return network_id;
    }

    public void setNetwork_id(String network_id) {
        this.network_id = network_id;
    }

    public String getTeamSign() {
        return teamSign;
    }

    public void setTeamSign(String teamSign) {
        this.teamSign = teamSign;
    }

    public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getInviter() {
            return inviter;
        }

        public void setInviter(String inviter) {
            this.inviter = inviter;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankcard() {
            return bankcard;
        }

        public void setBankcard(String bankcard) {
            this.bankcard = bankcard;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }


}
