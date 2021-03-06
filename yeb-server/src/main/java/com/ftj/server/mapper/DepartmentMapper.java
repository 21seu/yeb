package com.ftj.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ftj.server.pojo.Department;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fengtj
 * @since 2021-08-28
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> getAllDepartments(int i);

    void addDep(Department dep);

    void deleteDep(Department dep);

}
