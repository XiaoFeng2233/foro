package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.TopicPO;
import generator.service.TopicService;
import generator.mapper.TopicMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaofeng
* @description 针对表【t_topic】的数据库操作Service实现
* @createDate 2024-02-23 16:24:40
*/
@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, TopicPO>
    implements TopicService{

}




