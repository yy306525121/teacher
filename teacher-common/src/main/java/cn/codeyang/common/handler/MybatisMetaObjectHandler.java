package cn.codeyang.common.handler;

import cn.codeyang.common.core.domain.model.LoginUser;
import cn.codeyang.common.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.security.Security;
import java.time.LocalDateTime;

/**
 * @author yangzy
 */
@Slf4j
@Component
public class MybatisMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        if (SecurityUtils.getAuthentication() != null && SecurityUtils.getAuthentication().getPrincipal() instanceof LoginUser) {
            this.strictInsertFill(metaObject, "createBy", String.class, SecurityUtils.getLoginUser().getUserId().toString());
            this.strictInsertFill(metaObject, "updateBy", String.class, SecurityUtils.getLoginUser().getUserId().toString());
        }
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        if (SecurityUtils.getAuthentication() != null && SecurityUtils.getAuthentication().getPrincipal() instanceof LoginUser) {
            this.strictUpdateFill(metaObject, "updateBy", String.class, SecurityUtils.getLoginUser().getUserId().toString());
        }
    }


}
