package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.Video;
import com.atguigu.ggkt.vod.mapper.VideoMapper;
import com.atguigu.ggkt.vod.service.VideoService;
import com.atguigu.ggkt.vod.service.VodService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-08-22
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodService vodService;


    /*
    * 根据课程id删除小节和视频
    * */
    @Override
    public void removeByCourseId(Long id) {
        /*LambdaQueryWrapper<Video> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Video::getCourseId, id);
        baseMapper.delete(lambdaQueryWrapper);*/
        //根据id查询小节
        //查询出所有小节
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        List<Video> videoList = baseMapper.selectList(wrapper);
        for (Video video : videoList) {
            //获取小节里面的视频id
            String videoSourceId = video.getVideoSourceId();
            //判断是否为空
            if (!StringUtils.isEmpty(videoSourceId)) {
                //不为空调用删除视频的方法
                vodService.removeVideo(videoSourceId);
            }
        }
        //根据id删除小节,不可以先删小节,要先删视频
        baseMapper.delete(wrapper);
    }

    /*
     *根据小节id删除视频和小节
     * */
    @Override
    public void removeVideoById(Long id) {
        Video video = baseMapper.selectById(id);
        //获取小节里面的视频id
        String videoSourceId = video.getVideoSourceId();
        //判断是否为空
        if (!StringUtils.isEmpty(videoSourceId)) {
            //不为空调用删除视频的方法
            vodService.removeVideo(videoSourceId);
        }
        //根据id删除小节,不可以先删小节,要先删视频
        baseMapper.deleteById(id);
    }
}
