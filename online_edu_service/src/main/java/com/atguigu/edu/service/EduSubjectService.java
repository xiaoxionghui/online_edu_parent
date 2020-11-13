package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduSubject;
import com.atguigu.response.SubjectVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author xiaoxionghui
 * @since 2020-11-06
 */
public interface EduSubjectService extends IService<EduSubject> {
    /**
     * 查询所有分类信息
     * @return
     * @throws Exception
     */
    List<SubjectVo> getAllSubject() throws Exception;

    /**
     * 判断条件与标题与parentId，title是否不同
     * @param s
     * @param parentSubjectName
     * @return
     */
    EduSubject existSubject(String s, String parentSubjectName);

    /**
     * 导入课程信息
     * @param file
     * @throws Exception
     */
    void uploadSubject(MultipartFile file) throws Exception;

    /**
     * 根据id删除课程节点
     * @param id
     * @return
     */
    boolean deleteSubjectById(String id);

    /**
     * 添加一级分类
     * @param eduSubject
     * @return
     */
    boolean saveParentSubject(EduSubject eduSubject);

    /**
     * 添加二级分类
     * @param eduSubject
     * @return
     */
    boolean saveChildSubject(EduSubject eduSubject);
}
