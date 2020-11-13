package com.atguigu.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.edu.entity.EduSubject;
import com.atguigu.edu.entity.ExcelSubject;
import com.atguigu.edu.mapper.EduSubjectMapper;
import com.atguigu.edu.service.EduSubjectService;
import com.atguigu.exception.EduException;
import com.atguigu.response.SubjectVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-06
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    /**
     * 导入课程分类信息
     */
    @Override
    public void uploadSubject(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        EasyExcel.read(inputStream, ExcelSubject.class,null).doReadAll();
    }

    /**
     * 根据id删除课程节点
     * @param id
     * @return
     */
    @Override
    public boolean deleteSubjectById(String id) {
        //删除节点的时候看该节点是否有子节点 不允许前端删除
        //构建一个查询条件 parent_id=id看其是否有节点
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        //如果这个节点下没有子节点
        if (count==0){
            int rows = baseMapper.deleteById(id);
            return rows>0;
        }else{
            throw new EduException(20001,"该节点存在子节点");
        }
    }

    /**
     * 添加以及分类
     * @param eduSubject
     * @return
     */
    @Override
    public boolean saveParentSubject(EduSubject eduSubject) {
        //需要接收两个参数  parentId title
        //要判断该节点在数据库中是否存在
        EduSubject subject = existSubject("0", eduSubject.getTitle());
        if (subject==null){
            subject = new EduSubject();
            subject.setParentId("0");
            subject.setTitle(eduSubject.getTitle());
            int insert = baseMapper.insert(subject);
            return insert>0;
        }
        return false;
    }

    /**
     * 添加二级分类
     * @param eduSubject
     * @return
     */
    @Override
    public boolean saveChildSubject(EduSubject eduSubject) {
        EduSubject existSubject = existSubject(eduSubject.getParentId(), eduSubject.getTitle());
        if (existSubject==null){
            existSubject=new EduSubject();
            existSubject.setParentId(eduSubject.getParentId());
            existSubject.setTitle(eduSubject.getTitle());
            int insert = baseMapper.insert(existSubject);
            return insert>0;
        }
        return false;
    }

    /**
     * 查询所有课程信息
     * @return
     * @throws Exception
     */
    @Override
    public List<SubjectVo> getAllSubject() throws Exception {
        //a.设计一个返回对象给前端
        //b.查询数据库返回所有课程分类
        List<EduSubject> allSubjects = baseMapper.selectList(null);
        //c.先查询所有的一级分类(找组长)
        ArrayList<SubjectVo> parentSubjectVos = new ArrayList<>();
        for (EduSubject subject : allSubjects) {
            if (subject.getParentId().equals("0")){
                SubjectVo parentSubjectVo = new SubjectVo();
                BeanUtils.copyProperties(subject,parentSubjectVo);
                parentSubjectVos.add(parentSubjectVo);
            }
        }
        //d.把一级分类放到一个角落(map)
        HashMap<String, SubjectVo> parentSubjectMap = new HashMap<>();
        for (SubjectVo parentSubjectVo : parentSubjectVos) {
            parentSubjectMap.put(parentSubjectVo.getId(),parentSubjectVo);
        }
        //e.查询所有的二级分类(找组员)
        for (EduSubject subject : allSubjects) {
            //判断标准	parentId！=0
            if (!subject.getParentId().equals("0")){
                //拿到二级分类的parentId(组号) 从map中找一级分类
                SubjectVo subjectVo = parentSubjectMap.get(subject.getParentId());
                List<SubjectVo> children = subjectVo.getChildren();
                //一级分类里面有个children属性 把二级分类放到children里面 成为它的子节点
                SubjectVo childSubjectVo = new SubjectVo();
                //把对应属性值赋值给响应对象
                BeanUtils.copyProperties(subject,childSubjectVo);
                children.add(childSubjectVo);
            }
        }
        //f.返回所有的一级分类
        return parentSubjectVos;
    }

    /**
     * 判断title和parentId是否存在
     * @param title
     * @param parentId
     * @return
     */
    @Override
    public EduSubject existSubject(String title, String parentId) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",parentId);
        wrapper.eq("title",title);
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }
}
