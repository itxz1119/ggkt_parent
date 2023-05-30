package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.exception.GgktException;
import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.TeacherQueryVo;
import com.atguigu.ggkt.vod.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-08-12
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping(value = "/admin/vod/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /*@ApiOperation("查询所有讲师")
    @GetMapping("/findAll")
    public List<Teacher> findAll(){
        return teacherService.list();
    }*/
    @ApiOperation("查询所有讲师")
    @GetMapping("/findAll")
    public Result<List<Teacher>> findAll() {
        List<Teacher> list = teacherService.list();
        return Result.ok(list);
    }

    /* @ApiOperation("逻辑删除讲师")
     @DeleteMapping("/remove/{id}")
     public boolean removeById(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable Long id){
         boolean b = teacherService.removeById(id);
         return b;
     }*/
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("/remove/{id}")
    public Result removeById(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable Long id) {
        boolean isSuccess = teacherService.removeById(id);
        if (isSuccess) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    /*
     * 条件查询分页
     * */

    @ApiOperation("条件查询分页")
    //@GetMapping("/findPage/{current}/{limit}")
    @PostMapping("/findPage/{current}/{limit}")
    public Result findPage(@PathVariable Long current,
                           @PathVariable Long limit,
                           @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {//@RequestBody表示前端参数以json格式提交,必须和postMapping一起使用;required = false表示数据可以为空
        Page<Teacher> teacherPage = new Page<>(current, limit);

        if (teacherQueryVo == null) {
            IPage<Teacher> pageModel = teacherService.page(teacherPage, null);
            return Result.ok(pageModel);
        } else {
            String name = teacherQueryVo.getName();
            Integer level = teacherQueryVo.getLevel();
            String joinDateBegin = teacherQueryVo.getJoinDateBegin();
            String joinDateEnd = teacherQueryVo.getJoinDateEnd();
            QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(name)) {
                wrapper.like("name", name);
            }
            if (!StringUtils.isEmpty(level)) {
                wrapper.eq("level", level);
            }
            if (!StringUtils.isEmpty(joinDateBegin)) {
                wrapper.ge("join_date", joinDateBegin);
            }
            if (!StringUtils.isEmpty(joinDateEnd)) {
                wrapper.le("join_date", joinDateEnd);
            }
            wrapper.orderByDesc("id");
            IPage<Teacher> pageModel = teacherService.page(teacherPage, wrapper);
            return Result.ok(pageModel);
        }
    }

    /*
    * 添加讲师
    * */
    @ApiOperation("添加讲师")
    @PostMapping("/saveTeacher")
    public Result saveTeacher(@RequestBody Teacher teacher){
        boolean isSuccess = teacherService.save(teacher);
        if (isSuccess){
            return Result.ok(null);
        }
        return Result.fail(null);
    }

    /*
    * 修改讲师----查询
        ggkt-1313373771.cos.ap-beijing.myqcloud.com
    * */
    @ApiOperation("根据id查询讲师")
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Long id){
        try {
            //int i = 10/0;
        }catch (Exception e){
            throw new GgktException(500, "除数不可以为零");
        }

        Teacher teacher = teacherService.getById(id);
        return Result.ok(teacher);
    }
    /*
    * 修改讲师------修改
    * */
    @ApiOperation("修改讲师")
    @PutMapping("/updateById")
    public Result updateById(@RequestBody Teacher teacher){
        boolean isSuccess = teacherService.updateById(teacher);
        if (isSuccess){
            return Result.ok(null);
        }
        return Result.fail(null);
    }

    /*
    * 批量删除
    * */
    @ApiOperation("批量删除")
    @DeleteMapping("/removeBatch")
    public Result removeBatch(@RequestBody List<Long> ids){
        boolean isSuccess = teacherService.removeByIds(ids);
        if (isSuccess){
            return Result.ok(null);
        }
        return Result.fail(null);
    }

}

