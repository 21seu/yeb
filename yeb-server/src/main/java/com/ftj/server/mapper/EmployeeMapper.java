package com.ftj.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ftj.server.pojo.Employee;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author fengtj
 * @since 2021-08-28
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 获取所有员工(分页)
     *
     * @param page
     * @param employee
     * @param beginDateScope
     * @return
     */
    IPage<Employee> getEmployeeByPage(Page<Employee> page, @Param("employee") Employee employee, @Param("beginDateScope") LocalDate[] beginDateScope);

    /**
     * 查询员工
     *
     * @param id
     * @return
     */
    List<Employee> getEmployee(@Param("id") Integer id);

    /*
    获取所有员工账套
     */
    IPage<Employee> getEmployeeWithSalary(Page<Employee> page);
}
