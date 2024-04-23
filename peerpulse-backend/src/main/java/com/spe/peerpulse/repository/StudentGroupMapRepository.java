package com.spe.peerpulse.repository;

import com.spe.peerpulse.entity.StudentGroupMap;
import com.spe.peerpulse.utils.StudentGroupMapId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentGroupMapRepository extends JpaRepository<StudentGroupMap, StudentGroupMapId> {

    Long deleteByStudent_IdAndGroup_ClassGroupId(Long studentId, Long groupId);
}