package com.atguigu.ggkt.vod.service;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-08-22
 */
public interface CourseService extends IService<Course> {

    Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    Long saveCourseInfo(CourseFormVo courseFormVo);
    /*
     * 根据id获取课程信息
     * */
    CourseFormVo getCourseInfoById(Long id);
    /*
     * 修改课程
     * */
    void updateCourseInfo(CourseFormVo courseFormVo);

    /*
     * 根据课程id查询发布课程的信息
     * */
    CoursePublishVo getCoursePublishVo(Long id);
    /*
     * 最终发布
     * */
    void publishCourseById(Long id);
    /*
     * 删除课程
     * */
    void removeByCourseId(Long id);
}
