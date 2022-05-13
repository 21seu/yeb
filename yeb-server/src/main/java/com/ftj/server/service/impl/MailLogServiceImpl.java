package com.ftj.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ftj.server.mapper.MailLogMapper;
import com.ftj.server.pojo.MailLog;
import com.ftj.server.service.IMailLogService;
import org.springframework.stereotype.Service;

/**
 * Created by fengtj on 2022/5/13 8:11
 */
@Service
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {

}
