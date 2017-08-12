//package com.fh.controller.community.video;
//
//import com.fh.entity.bo.DoctorBo;
//import com.fh.entity.bo.UserInfoBO;
//import com.fh.entity.bo.VideoBO;
//import com.fh.entity.vo.QueryDoctorVo;
//import com.fh.service.community.treatment.TreatmentService;
//import com.fh.service.community.video.VideoService;
//import com.fh.util.Tools;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by Administrator on 2016/10/25 0025.
// */
//@Controller
//@RequestMapping(value="/video")
//
//public class VideoController {
//    @Resource(name="videoService")
//    private VideoService videoService;
//    private String videoHeader="http://www.zxyl1688.com/video/";
//
//    /**
//     *
//     */
//    @RequestMapping(value = "index")
//    @ResponseBody
//    public Map<String,Object> videoIndex(@RequestBody VideoBO videoBO) throws Exception {
//        Map<String, Object> modelMap =Tools.errMessageSystem();
//        try {
////            UserInfoBO  tokneBO=videoService.findToken(videoBO);
////            if(tokneBO==null){
////                modelMap.put("code",2);
////                modelMap.put("data",null);
////                modelMap.put("message","token已失效");
////                return  modelMap ;
////            }else{
//                List<VideoBO> list=videoService.videoIndex(videoBO);
//                if(list!=null&&list.size()>0){
//                    modelMap.put("data",list);
//                    modelMap.put("message","获取数据成功");
//                    modelMap.put("code",0);
//                }else{
//                    modelMap.put("data",null);
//                    modelMap.put("message","暂无数据");
//                    modelMap.put("code",1);
//                }
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            modelMap=Tools.errMessageSystem();
//            return modelMap;
//        }
//        return modelMap;
//    }
//
//    /**
//     * 视频详情
//     * @auth 李荣洲
//     * @param videoBO
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "videoInfo")
//    @ResponseBody
//    public Map<String,Object> videoInfo(@RequestBody VideoBO videoBO) throws Exception {
//        Map<String, Object> modelMap =Tools.errMessageSystem();
//        try {
//            UserInfoBO  tokneBO=videoService.findToken(videoBO);
////            if(tokneBO==null){
////                modelMap.put("code",2);
////                modelMap.put("data",null);
////                modelMap.put("message","token已失效");
////                return  modelMap ;
////            }else{
//                VideoBO video=videoService.queryVideoById(videoBO);
//            if(video==null){
//                modelMap.put("data",null);
//                modelMap.put("message","暂无数据");
//                modelMap.put("code",1);
//                return modelMap;
//            }
//            video.setVideo_url(videoHeader+video.getVideo_url());
//            modelMap.put("data",video);
//            modelMap.put("message","获取数据成功");
//            modelMap.put("code",0);
//
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            modelMap=Tools.errMessageSystem();
//            return modelMap;
//        }
//        return modelMap;
//    }
//    /**
//     * 视频详情
//     * @auth 李荣洲
//     * @param videoBO
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "videoTitle")
//    @ResponseBody
//    public Map<String,Object> videoTitle(@RequestBody VideoBO videoBO) throws Exception {
//        Map<String, Object> modelMap =Tools.errMessageSystem();
//        try {
//            UserInfoBO  tokneBO=videoService.findToken(videoBO);
////            if(tokneBO==null){
////                modelMap.put("code",2);
////                modelMap.put("data",null);
////                modelMap.put("message","token已失效");
////                return  modelMap ;
////            }else{
//            VideoBO video=videoService.queryVideoById(videoBO);
//            if(video==null){
//                modelMap.put("data",null);
//                modelMap.put("message","暂无数据");
//                modelMap.put("code",1);
//                return modelMap;
//            }
//            video.setVideo_url(videoHeader+video.getVideo_url());
//            modelMap.put("data",video);
//            modelMap.put("message","获取数据成功");
//            modelMap.put("code",0);
//
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            modelMap=Tools.errMessageSystem();
//            return modelMap;
//        }
//        return modelMap;
//    }
//    /**
//     * 视频栏目名称
//     * @auth 李荣洲
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "videoColumn")
//    @ResponseBody
//    public Map<String,Object> videoColumn() throws Exception {
//        Map<String, Object> modelMap =Tools.errMessageSystem();
//        try {
//            modelMap=videoService.queryVideoColumn();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Tools.errMessageSystem();
//        }
//        return modelMap;
//    }
//
//}
