//package com.fh.service.community.video;
//
///**
// * Created by Administrator on 2016/10/25 0025.
// */
//
//import com.fh.dao.DaoSupport;
//import com.fh.entity.bo.DoctorBo;
//import com.fh.entity.bo.UserInfoBO;
//import com.fh.entity.bo.VideoBO;
//import com.fh.entity.bo.VideoColumn;
//import com.fh.entity.vo.QueryDoctorVo;
//import com.fh.entity.vo.TokenVo;
//import com.fh.util.Tools;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@Service("videoService")
//
//public class VideoService {
//    @Resource(name = "daoSupport")
//    private DaoSupport dao;
//
//    /**
//     * 视频列表
//     */
//    public List videoIndex(VideoBO videoBO) throws Exception {
//        List<VideoBO> listVideo = new ArrayList<VideoBO>();
//
//        if(videoBO.getPageIndex()!=0&&videoBO.getPageSize()!=0){
//            videoBO.setPageIndex((videoBO.getPageIndex()-1)*videoBO.getPageSize());
//        }
//        List<VideoBO>  list =(List<VideoBO>) dao.findForList("Video.videoIndex", videoBO);
//          if(list !=null && list.size()>0){
//              for(int i=0;i<list.size();i++){
//                  VideoBO newBo =list.get(i);
//                  newBo.setVideo_url("http://shop.zxyl1688.com/video/"+newBo.getVideo_url());
//                  listVideo.add(newBo);
//              }
//          }
//        return listVideo;
//    }
//    //验证token
//    public UserInfoBO findToken(VideoBO videoBO) throws Exception {
//        return (UserInfoBO) dao.findForObject("Video.findToken",videoBO);
//    }
//
//    public VideoBO queryVideoById(VideoBO videoBO) throws Exception {
//        return (VideoBO) dao.findForObject("Video.queryVideoInfo",videoBO);
//    }
//    public Map<String,Object> queryVideoColumn() throws Exception {
//        Map<String,Object> map= Tools.errMessage();
//        List<VideoColumn> list= (List<VideoColumn>) dao.findForList("Video.queryVideoColumn",null);
//        if(list!=null && list.size()>0){
//            map.put("data",list);
//            map.put("code","0");
//            map.put("message","获取栏目名称列表成功");
//        }else{
//            map.put("data",null);
//            map.put("code","1");
//            map.put("message","获取栏目名称列表失败");
//        }
//        return map;
//    }
//}
