package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.NoticePO;
import generator.service.NoticeService;
import generator.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaofeng
* @description 针对表【t_notice(消息通知表)】的数据库操作Service实现
* @createDate 2024-03-08 20:14:46
*/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, NoticePO>
    implements NoticeService{

}




