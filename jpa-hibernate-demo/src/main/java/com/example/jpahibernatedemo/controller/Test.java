package com.example.jpahibernatedemo.controller;

import com.example.jpahibernatedemo.TreeVo;
import com.example.jpahibernatedemo.test.dao.MyClassRepository;
import com.example.jpahibernatedemo.test.dao.MyStudentRepository;
import com.example.jpahibernatedemo.test.dao.MyTeacherRepository;
import com.example.jpahibernatedemo.test.dao.MytreeRepository;
import com.example.jpahibernatedemo.test.entity.MyStudent;
import com.example.jpahibernatedemo.test.entity.Mytree;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
public class Test {

    @Resource(name = "myClassRepository", type = MyClassRepository.class)
    MyClassRepository myClassRepository;

    @Resource(name = "myStudentRepository", type = MyStudentRepository.class)
    MyStudentRepository myStudentRepository;

    @Resource(name = "myTeacherRepository", type = MyTeacherRepository.class)
    MyTeacherRepository myTeacherRepository;

    @Resource(name = "mytreeRepository", type = MytreeRepository.class)
    MytreeRepository mytreeRepository;



    @GetMapping("/getMytrees")
    public List<TreeVo> getMytrees() {
        List<com.example.jpahibernatedemo.test.entity.Mytree> mytrees = mytreeRepository.findAll();
        List<TreeVo> treeVos = mytrees.stream().map(
                mytree -> {
                    TreeVo treeVo = new TreeVo();
                    treeVo.setId(mytree.getId());
                    treeVo.setParentId(mytree.getParentId());
                    treeVo.setPortName(mytree.getPortName());
                    treeVo.setIndex(mytree.getIndex());
                    treeVo.setLink(mytree.getLink());
                    return treeVo;
                }
        ).collect(java.util.stream.Collectors.toList());
        Map<Long, TreeVo> idEntity = treeVos.stream().collect(Collectors.toMap(TreeVo::getId, treeVo -> treeVo));
        List<TreeVo> parents = treeVos.stream()
                .filter(treeVo -> treeVo.getParentId() == 0)
                .sorted(Comparator.comparingInt(TreeVo::getIndex))
                .collect(Collectors.toList());
        getTreeVos(idEntity, parents);
        return parents;
    }



    public void getTreeVos(Map<Long, TreeVo> idEntity, List<TreeVo> parents) {
        for (TreeVo vo : parents) {
            List<TreeVo> children = new ArrayList<>();
            for (TreeVo treeVo : idEntity.values()) {
                if (treeVo.getParentId().equals(vo.getId())) {
                    children.add(treeVo);
                }
            }
            vo.setChildren(children);
            getTreeVos(idEntity, children);
        }
    }


    @GetMapping("/addStudent")
    public void addStudent() {
        MyStudent myStudent = new MyStudent();
        myStudent.setName("张三");
        myStudent.setAge(1);
        myStudentRepository.save(myStudent);
    }

    @GetMapping("/findStudent")
    public void findStudent() {
        MyStudent myStudent = myStudentRepository.findById(1).orElse(new MyStudent());
        System.out.println(myStudent);
    }


    @GetMapping("/findStudentByPage")
    public void findStudentByPage() {
        Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
        List<MyStudent> myStudents = myStudentRepository.findByNameOrderByIdDesc("张三", firstPageWithTwoElements);
        System.out.println(myStudents.get(0));
    }
}
