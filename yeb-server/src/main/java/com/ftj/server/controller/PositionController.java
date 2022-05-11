package com.ftj.server.controller;


import com.ftj.server.pojo.Position;
import com.ftj.server.pojo.RespBean;
import com.ftj.server.service.IPositionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Autowired
    private IPositionService positionService;

    @ApiOperation(value = "获取职位信息")
    @GetMapping("/")
    public List<Position> getPositions() {
        return positionService.list();
    }

    @ApiOperation(value = "添加职位信息")
    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position) {
        position.setCreateDate(LocalDateTime.now());
        if (positionService.save(position)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "更新职位信息")
    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position) {
        if (positionService.updateById(position)) {
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "删除职位信息")
    @DeleteMapping("/{id}")
    public RespBean deletePosition(@PathVariable Integer id) {
        if (positionService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除职位信息")
    @RequestMapping("/batchDel")
    public RespBean deletePositionByIds(@RequestParam("ids") String ids) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < ids.split(",").length; i++) {
            list.add(Integer.parseInt(ids.split(",")[i]));
        }
        if (positionService.removeByIds(list)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
