package com.sjxy.bbs.service;

import com.alibaba.fastjson2.JSONObject;
import com.sjxy.bbs.entity.bo.SearchTopicBO;
import com.sjxy.bbs.entity.result.SearchResult;

public interface MeiliSearchService {
    void addDocument(String indexName, String jsonObject);

    void deleteDocument(String indexName, String documentId);

    void updateDocument(String indexName, String jsonObject);

    Boolean indexExist(String indexName);

    void createIndex(String indexName, String key);

    SearchResult<SearchTopicBO> search(String indexName, String query, Integer pageNum, Integer pageSize);
}
