package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.CommentPO;
import generator.service.CommentService;
import generator.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaofeng
* @description 针对表【t_comment】的数据库操作Service实现
* @createDate 2024-03-06 23:52:40
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentPO>
    implements CommentService{

}




