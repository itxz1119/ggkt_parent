package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.VideoVisitor;
import com.atguigu.ggkt.vo.vod.VideoVisitorCountVo;
import com.atguigu.ggkt.vod.mapper.VideoVisitorMapper;
import com.atguigu.ggkt.vod.service.VideoVisitorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 视频来访者记录表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-08-24
 */
@Service
public class VideoVisitorServiceImpl extends ServiceImpl<VideoVisitorMapper, VideoVisitor> implements VideoVisitorService {


    @Override
    public Map<String, Object> findCount(Long courseId, String startDate, String endDate) {
        //查出某个日期内,用户的数量
        List<VideoVisitorCountVo> countVoList = baseMapper.findCount(courseId,startDate,endDate);
        //创建两个集合,一个封装日期,一个封装用户数量
        List<String> dateList = countVoList.stream().map(VideoVisitorCountVo::getJoinTime)
                .collect(Collectors.toList());
        List<Integer> countList = countVoList.stream().map(VideoVisitorCountVo::getUserCount)
                .collect(Collectors.toList());
        //创建集合用来接收
        Map<String, Object> map = new HashMap<>();
        //放到map集合
        map.put("xData", dateList);
        map.put("yData", countList);
        return map;
    }
}
