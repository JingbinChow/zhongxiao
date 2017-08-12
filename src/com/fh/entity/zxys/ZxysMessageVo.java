package com.fh.entity.zxys;

import org.apache.james.mime4j.field.datetime.DateTime;

import java.util.Date;

/**
 * Created by Administrator on 2016/12/1.
 */
public class ZxysMessageVo {
    private int id;                 //记录编号
    private String content;         //消息内容
    private int	type;               //消息类型（0：文本；1：语音；2：图片；3：小视频；4：系统公告）
    private int from_id;	        //发送者id
    private int to_id;	            //接收者id
    private Date send_time;		//发送时间
    private Date receive_time;  //接收时间
    private int is_open;            //是否公开消息（0：公开；1：不公开）
    private int is_group;		    //是否群组消息（0:不是；1：是）
    private int is_read;		    //是否已读（0：已读；1：未读）
    private String extend;	        //扩展内容
    private int chatroom_id;	    //聊天室id（预留字段）
    private int user_type;	        //用户类型（0：患者；1：医生）
    private int group_id;	        //群组id(预留)
    private  int session_id;        //回话id
    private int is_end;             //是否结束对话（0：未结束；1：结束）

    public int getIs_end() {
        return is_end;
    }

    public void setIs_end(int is_end) {
        this.is_end = is_end;
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    public Date getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(Date receive_time) {
        this.receive_time = receive_time;
    }

    public int getIs_open() {
        return is_open;
    }

    public void setIs_open(int is_open) {
        this.is_open = is_open;
    }

    public int getIs_group() {
        return is_group;
    }

    public void setIs_group(int is_group) {
        this.is_group = is_group;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public int getChatroom_id() {
        return chatroom_id;
    }

    public void setChatroom_id(int chatroom_id) {
        this.chatroom_id = chatroom_id;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }


}
