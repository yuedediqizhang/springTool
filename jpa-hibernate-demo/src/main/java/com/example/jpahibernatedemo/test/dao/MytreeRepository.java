package com.example.jpahibernatedemo.test.dao;

import com.example.jpahibernatedemo.test.entity.Mytree;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MytreeRepository extends CrudRepository<Mytree, Long> {

    @Override
    List<Mytree> findAll();

    List<Mytree> findByParentId(Long parentId);

    @Query("select m from Mytree m where m.portName like :portName order by m.index asc")
    List<Mytree> findByPortNameLike(String portName);

    Mytree findById(long id);

}