package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.ForbiddenLogPO;
import generator.service.ForbiddenLogService;
import generator.mapper.ForbiddenLogMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaofeng
* @description 针对表【t_forbidden_log(封禁记录表)】的数据库操作Service实现
* @createDate 2024-03-06 14:46:29
*/
@Service
public class ForbiddenLogServiceImpl extends ServiceImpl<ForbiddenLogMapper, ForbiddenLogPO>
    implements ForbiddenLogService{

}




