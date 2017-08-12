package com.fh.entity.vo;

/**
 * Created by Administrator on 2017/2/9 0009.
 */
public class AdminQueryMemberVo {
    private String addTimeStart;
    private String addTimeEnd;
    private String member_name;

    public AdminQueryMemberVo() {
    }

    public AdminQueryMemberVo(String addTimeStart, String addTimeEnd, String member_name) {
        this.addTimeStart = addTimeStart;
        this.addTimeEnd = addTimeEnd;
        this.member_name = member_name;
    }

    public String getAddTimeStart() {
        return addTimeStart;
    }

    public void setAddTimeStart(String addTimeStart) {
        this.addTimeStart = addTimeStart;
    }

    public String getAddTimeEnd() {
        return addTimeEnd;
    }

    public void setAddTimeEnd(String addTimeEnd) {
        this.addTimeEnd = addTimeEnd;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }
}
