//package com.fh.service.community.treatment;
//
///**
// * Created by Administrator on 2016/10/25 0025.
// */
//
//import com.fh.dao.DaoSupport;
//import com.fh.entity.bo.DoctorBo;
//import com.fh.entity.bo.UserInfoBO;
//import com.fh.entity.vo.*;
//import com.fh.entity.zxys.ZxysDoctorBO;
//import com.fh.entity.zxys.ZxysMessageVo;
//import com.fh.entity.zxys.ZxysOfficeBO;
//import com.fh.entity.zxys.*;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service("treatmentService")
//
//
//
//public class TreatmentService {
//    @Resource(name = "daoSupport")
//    private DaoSupport dao;
//
//    /**
//     * 校验token是否失效
//     */
//    public UserInfoBO checkTokenIsGone(QueryDoctorVo queryDoctorVo) throws Exception {
//        UserInfoBO userInfoBO= (UserInfoBO) dao.findForObject("Treatment.QueryMember",queryDoctorVo);
//        return userInfoBO;
//    }
//
//    /**
//     * @auth  李荣洲
//     * 通过did查询department
//     * @param queryDoctorVo
//     * @return
//     * @throws Exception
//     */
//public String queryDepartment(QueryDoctorVo queryDoctorVo) throws Exception {
//    if(queryDoctorVo.getDid()==0){
//        return null;
//    }else {
//        String department = (String) dao.findForObject("Treatment.QueryDepartment", queryDoctorVo);
//        return department;
//    }
//}
//    /**
//     * 分页查询doctor列表
//     * @param queryDoctorVo
//     * @return
//     * @throws Exception
//     */
//    public List<DoctorBo> queryDoctors(QueryDoctorVo queryDoctorVo) throws Exception {
//        if(queryDoctorVo.getPageIndex()!=0&&queryDoctorVo.getPageSize()!=0){
//          queryDoctorVo.setPageIndex((queryDoctorVo.getPageIndex()-1)*queryDoctorVo.getPageSize());
//        }
//        queryDoctorVo.setDepartment(this.queryDepartment(queryDoctorVo));
//        List<DoctorBo> list= (List<DoctorBo>) dao.findForList("Treatment.QueryDoctors", queryDoctorVo);
//        return list;
//    }
//
//
//    /**
//     * 查询doctor详情
//     * @param queryDoctorVo
//     * @return
//     * @throws Exception
//     */
//    public DoctorBo queryDoctorsById(QueryDoctorVo queryDoctorVo) throws Exception {
//        DoctorBo bo = (DoctorBo)dao.findForObject("Treatment.queryDoctorById", queryDoctorVo);
//        return bo;
//    }
//
//    /**
//     * 分页查询交流记录列表中的
//     * 校验token是否失效
//     */
//    public UserInfoBO checkTokenIsGone(QueryCommunicateVO queryCommunicateVO) throws Exception {
//        UserInfoBO userInfoBO= (UserInfoBO) dao.findForObject("Treatment.QueryMember",queryCommunicateVO);
//        return userInfoBO;
//    }
//
//    /**
//     * 分页查询交流记录列表
//     * @param queryCommunicateVO
//     * @return
//     * @throws Exception
//     */
//    public List<CommunicateRecordVo> queryCommunicate(QueryCommunicateVO queryCommunicateVO) throws Exception {
//        if(queryCommunicateVO.getPageIndex()!=0&&queryCommunicateVO.getPageSize()!=0){
//            queryCommunicateVO.setPageIndex((queryCommunicateVO.getPageIndex()-1)*queryCommunicateVO.getPageSize());
//        }
//
//        List<CommunicateRecordVo> list= (List<CommunicateRecordVo>) dao.findForList("Treatment.queryCommunicate", queryCommunicateVO);
//        return list;
//    }
//    /**
//     * 添加交流记录
//     */
//    public void addCommunicateRecord(CommunicateRecordVo communicateRecordVo) throws Exception {
//        dao.save("Treatment.addCommunicateRecord",communicateRecordVo);
//    }
//
//    public CommunicateRecordVo findCommunicateRecord(CommunicateRecordVo communicateRecordVo) throws Exception {
//       return (CommunicateRecordVo)dao.findForObject("Treatment.findCommunicateRecord", communicateRecordVo);
//    }
//
//
//
//
//    /**
//     * 评价等级和评价内容的
//     * 校验token是否失效
//     */
//    public UserInfoBO checkTokenIsGone(ModifyCommunicateVO modifyCommunicateVO) throws Exception {
//        UserInfoBO userInfoBO= (UserInfoBO) dao.findForObject("Treatment.QueryMember",modifyCommunicateVO);
//        return userInfoBO;
//    }
//    /**
//     * 评价等级和评价内容
//     */
//    public void modifyCommunicate(ModifyCommunicateVO modifyCommunicateVO)throws Exception {
//        dao.update("Treatment.modifyCommunicate", modifyCommunicateVO);
//    }
//    /**
//     * 分页查询获得客服电话等记录列表
//     * @param queryCommunicateVO
//     * @return
//     * @throws Exception
//     */
//    public List<QueryCommunicateVO> getPhone(QueryCommunicateVO queryCommunicateVO) throws Exception {
////        if(queryCommunicateVO.getPageIndex()!=0&&queryCommunicateVO.getPageSize()!=0){
////            queryCommunicateVO.setPageIndex((queryCommunicateVO.getPageIndex()-1)*queryCommunicateVO.getPageSize());
////        }
//        List<QueryCommunicateVO> list= (List<QueryCommunicateVO>) dao.findForList("Treatment.getPhone", queryCommunicateVO);
//        return list;
//    }
//    /**
//     * 存入支付宝返回信息
//     */
//    public void saveAlipayTransaction(AlipayTransactionVo alipayTransactionVo) throws Exception {
//        dao.save("Treatment.addAlipayTransaction", alipayTransactionVo);
//    }
//    /**
//     * 获取科室信息
//     */
//    public  List<DepartmentVo> queryDepartment()throws Exception {
//        List<DepartmentVo> list = (List<DepartmentVo>)dao.findForList("Treatment.queryDepartment",null);
//        return list;
//    }
//
//    /**
//     * 通过communicationid查记录
//     */
//    public CommunicateRecordVo findCommunicateById(String id) throws Exception {
//       return (CommunicateRecordVo)dao.findForObject("Treatment.findCommunicateRecordById",id);
//    }
//    /**
//     * 更新流水号
//     */
//    public void updateTrade_no(CommunicateRecordVo communicateRecordVo) throws Exception {
//        dao.update("Treatment.updateTrade_no",communicateRecordVo);
//    }
//
//    /**
//     * 保存支付数据
//     */
//    public void saveAlipayResult(AlipayResultVo alipayResultVo) throws Exception {
//        dao.save("Treatment.saveAlipayResult",alipayResultVo);
//    }
//    /**
//     * 删除订单
//     * （修改isdelete字段的状态）
//     */
//    public void modifyIsDelete(QueryCommunicateVO queryCommunicateVO) throws Exception {
//        dao.update("Treatment.modifyIsDelete",queryCommunicateVO);
//    }
//    /**
//     * 通过订单查询是否支付成功
//     */
//    public boolean  checkCommunicateById(AlipayResultVo alipayResultVo) throws Exception {
////        boolean result=false;
//        CommunicateRecordVo communicateRecordVo = (CommunicateRecordVo) dao.findForObject("Treatment.queryPayRecord", alipayResultVo);
//        if (communicateRecordVo != null) {
//            return true;
//        } else {
//            return false;
//        }
//
//    }
//
////    /**
////     * 支付成功之后的订单详情
////     */
////    public UserInfoBO checkToken(AlipayTransactionVo alipayTransactionVo) throws Exception {
////        UserInfoBO userInfoBO= (UserInfoBO) dao.findForObject("Treatment.QueryMember",alipayTransactionVo);
////        return userInfoBO;
////    }
//
//
//    /********************************************中孝医生***********************************************************************************/
//
//
//    public UserInfoBO checkToken(String token)throws Exception{
//        return (UserInfoBO) dao.findForObject("Treatment.checkToken",token);
//    }
//
//
//    /**
//     * 查询该科室下，热度最高的4位医生
//     * @param officeVO
//     * @return
//     * @throws Exception
//     */
//    public List<ZxysDoctorBO> queryDoctorsTopFourOrdered(ZxysOfficeVO officeVO) throws Exception {
//
//        List<ZxysDoctorBO> list = (List<ZxysDoctorBO>) dao.findForList("Treatment.queryDoctorsTopFourOrdered", officeVO);
//        return list;
//    }
//
//    /**
//     * 通过officeid查询科室
//     * @param officeVO
//     * @return
//     * @throws Exception
//     */
//    public Object queryOfficeByName(ZxysOfficeVO officeVO) throws Exception {
//
//        return dao.findForObject("Treatment.queryOfficeById",officeVO);
//    }
//
//    /**
//     * 查询该科室下的医生
//     * @param officeVO
//     * @return
//     * @throws Exception
//     */
//    public List<ZxysDoctorBO> queryDoctorsOrdered(ZxysOfficeVO officeVO) throws Exception {
//
//        List<ZxysDoctorBO> list = (List<ZxysDoctorBO>) dao.findForList("Treatment.queryDoctorsOrdered", officeVO);
//
//        return list;
//    }
//
//
//    /**
//     *
//     * 不区分科室，查询所有医生，按照接诊量排序，取前四
//     * @return
//     * @throws Exception
//     */
//    public List<ZxysDoctorBO> queryAllDoctorsTopFourOrdered() throws Exception {
//
//        List<ZxysDoctorBO> list = (List<ZxysDoctorBO>) dao.findForList("Treatment.queryAllDoctorsTopFourOrdered", null);
//
//        return list;
//    }
//
//    /**
//     * 不区分科室，查询所有医生，按照接诊量排序
//     * @return
//     * @throws Exception
//     */
//    public List<ZxysDoctorBO> queryAllDoctorsOrdered() throws Exception {
//        List<ZxysDoctorBO> list = (List<ZxysDoctorBO>) dao.findForList("Treatment.queryAllDoctorsOrdered", null);
//
//        return list;
//    }
//
//    /**
//     * 科室列表
//     * @return
//     * @throws Exception
//     */
//    public List<ZxysOfficeBO> queryAllOffice() throws Exception {
//
//        List<ZxysOfficeBO> list = (List<ZxysOfficeBO>) dao.findForList("Treatment.queryAllOffice", null);
//        return list;
//    }
//
//    /**
//     * 获取对话列表消息
//     * @return
//     */
//    public List<ZxysMessageVo> queryZxysMessage(ZxysMessageVo zxysMessageVo) throws Exception {
//        List<ZxysMessageVo> list = (List<ZxysMessageVo>) dao.findForList("Treatment.queryZxysMessage", zxysMessageVo);
//        return list;
//    }
//
//
//    /**
//     * 分页获取医生信息
//     * @param pageVO
//     * @return
//     */
//    public List<ZxysDoctorBO> queryDoctorsOrderedByPage(ZxysDoctorPageVO pageVO) throws Exception {
//
//        List<ZxysDoctorBO> list = (List<ZxysDoctorBO>) dao.findForList("Treatment.queryDoctorsOrderedByPage", pageVO);
//
//        return list;
//    }
//
//    /**
//     * 获取数据的总条数
//     * @param pageVO
//     * @return
//     */
//    public int queryDoctorsCount(ZxysDoctorPageVO pageVO) throws Exception {
//
//        return (Integer) dao.findForObject("Treatment.queryDoctorsCount", pageVO);
//    }
//
//    /**
//     * 验证token
//     * @param tokenVo
//     * @return
//     * @throws Exception
//     */
//    public Object queryMemberByToken(TokenVo tokenVo) throws Exception {
//        return dao.findForObject("Treatment.queryMemberByToken",tokenVo);
//    }
//
//    /**
//     * 根据接诊来量来查询科室列表
//     * @return
//     * @throws Exception
//     */
//    public List<ZxysOfficeBO> queryAllOfficeOrderRecptionNum() throws Exception {
//        List<ZxysOfficeBO> list = (List<ZxysOfficeBO>) dao.findForList("Treatment.queryAllOfficeOrderRecptionNum", null);
//        return list;
//    }
//
//
//    /**
//     * @methodName 预约医生获取医生评价
//     * @Author 刘洋
//     * @param doctorId
//     * @return recordBOList
//     * @throws
//     * @Date: 2016-12-01
//     * @Time: 11:10
//     */
//    public List<ZxysCommentContentBO> queryCommentContent(Integer doctorId)throws Exception {
//        List<ZxysCommentContentBO> commentContentList = new ArrayList<ZxysCommentContentBO>();
//        // 获取查看医生的评论内容
//        commentContentList = (List<ZxysCommentContentBO>) dao.findForList("Treatment.queryCommentContent",doctorId);
//        if(commentContentList!=null && commentContentList.size()>0){
//            for(int i = 0; i < commentContentList.size(); i++){
//                // 评价内容为空，不做处理
//                if(commentContentList.get(i).getComment_content() != null || !"".equals(commentContentList.get(i).getComment_content())
//                        || commentContentList.get(i).getLabel() != null || !"".equals(commentContentList.get(i).getLabel())
//                        || commentContentList.get(i).getStart_level() != null || !"".equals(commentContentList.get(i).getStart_level())) {
//                    String createTime = commentContentList.get(i).getCreate_time();
//                    createTime = StringUtils.substringBefore(createTime," ");
//                    commentContentList.get(i).setCreate_time(createTime);
//
//                    String label = commentContentList.get(i).getLabel();
//                    if(label != null && !"".equals(label) && !"(null)".equals(label)) {
//                        String str[] = label.split(",");
//                        List<ZxysVariableBO> variableBoList = new ArrayList<ZxysVariableBO>();
//                        for(int k = 0; k < str.length; k++){
//                            ZxysVariableBO variableBO = new ZxysVariableBO();
//                            Integer id = Integer.parseInt(str[k]);
//                            // 获取评论内容中的评论标签
//                            variableBO = (ZxysVariableBO) dao.findForObject("Treatment.queryVariableValue",id);
//                            variableBoList.add(variableBO);
//                        }
//                        commentContentList.get(i).setVariableBOList(variableBoList);
//                    }
//                }
//            }
//        }
//        return commentContentList;
//    }
//
//    /**
//     * @methodName 预约医生获取全部医生评价
//     * @Author 刘洋
//     * @param
//     * @return
//     * @throws
//     * @Date: 2016-12-01
//     * @Time: 14:20
//     */
//    public List<ZxysCommentContentBO> queryAllComment(ZxysCommentContentVO zxysCommentContentVO)throws Exception {
//        Integer index = zxysCommentContentVO.getPageIndex();
//        Integer pageSize = zxysCommentContentVO.getPageSize();
//        if(index!=null && pageSize != null){
//            zxysCommentContentVO.setPageIndex((index - 1) * zxysCommentContentVO.getPageSize());
//        }
//
//        List<ZxysCommentContentBO> commentContentBOList = new ArrayList<ZxysCommentContentBO>();
//        commentContentBOList = (List<ZxysCommentContentBO>) dao.findForList("Treatment.queryAllComment",zxysCommentContentVO);
//        if(commentContentBOList!=null && commentContentBOList.size()>0){
//            for(int i = 0; i < commentContentBOList.size(); i++){
//                // 评价内容为空，不做处理
//                if(commentContentBOList.get(i).getComment_content() != null || !"".equals(commentContentBOList.get(i).getComment_content())
//                        || commentContentBOList.get(i).getLabel() != null || !"".equals(commentContentBOList.get(i).getLabel())
//                        || commentContentBOList.get(i).getStart_level() != null || !"".equals(commentContentBOList.get(i).getStart_level())) {
//                    String createTime = commentContentBOList.get(i).getCreate_time();
//                    createTime = StringUtils.substringBefore(createTime," ");
//                    commentContentBOList.get(i).setCreate_time(createTime);
//
//                    String label = commentContentBOList.get(i).getLabel();
//                    if(label == null || "".equals(label) || "(null)".equals(label)) {
//                        continue;
//                    }
//                    String str[] = label.split(",");
//                    List<ZxysVariableBO> variableBoList = new ArrayList<ZxysVariableBO>();
//                    for(int k = 0; k < str.length; k++){
//                        ZxysVariableBO variableBO = new ZxysVariableBO();
//                        Integer id = Integer.parseInt(str[k]);
//                        // 获取评论内容中的评论标签
//                        variableBO = (ZxysVariableBO) dao.findForObject("Treatment.queryVariableValue",id);
//                        variableBoList.add(variableBO);
//                    }
//                    commentContentBOList.get(i).setVariableBOList(variableBoList);
//                }
//
//            }
//        }
//
//        return commentContentBOList;
//    }
//
//    /**
//     * @methodName 预约医生获取医生评价总数
//     * @Author 刘洋
//     * @param
//     * @return
//     * @throws
//     * @Date: 2016-12-01
//     * @Time: 13:34
//     */
//    public Integer queryCommentSum(Integer doctorId)throws Exception{
//        List<ZxysCommentContentBO> baseList = (List<ZxysCommentContentBO>) dao.findForList("Treatment.queryCommentSum",doctorId);
//        List<ZxysCommentContentBO> list = new ArrayList<ZxysCommentContentBO>();
//        if(baseList!= null && baseList.size() > 0) {
//            // 评价内容为空，不做处理
//            for (ZxysCommentContentBO bo: baseList){
//                if(bo.getComment_content() != null || !"".equals(bo.getComment_content())
//                        || bo.getLabel() != null || !"".equals(bo.getLabel())
//                        || bo.getStart_level() != null || !"".equals(bo.getStart_level())) {
//                    list.add(bo);
//                }
//            }
//        }
//
//        if (list == null){
//            return 0;
//        }
//
//        return list.size();
//    }
//
//    /**
//     * 搜索医生
//     * @param searchVO
//     * @return
//     * @throws Exception
//     */
//    public List<ZxysDoctorBO> searchDoctors(ZxysSearchVO searchVO) throws Exception {
//        List<ZxysDoctorBO> list = (List<ZxysDoctorBO>) dao.findForList("Treatment.searchDoctors", searchVO);
//       for (ZxysDoctorBO doctorBO : list){
//           if(doctorBO != null) {
//               if(doctorBO.getAdept() == null) {
//                   doctorBO.setAdept("");
//               }
//               if(doctorBO.getHospital_level() == null) {
//                   doctorBO.setHospital_level("");
//               }
//           }
//       }
//
//        return list;
//    }
//
//    /**
//     * 根据doctor_id查询医生
//     * @param doctorVO
//     * @return
//     */
//    public ZxysDoctorBO queryDoctorById(ZxysDoctorVO doctorVO) throws Exception {
//
//        ZxysDoctorBO doctorBO = (ZxysDoctorBO) dao.findForObject("Treatment.queryDoctorByIdNew", doctorVO);
//        if(doctorBO.getAdept() == null || "".equals(doctorBO.getAdept())) {
//            doctorBO.setAdept("");
//        }
//        return doctorBO;
//    }
//
//
//    /**
//     * 更新对话咨询状态（是否结束）
//     * @return
//     * @throws Exception
//     */
//    public void updateIsEnd( ZxysMessageVo zxysMessageVo) throws Exception {
//        dao.update("Treatment.updateIsEnd", zxysMessageVo);
//    }
//
//    /**
//     * 根据医院名称和地区查询医院
//     * @param hopitalVO
//     * @return
//     * @throws Exception
//     */
//    public ZxysHospitalBO queryHospitalLevel(ZxysHospitalVO hopitalVO) throws Exception {
//
//        List<ZxysHospitalBO> list = (List<ZxysHospitalBO>) dao.findForList("Treatment.queryHospitalLevelByNameAndArea", hopitalVO);
//        if(list != null && list.size() > 0) {
//            return list.get(0);
//        }
//        return null;
//    }
//
//
//    /**
//     * 设置用户消息是否公开
//     * @param messageVo
//     */
//    public void setIsOpenForMessage(ZxysMessageVo messageVo) throws Exception {
//        dao.update("Treatment.setIsOpenForMessage", messageVo);
//    }
//
//    /**
//     * 保存评价记录
//     * @param commentContentVO
//     */
//    public void saveCommentRecord(ZxysCommentContentVO commentContentVO) throws Exception {
//
//        dao.update("Treatment.saveCommentRecord", commentContentVO);
//    }
//
//    /**
//     * 设置对话是否结束
//     * @param messageVo
//     */
//    public void setIsCloseForMessage(ZxysMessageVo messageVo) throws Exception {
//
//        dao.update("Treatment.setIsCloseForMessage", messageVo);
//
//    }
//
//    /**
//     * 通过session_id查询医生的id
//     * @param messageVo
//     * @return
//     */
//    public ZxysRecordBO queryZxysRecordBySessionId(ZxysMessageVo messageVo) throws Exception {
//
//        ZxysRecordBO recordBO = (ZxysRecordBO) dao.findForObject("Treatment.queryZxysRecordBySessionId", messageVo);
//        return recordBO;
//    }
//
//    /**
//     * 获取所有评论标签
//     * @return
//     */
//    public List<ZxysVariableBO> queryVariable(int type) throws Exception {
//
//        List<ZxysVariableBO> list = (List<ZxysVariableBO>) dao.findForList("Treatment.queryVariable", type);
//        return list;
//    }
//
//    /**
//     * 免费咨询订单生成
//     * @param recordVO
//     */
//    public void saveFreeConsultRecord(ZxysRecordVO recordVO) throws Exception {
//        dao.save("Treatment.saveFreeConsultRecord", recordVO);
//    }
//
//    /**
//     * 添加新的就诊人
//     * @param userVO
//     */
//    public void addNewFriend(ZxysUserVO userVO) throws Exception {
//        dao.save("Treatment.addNewFriend", userVO);
//    }
//
//    /**
//     *
//     * 通过用户编号查询用户的好友列表
//     * @param userVO
//     * @return
//     */
//    public List<ZxysUserBO> queryFriendListByUserId(ZxysUserVO userVO) throws Exception {
//
//        List<ZxysUserBO> list = (List<ZxysUserBO>) dao.findForList("Treatment.queryFriendListByUserId", userVO);
//
//        return list;
//    }
//
//    /**
//     * 通过永恒的id查询用户信息
//     * @param userId
//     * @return
//     * @throws Exception
//     */
//    public ZxysUserBO queryMemberById(int userId) throws Exception {
//        ZxysUserBO userBO = (ZxysUserBO) dao.findForObject("Treatment.queryMemberById", userId);
//        return userBO;
//    }
//
//    /**
//     *通过好友姓名删除好友
//     * @param userVO
//     */
//    public void deleteFriendByName(ZxysUserVO userVO) throws Exception {
//        dao.delete("Treatment.deleteFriendByName", userVO);
//    }
//
//    /**
//     * 获取患者的订单列表
//     * @param recordVO
//     * @return
//     */
//    public List<ZxysRecordBO> queryConsultRecordForPatient(ZxysRecordVO recordVO) throws Exception {
//        List<ZxysRecordBO> list = (List<ZxysRecordBO>) dao.findForList("Treatment.queryConsultRecordForPatient", recordVO);
//
//        return list;
//    }
//
//    /**
//     * 通过token查询用户信息
//     * @param tokenVo
//     * @return
//     */
//    public UserInfoBO findMemberByToken(TokenVo tokenVo) throws Exception {
//        UserInfoBO userInfoBO = (UserInfoBO) dao.findForObject("Treatment.findMemberByToken", tokenVo);
//        return userInfoBO;
//    }
//
//    /**
//     * 通过订单号查询订单
//     * @param recordVO
//     * @return
//     */
//    public ZxysRecordBO queryRecordByOutTradeNo(ZxysRecordVO recordVO) throws Exception {
//        ZxysRecordBO recordBO = (ZxysRecordBO) dao.findForObject("Treatment.queryRecordByOutTradeNo", recordVO);
//        return recordBO;
//    }
//
//    /**
//     * 通过用户id查询用户信息
//     *
//     * @param userInfoBO
//     * @return
//     */
//    public UserInfoBO findMemberByMemberId(UserInfoBO userInfoBO) throws Exception {
//
//        return (UserInfoBO) dao.findForObject("Treatment.findMemberByMemberId", userInfoBO);
//    }
//
//    /**
//     *
//     * 保存订单信息
//     * @param recordVO
//     */
//    public void saveZxysRecord(ZxysRecordVO recordVO) throws Exception {
//        dao.save("Treatment.saveZxysRecord", recordVO);
//    }
//
//    /**
//     * 更新订单的支付宝交易流水号
//     * @param zxysRecordBO
//     */
//    public void updateTradeNo(ZxysRecordBO zxysRecordBO) throws Exception {
//        dao.update("Treatment.updateTradeNo", zxysRecordBO);
//    }
//
//    /**
//     * 异步校验支付结果
//     * @param recordVO
//     * @return
//     */
//    public boolean checkZxysRecordByOutTradeNo(ZxysRecordVO recordVO) throws Exception {
//
//        ZxysRecordBO recordBO = (ZxysRecordBO) dao.findForObject("Treatment.checkZxysRecordByOutTradeNo", recordVO);
//
//        if(recordBO == null) {
//            return false;
//        }else {
//            return true;
//        }
//    }
//
//    /**
//     * 查找当前数据库中最大的sessin_id
//     * @return
//     */
//    public int queryMaxSessionId() throws Exception {
//        Integer session_id = (Integer) dao.findForObject("Treatment.queryMaxSessionId", null);
//        return session_id;
//    }
//
//    /**
//     * 更新record中的session_id字段
//     * @param messageVo
//     * @throws Exception
//     */
//    public void updateSessionIdForRecord(ZxysMessageVo messageVo) throws Exception {
//        dao.update("Treatment.updateSessionIdForRecord", messageVo);
//    }
//
//    /**
//     * 保存消息
//     * @param messageVo
//     * @throws Exception
//     */
//    public boolean saveMessage(ZxysMessageVo messageVo) throws Exception {
//
//       try {
//           dao.save("Treatment.saveMessage", messageVo);
//           return true;
//       }catch (Exception e){
//           e.printStackTrace();
//           return false;
//       }
//    }
//
//    /**
//     * 医生认证表中的浏览次数更新
//     * @param doctorVO
//     */
//    public void updateBrowsingNum(ZxysDoctorVO doctorVO) throws Exception {
//        dao.update("Treatment.updateBrowsingNum", doctorVO);
//    }
//
//    /**
//     * 更新认证表中的receptin_num字段
//     * @param doctorVO
//     */
//    public void updateReceptionNum(ZxysDoctorVO doctorVO) throws Exception {
//        dao.update("Treatment.updateReceptionNum", doctorVO);
//    }
//
//    /**
//     * 微信支付回调业务
//     */
//    public void savePaymentInfo(PaymentWechat paymentWechat) throws Exception {
//        try {
//            dao.save("Treatment.addPaymentWechat",paymentWechat);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 通过订单号删除订单
//     * @param recordVO
//     * @return
//     */
//    public boolean deleteOrderByTradeNo(ZxysRecordVO recordVO) {
//        try {
//            dao.delete("Treatment.deleteOrderByTradeNo", recordVO);
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 添加订单
//     * @param recordBO
//     */
//    public void saveZxysRecordNew(ZxysRecordBO recordBO) throws Exception {
//
//        dao.save("Treatment.saveZxysRecordNew", recordBO);
//
//    }
//
//    /**
//     * 根据科室查找医生列表
//     * @param officeVO
//     * @return
//     */
//    public List<ZxysDoctorBO> queryDoctorsByOffice(ZxysOfficeVO officeVO) throws Exception {
//        List<ZxysDoctorBO> list = (List<ZxysDoctorBO>) dao.findForList("Treatment.queryDoctorsByOffice", officeVO);
//        return list;
//    }
//
//    /**
//     * 通过用户id查询积分
//     * @param userInfoBO
//     * @return
//     */
//    public UserInfoBO queryMemberInfoById(UserInfoBO userInfoBO) throws Exception {
//        UserInfoBO userInfo = (UserInfoBO) dao.findForObject("Treatment.queryMemberInfoById", userInfoBO);
//        return userInfo;
//    }
//
//    /**
//     * 更新用户的积分
//     * @param userInfo
//     * @throws Exception
//     */
//    public void updateMemberPointInfo(UserInfoBO userInfo) throws Exception {
//        dao.update("Treatment.updateMemberPointInfo", userInfo);
//    }
//}
