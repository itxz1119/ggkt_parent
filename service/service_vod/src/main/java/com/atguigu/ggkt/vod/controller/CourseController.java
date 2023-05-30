package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.atguigu.ggkt.vod.service.CourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-08-22
 */
@Api(tags = "课程管理")
@RestController
@RequestMapping("/admin/vod/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /*
     * 课程查询
     * */
    @ApiOperation("条件查询分页")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit,
                        CourseQueryVo courseQueryVo) {
        Page<Course> pageParam = new Page<>(page, limit);
        Map<String, Object> map = courseService.findPage(pageParam, courseQueryVo);
        return Result.ok(map);
    }

    /*
     * 添加课程
     * @RequestBody:
     * 直接使用得到的是key=value&key=value&hellip;结构的数据，
     * 因此get方式不适用(get方式下@RequestBody获取不到任何数据)。
     * */
    @ApiOperation("课程添加")
    @PostMapping("save")
    public Result save(@RequestBody CourseFormVo courseFormVo) {
        Long courseId = courseService.saveCourseInfo(courseFormVo);
        return Result.ok(courseId);
    }

    /*
     * 根据id获取课程信息
     * */
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        CourseFormVo courseFormVo = courseService.getCourseInfoById(id);
        return Result.ok(courseFormVo);
    }

    /*
     * 修改课程
     * */
    @PostMapping("update")
    public Result update(@RequestBody CourseFormVo courseFormVo) {
        courseService.updateCourseInfo(courseFormVo);
        return Result.ok(courseFormVo.getId());
    }

    /*
     * 根据课程id查询发布课程的信息
     * */
    @ApiOperation("根据id获取课程发布信息")
    @GetMapping("getCoursePublishVo/{id}")
    public Result getCoursePublishVo(@PathVariable Long id) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVo(id);
        return Result.ok(coursePublishVo);
    }
    /*
     * 将状态改为1,代表正式发布
     * */
    @ApiOperation("发布课程")
    @PutMapping("publishCourseById/{id}")
    public Result publishCourseById(@PathVariable Long id) {
        courseService.publishCourseById(id);
        return Result.ok(null);
    }

    /*
    * 删除课程
    * */
    @ApiOperation("删除课程")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        courseService.removeByCourseId(id);
        return Result.ok(null);
    }
}

