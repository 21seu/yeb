package com.ftj.server.controller;


import com.ftj.server.pojo.Joblevel;
import com.ftj.server.pojo.RespBean;
import com.ftj.server.service.IJoblevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fengtj
 * @since 2021-08-28
 */
@RestController
@RequestMapping("/sysytem/basic/joblevel")
public class JoblevelController {

    @Autowired
    private IJoblevelService iJoblevelService;

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/")
    public List<Joblevel> getAllJobLevels() {
        return iJoblevelService.list();
    }

    @ApiOperation(value = "添加职称")
    @PostMapping("/")
    public RespBean addAllJobLevel(@RequestBody Joblevel joblevel) {
        joblevel.setCreateDate(LocalDateTime.now());
        if (iJoblevelService.save(joblevel)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "更新职称")
    @PutMapping("/")
    public RespBean updateAllJobLevel(@RequestBody Joblevel joblevel) {
        if (iJoblevelService.updateById(joblevel)) {
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "删除职称")
    @DeleteMapping("/{id}")
    public RespBean deleteAllJobLevel(@PathVariable Integer id) {
        if (iJoblevelService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除职称")
    @DeleteMapping("/")
    public RespBean deleteAllJobLevelByIds(Integer[] ids) {
        if (iJoblevelService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
