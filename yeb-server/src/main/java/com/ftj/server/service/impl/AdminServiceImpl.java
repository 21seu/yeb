package com.ftj.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ftj.server.config.security.JwtTokenUtil;
import com.ftj.server.mapper.AdminMapper;
import com.ftj.server.pojo.Admin;
import com.ftj.server.pojo.AdminLoginParam;
import com.ftj.server.pojo.RespBean;
import com.ftj.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fengtj
 * @since 2021-08-28
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    /**
     * 登录之后返回token
     *
     * @param adminLoginParam
     * @param request
     * @return
     */
    @Override
    public RespBean login(AdminLoginParam adminLoginParam, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(adminLoginParam.getUsername());
        if (null == userDetails || passwordEncoder.matches(adminLoginParam.getPassword(), userDetails.getPassword())) {
            return RespBean.error("用户名或密码不正确");
        }
        if (!userDetails.isEnabled()) {
            return RespBean.error("账号被禁用，请联系管理员");
        }

        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHeader", tokenHeader);
        return RespBean.success("登录成功", tokenMap);
    }
}
