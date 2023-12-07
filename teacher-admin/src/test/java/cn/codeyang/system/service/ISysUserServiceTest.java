package cn.codeyang.system.service;

import cn.codeyang.common.core.domain.entity.SysUser;
import cn.codeyang.common.utils.ip.IpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * @author yangzy
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ISysUserServiceTest {
    @Autowired
    private ISysUserService sysUserService;


    @Test
    public void testUpdate() {
        SysUser user = new SysUser();
        user.setUserId(1L);
        user.setNickName("杨");
        user.setLoginIp(IpUtils.getIpAddr());
        user.setLoginDate(LocalDateTime.now());
        sysUserService.updateById(user);
    }
}