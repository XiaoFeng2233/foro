package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.UserFollowRelatePO;
import generator.service.UserFollowRelateService;
import generator.mapper.UserFollowRelateMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaofeng
* @description 针对表【t_user_follow_relate(关注表)】的数据库操作Service实现
* @createDate 2024-03-07 21:37:51
*/
@Service
public class UserFollowRelateServiceImpl extends ServiceImpl<UserFollowRelateMapper, UserFollowRelatePO>
    implements UserFollowRelateService{

}




