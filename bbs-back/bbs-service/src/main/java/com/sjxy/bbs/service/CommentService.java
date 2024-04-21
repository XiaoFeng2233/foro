package com.sjxy.bbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjxy.bbs.entity.bo.CommentBO;
import com.sjxy.bbs.entity.dto.CommentPublishDTO;
import com.sjxy.bbs.entity.po.CommentPO;
import com.sjxy.bbs.entity.query.CommentQuery;

import java.util.List;

public interface CommentService {
    Long getAllCommentCount();
    Page<CommentPO> query(Integer pageNum, Integer pageSize, CommentQuery commentQuery);

    Page<CommentBO> queryUserCommentList(Integer pageNum, Integer pageSize);

    Page<CommentBO> queryUserCommentListByUserId(Integer pageNum, Integer pageSize, Long userId);

    Page<CommentBO> queryTopicComment(Integer pageNum, Integer pageSize, Long topicId);

    void publishComment(CommentPublishDTO record);

    void delete(Long id);

    CommentPO get(Long id);

    void deleteByUserId(Long userId);

    void deleteByTopicId(Long topicId);

}
