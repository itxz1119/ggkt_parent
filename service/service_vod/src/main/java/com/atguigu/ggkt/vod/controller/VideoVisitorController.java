package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vod.service.VideoVisitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 视频来访者记录表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-08-24
 */
@Api(value = "VideoVisitor管理", tags = "VideoVisitor管理")
@RestController
@RequestMapping("/admin/vod/videoVisitor")
public class VideoVisitorController {
    @Autowired
    private VideoVisitorService videoVisitorService;

    /*
    * 根据时间 统计每个时间点的浏览数量
    * */
    @ApiOperation("显示统计数据")
    @GetMapping("findCount/{courseId}/{startDate}/{endDate}")
    public Result showChart(@PathVariable Long courseId,
                            @PathVariable String startDate,
                            @PathVariable String endDate){

        Map<String, Object> map = videoVisitorService.findCount(courseId,startDate,endDate);
        return Result.ok(map);
    }

}

