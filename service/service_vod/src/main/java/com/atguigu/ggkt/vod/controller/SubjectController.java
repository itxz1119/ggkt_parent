package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vod.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-08-18
 */
@Api(tags = "分类列表")
@RestController
@RequestMapping("/admin/vod/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;


    @ApiOperation("查询一级分类")
    @GetMapping("getChildSubject/{id}")
    public Result getChildSubject(@PathVariable Long id){
        List<Subject> subject = subjectService.selectList(id);
        return Result.ok(subject);
    }
    /*
    * 课程导出
    * */
    @ApiOperation(value="课程导出")
    @GetMapping(value = "/exportData")
    public void exportData(HttpServletResponse response){
        subjectService.exportData(response);
    }

    /*
    * 课程导入
    * */
    @ApiOperation(value="课程导入")
    @PostMapping(value = "/importData")
    public Result importData(MultipartFile file){
        subjectService.importData(file);
        return Result.ok(null);
    }
}

