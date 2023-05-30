package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.Chapter;
import com.atguigu.ggkt.model.vod.Video;
import com.atguigu.ggkt.vo.vod.ChapterVo;
import com.atguigu.ggkt.vo.vod.VideoVo;
import com.atguigu.ggkt.vod.mapper.ChapterMapper;
import com.atguigu.ggkt.vod.service.ChapterService;
import com.atguigu.ggkt.vod.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-08-22
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {


    @Autowired
    private VideoService videoService;

    /*
     * 查询章节列表
     * */
    @Override
    public List<ChapterVo> getTreeList(Long courseId) {
        List<ChapterVo> finalChapterVos = new ArrayList<>();

        //查询章节
        QueryWrapper<Chapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        List<Chapter> chapters = baseMapper.selectList(chapterWrapper);

        //查询所有小节
        LambdaQueryWrapper<Video> videoVoWrapper = new LambdaQueryWrapper<>();
        videoVoWrapper.eq(Video::getCourseId,courseId);
        List<Video> videos = videoService.list(videoVoWrapper);

        //将章节封装到finalChapterVos集合
        for (Chapter chapter : chapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            finalChapterVos.add(chapterVo);

            //查询章节下是否有小节
            ArrayList<VideoVo> videoVos = new ArrayList<>();
            for (Video video : videos) {
                if (chapter.getId().equals(video.getChapterId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);
        }
        return finalChapterVos;
    }

    /*
    * 删除课程之删除章节
    * */
    @Override
    public void removeByCourseId(Long id) {
        LambdaQueryWrapper<Chapter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Chapter::getCourseId,id);
        baseMapper.delete(wrapper);
    }
}
