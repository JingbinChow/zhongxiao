package com.fh.service.angle;

import com.fh.dao.DaoSupport;
import com.fh.entity.angle.*;
import com.fh.entity.bo.TokenBO;
import com.fh.entity.vo.TokenVo;
import com.fh.entity.zxys.ZxysDirectSeedingBo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/2/13.
 */
@Service("angleService")
public class AngleService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 校验token
     * @param tokenVo
     * @return
     */
    public TokenBO CheckAngleToken(TokenVo tokenVo) throws Exception {
        TokenBO tokenBO = (TokenBO) dao.findForObject("AngleMapper.CheckAngleToken", tokenVo);
        return tokenBO;
    }

    /**
     * 用户登录校验
     * @param memberLoginVO
     * @return
     */
    public AngleMemberLoginBO findMemberByPwd(AngleMemberLoginVO memberLoginVO) throws Exception {
        AngleMemberLoginBO memberLoginBO= null;
        memberLoginBO=(AngleMemberLoginBO) dao.findForObject("AngleMapper.tszbLogin",memberLoginVO);
        if(memberLoginBO==null) {
            memberLoginBO = (AngleMemberLoginBO) dao.findForObject("AngleMapper.findMemberByPwd", memberLoginVO);
        }
        return memberLoginBO;
    }

    /**
     * 用户重复注册校验
     * @param memberLoginVO
     * @return
     */
    public AngleMemberLoginBO findMemberByMobile(AngleMemberLoginVO memberLoginVO) throws Exception {
        AngleMemberLoginBO memberLoginBO = (AngleMemberLoginBO) dao.findForObject("AngleMapper.findMemberByMobile", memberLoginVO);
        return memberLoginBO;
    }

    /**
     * 用户注册
     * @param memberLoginVO
     * @return
     */
    public boolean reg(AngleMemberLoginVO memberLoginVO) throws Exception {
        dao.save("AngleMapper.reg", memberLoginVO);
        AngleMemberLoginBO memberLoginBO = findMemberByMobile(memberLoginVO);
        if(memberLoginBO == null) {
            return false;
        }else {
            this.initMember(memberLoginBO);
            return true;
        }

    }
    public void initMember(AngleMemberLoginBO angleMemberLoginBO) throws Exception {
        dao.save("AngleMapper.initMemberInfo",angleMemberLoginBO);
        dao.save("AngleMapper.initMemberBase",angleMemberLoginBO);
    }
    /**
     * 记录token信息
     * @param tokenBo
     */
    public void savaToken(TokenBO tokenBo) throws Exception {
        dao.save("AngleMapper.saveToken", tokenBo);
    }

    /**
     * 校验用户是否存在token
     * @param loginBo
     * @return
     */
    public TokenBO checkTokenExist(AngleMemberLoginBO loginBo) throws Exception {
        TokenBO tokenBO = (TokenBO) dao.findForObject("AngleMapper.checkTokenExist", loginBo);
        return tokenBO;
    }

    /**
     * 登录时更新token信息
     * @param token
     */
    public void updateTokenById(TokenBO token) throws Exception {
        dao.update("AngleMapper.updateTokenById", token);
    }

    /**
     * 校验token是否过期
     * @param tokenVo
     * @return
     */
    public TokenBO checkTokenOver(TokenVo tokenVo) throws Exception {
        TokenBO tokenBO = (TokenBO) dao.findForObject("AngleMapper.checkTokenOver", tokenVo);
        return tokenBO;
    }

    /**
     * 热门直播
     * @param page
     * @return
     * @throws Exception
     */
    public List<AngleLiveBO> findHotLive(AnglePageVO page) throws Exception {
        if(page.getIndex() == 0) {
            page.setIndex(1);
        }
        if(page.getPageSize() == 0) {
            page.setPageSize(20);
        }
        List<AngleLiveBO> list = (List<AngleLiveBO>) dao.findForList("AngleMapper.findHotLive", page);
        return list;
    }

    /**
     * 频道数据
     * @return
     */
    public List<AngleChannelBO> findChannelList() throws Exception {
        List<AngleChannelBO> list = (List<AngleChannelBO>) dao.findForList("AngleMapper.findChannelList", null);
        return list;
    }

    /**
     * 礼物列表
     * @param page
     * @return
     */
    public List<AngleGiftBO> findGiftList(AnglePageVO page) throws Exception {
        if(page.getIndex() == 0) {
            page.setIndex(1);
        }
        if(page.getPageSize() == 0) {
            page.setPageSize(8);
        }
        List<AngleGiftBO> list = (List<AngleGiftBO>) dao.findForList("AngleMapper.findGiftList", page);
        return list;
    }

    /**
     * 直播间人数
     * @param liveVO
     * @return
     */
    public int liveRoomNum(AngleLiveVO liveVO) throws Exception {
        Integer num = (Integer) dao.findForObject("AngleMapper.liveRoomNum", liveVO);
        return num;
    }

    /**
     * 直播间观众列表
     * @param liveVO
     * @return
     */
    public List<AngleMemberBO> roomMemberList(AngleLiveVO liveVO) throws Exception {
        List<AngleMemberBO> list = (List<AngleMemberBO>) dao.findForList("AngleMapper.roomMemberList", liveVO);
        return list;
    }

/**
 * 查询是否认证过
 */
public AuthenticationBO isAuthentication(AuthenticationBO authenticationBO) throws Exception {
    authenticationBO= (AuthenticationBO) dao.findForObject("AngleMapper.queryAuthentication",authenticationBO);
    return authenticationBO;





}
    /**
    <<<<<<< .mine
     * 直播用户认证
     * @param
     * 直播间观众列表
     * @param liveVO
    =======
     * 直播间观众列表
     * @param
     * @return
     */
    public void memberAuthentication(AuthenticationBO authenticationBO) throws Exception {
        dao.update("AngleMapper.saveAuthentication", authenticationBO);
    }

    public List<AngleMemberBO> saveOrUpdateDirectseeding (AngleLiveVO liveVO)throws Exception {
            List<AngleMemberBO> list = (List<AngleMemberBO>) dao.findForList("AngleMapper.roomMemberList", liveVO);
            return list;
    }
    public AngleLiveBO  queryDirectseeding(AngleCommonVO angleCommonVO) throws Exception {
        return  (AngleLiveBO) dao.findForObject("AngleMapper.queryDirectseeding", angleCommonVO);
    }

    public void  saveDirectseeding(ZxysDirectSeedingBo zxysDirectSeedingBo) throws Exception {
       dao.save("AngleMapper.saveDirectseeding", zxysDirectSeedingBo);
    }
    public void  updateDirectSeeding(AngleCommonVO angleCommonVO) throws Exception {
        dao.update("AngleMapper.updateDirectSeeding", angleCommonVO);
    }


    public List<AngleLiveBO>  queryLiveingList() throws Exception {
        return  (List<AngleLiveBO>)dao.findForList("AngleMapper.queryLiveingList", null);
    }

    public void  savePicture( AngleCommonVO angleCommonVO) throws Exception {
       dao.save("AngleMapper.savePicture", angleCommonVO);
    }

    public void  closeLiving( AngleCommonVO angleCommonVO) throws Exception {
        dao.save("AngleMapper.closeLiving", angleCommonVO);
    }

    public AngleMemberLoginBO  loginLiving(AngleCommonVO angleCommonVO) throws Exception {
        return (AngleMemberLoginBO)dao.findForObject("AngleMapper.loginLiving", angleCommonVO);
    }
}


