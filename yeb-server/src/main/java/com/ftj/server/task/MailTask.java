package com.ftj.server.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ftj.server.constants.MailConstants;
import com.ftj.server.pojo.Employee;
import com.ftj.server.pojo.MailLog;
import com.ftj.server.service.IEmployeeService;
import com.ftj.server.service.IMailLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 邮件发送定时任务
 * Created by fengtj on 2022/5/15 0:22
 */
@Component
public class MailTask {

    @Autowired
    private IMailLogService iMailLogService;
    @Autowired
    private IEmployeeService iEmployeeService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 10秒执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask() {
        List<MailLog> list = iMailLogService.list(new QueryWrapper<MailLog>()
                .eq("status", 0)
                .lt("tryTime", LocalDateTime.now()));

        list.forEach(l -> {
            if (l.getCount() >= 3) {
                iMailLogService.update(new UpdateWrapper<MailLog>()
                        .set("status", 2)
                        .eq("msgId", l.getMsgId()));
            }
            iMailLogService.update(new UpdateWrapper<MailLog>()
                    .set("count", l.getCount() + 1)
                    .set("updateTime", LocalDateTime.now())
                    .set("tryTime", LocalDateTime.now()
                            .plusMinutes(MailConstants.MSG_TIMEOUT))
                    .eq("msgId", l.getMsgId()));

            Employee employee = iEmployeeService.getEmployee(l.getEid()).get(0);
            //重新发送消息
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME
                    , MailConstants.MAIL_ROUTING_KEY_NAME
                    , employee
                    , new CorrelationData(l.getMsgId()));
        });
    }
}
