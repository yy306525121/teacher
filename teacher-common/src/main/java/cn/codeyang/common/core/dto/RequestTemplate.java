package cn.codeyang.common.core.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class RequestTemplate<T> {
    private Page<T> page;
}
