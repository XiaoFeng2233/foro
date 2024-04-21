package com.sjxy.bbs.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 文件上传表
 * @TableName t_upload_file
 */
@TableName(value ="t_upload_file")
@Data
public class UploadFilePO extends BasePO {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件ID
     */
    @TableField(value = "file_id")
    private String fileId;

    /**
     * 文件访问地址
     */
    @TableField(value = "url")
    private String url;

}