package peaksoft.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.models.Department;

import java.util.List;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 17.02.2023
 */
@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {
//    List<Department> findAll(Long hospitalId);
//
//    void save(Department department);
//
//    Department findById(Long departmentId);
//
//    void update(Long departmentId,Department department);
//
//
//    void delete(Long id, Long hospitalId);



}
