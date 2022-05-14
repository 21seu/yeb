package com.ftj.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ftj.server.constants.MailConstants;
import com.ftj.server.mapper.EmployeeMapper;
import com.ftj.server.mapper.MailLogMapper;
import com.ftj.server.pojo.Employee;
import com.ftj.server.pojo.MailLog;
import com.ftj.server.pojo.RespBean;
import com.ftj.server.pojo.RespPageBean;
import com.ftj.server.service.IEmployeeService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fengtj
 * @since 2021-08-28
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MailLogMapper mailLogMapper;

    /**
     * 获取所有员工(分页)
     *
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    @Override
    public RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> employeeByPage = employeeMapper.getEmployeeByPage(page, employee, beginDateScope);
        RespPageBean respPageBean = new RespPageBean(employeeByPage.getTotal(), employeeByPage.getRecords());
        return respPageBean;
    }

    @Override
    public RespBean maxWorkID() {
//        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workID)"));
//        return RespBean.success(String.format("%08d", Integer.parseInt(maps.get(0).get("max(workID)").toString()) + 1));
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workID)"));
        return RespBean.success(null, String.format("%08d", Integer.parseInt(maps.get(0).get("max(workID)").toString()) + 1));
    }

    @Override
    public RespBean addEmp(Employee employee) {
        //处理合同期限，保留2位小数
        LocalDate endContract = employee.getEndContract();
        LocalDate beginContract = employee.getBeginContract();
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days / 365.00)));
        if (1 == employeeMapper.insert(employee)) {

            Employee emp = employeeMapper.getEmployee(employee.getId()).get(0);
            //数据库记录发送的消息
            String msgId = UUID.randomUUID().toString();
            MailLog mailLog = MailLog.builder()
                    .msgId(msgId)
                    .eid(employee.getId())
                    .status(0)
                    .routeKey(MailConstants.MAIL_ROUTING_KEY_NAME)
                    .exchange(MailConstants.MAIL_EXCHANGE_NAME)
                    .count(0)
                    .tryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT))
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            //消息落库
            mailLogMapper.insert(mailLog);
            //发送信息
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,
                    MailConstants.MAIL_ROUTING_KEY_NAME,
                    emp,
                    new CorrelationData(msgId));
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    /**
     * 查询员工
     *
     * @param id
     * @return
     */
    @Override
    public List<Employee> getEmployee(Integer id) {
        return employeeMapper.getEmployee(id);
    }
}
