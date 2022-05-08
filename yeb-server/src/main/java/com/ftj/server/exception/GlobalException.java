package com.ftj.server.exception;

import com.ftj.server.pojo.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 * Created by fengtj on 2022/5/8 14:25
 */
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(SQLException.class)
    public RespBean mysqlException(SQLException exception) {
        if (exception instanceof SQLIntegrityConstraintViolationException)
            return RespBean.error("该数据有关联数据，操作失败");

        return RespBean.error("数据库异常，操作失败");
    }
}
