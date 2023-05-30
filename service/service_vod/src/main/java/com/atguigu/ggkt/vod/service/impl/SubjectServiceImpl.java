package com.atguigu.ggkt.vod.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.ggkt.exception.GgktException;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.vo.vod.SubjectEeVo;
import com.atguigu.ggkt.vod.listener.SubjectListener;
import com.atguigu.ggkt.vod.mapper.SubjectMapper;
import com.atguigu.ggkt.vod.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-08-18
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectListener subjectListener;

    @Override
    public List<Subject> selectList(Long id) {
        QueryWrapper<Subject> wrapper = new QueryWrapper();
        wrapper.eq("parent_id", id);
        List<Subject> subjects = baseMapper.selectList(wrapper);
        //hasChildren 默认值都是false,
        // 根据id 查询parent_id, 如果有值,说明有子级,设置为true
        for (Subject subject : subjects) {
            Long subjectId = subject.getId();
            boolean b = isChildren(subjectId);
            subject.setHasChildren(b);
        }
        return subjects;
    }

    private boolean isChildren(Long subjectId) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", subjectId);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }

    /**
     * 课程导出功能
     */
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("课程分类", "utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            List<Subject> subjects = baseMapper.selectList(null);
            List<SubjectEeVo> subjectEeVos = new ArrayList<>();
            for (Subject subject : subjects) {
                SubjectEeVo subjectEeVo = new SubjectEeVo();
//                subjectEeVo.setId(subject.getId());
//                subjectEeVo.setSort(subject.getSort());
//                subjectEeVo.setTitle(subject.getTitle());
//                subjectEeVo.setParentId(subject.getParentId());
                //使用工具类  找到相同属性名的属性进行复制
                BeanUtils.copyProperties(subject, subjectEeVo);
                subjectEeVos.add(subjectEeVo);
            }
            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class)
                    .sheet("课程分类")
                    .doWrite(subjectEeVos);
        } catch (Exception e) {
            throw new GgktException(200001, "导出失败");
        }
    }

    /*
     * 课程导入
     * */
    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), SubjectEeVo.class, subjectListener)
                    .sheet().doRead();
        } catch (Exception e) {
            throw new GgktException(20001, "导入失败!");
        }
    }
}
