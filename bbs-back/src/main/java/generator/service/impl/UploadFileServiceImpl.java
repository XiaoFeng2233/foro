package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.UploadFilePO;
import generator.service.UploadFileService;
import generator.mapper.UploadFileMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaofeng
* @description 针对表【t_upload_file(文件上传表)】的数据库操作Service实现
* @createDate 2024-02-22 22:40:56
*/
@Service
public class UploadFileServiceImpl extends ServiceImpl<UploadFileMapper, UploadFilePO>
    implements UploadFileService{

}




