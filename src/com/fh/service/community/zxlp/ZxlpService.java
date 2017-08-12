package com.fh.service.community.zxlp;

import com.fh.dao.DaoSupport;
import com.fh.entity.bo.TokenBO;
import com.fh.entity.bo.UserInfoBO;
import com.fh.entity.vo.*;
import com.fh.entity.zxlp.ZxlpCommonVo;
import com.fh.entity.zxzq.*;
import com.fh.util.MD5;
import com.fh.util.RandomCode;
import com.fh.util.StringUtil;
import com.fh.util.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.*;


@Service("zxlpService")
public class ZxlpService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    //积分查询
    public ZxlpCommonVo queryIntegration(ZxlpCommonVo zxlpCommonVo) throws Exception {
        return (ZxlpCommonVo) dao.findForObject("Zxlp.queryIntegration",zxlpCommonVo);
    }
    //积分下单
    public void zxlpOrder (ZxlpCommonVo zxlpCommonVo) throws Exception{
        zxlpCommonVo.setOuter_trn_id(getSecuritiesid());
        dao.save("Zxlp.zxlpOrder" ,zxlpCommonVo);
    }

    //查询订单是否存在
    public ZxlpCommonVo queryOrder(ZxlpCommonVo zxlpCommonVo) throws Exception {
        return (ZxlpCommonVo) dao.findForObject("Zxlp.queryOrder",zxlpCommonVo);
    }

    //下单时扣除积分
    public void zxlpIntegrationBack (ZxlpCommonVo zxlpCommonVo) throws Exception{
        dao.update("Zxlp.zxlpIntegrationBack" ,zxlpCommonVo);
    }

    //更改订单状态
    public void orderBack (ZxlpCommonVo zxlpCommonVo) throws Exception{
        zxlpCommonVo.setOuter_trn_id(getSecuritiesid());
        dao.update("Zxlp.orderBack" ,zxlpCommonVo );
    }


    //退单时还原积分
    public void zxlpIntegrationAdd (ZxlpCommonVo zxlpCommonVo) throws Exception{
        dao.update("Zxlp.zxlpIntegrationAdd" ,zxlpCommonVo);
    }



    /**
     * 流水号(订单号)
     *
     * @return
     */
    public String getSecuritiesid() {
        int j = (int) (Math.random() * (9999 - 1000 + 1)) + 1000;//产生1000-9999的随机数
        String i = Tools.getSysDateString("yyyyMMddHHmmss") + j;
        return i;
    }


}