package com.fh.entity.angle;

import java.math.BigDecimal;

/**
 * Created by admin on 2017/2/14.
 * 直播礼物
 */
public class AngleVideo {
    private Integer typeId;//视频分类Id
    private String code;//状态码
    private String  typeName;//视频分类的名称
    private String desc;//视频分类的描述信息
    private String number;//该类别下的视频文件数量
    private String isDel;//是否允许被删除（默认分类不允许删除），1：允许，0：不允许
    private String createTime;//视频分类的创建时间（单位：毫秒）
    private String  msg;//错误信息
    private String  description;//否	视频分类的描述信息

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
