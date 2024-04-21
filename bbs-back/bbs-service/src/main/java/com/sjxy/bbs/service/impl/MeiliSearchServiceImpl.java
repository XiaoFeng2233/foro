package com.sjxy.bbs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.SearchRequest;
import com.sjxy.bbs.entity.bo.SearchTopicBO;
import com.sjxy.bbs.entity.result.SearchResult;
import com.sjxy.bbs.service.MeiliSearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MeiliSearchServiceImpl implements MeiliSearchService {

    @Value("${meilisearch.endPoint}")
    private String endPoint;

    @Value("${meilisearch.masterKey}")
    private String masterKey;

    @Override
    public void addDocument(String indexName, String jsonObject) {
        Client client = new Client(new Config(endPoint, masterKey));
        Index index = client.getIndex(indexName);
        index.addDocuments(jsonObject);
    }

    @Override
    public void deleteDocument(String indexName, String documentId) {
        Client client = new Client(new Config(endPoint, masterKey));
        Index index = client.getIndex(indexName);
        index.deleteDocument(documentId);
    }

    @Override
    public void updateDocument(String indexName, String jsonObject) {
        Client client = new Client(new Config(endPoint, masterKey));
        Index index = client.getIndex(indexName);
        index.updateDocuments(jsonObject);
    }

    @Override
    public Boolean indexExist(String indexName) {
        Client client = new Client(new Config(endPoint, masterKey));
        client.getIndex(indexName);
        try {
            client.index(indexName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void createIndex(String indexName, String key) {
        Client client = new Client(new Config(endPoint, masterKey));
        client.createIndex(indexName, key);
        Index index = client.getIndex(indexName);
        String[] sortableAttr = {"publishTime"};
        index.updateSortableAttributesSettings(sortableAttr);
    }

    @Override
    public SearchResult<SearchTopicBO> search(String indexName, String query, Integer pageNum, Integer pageSize) {
        Client client = new Client(new Config(endPoint, masterKey));
        Index index = client.getIndex(indexName);
        SearchRequest searchRequest = new SearchRequest(query);
        //设置分页偏移
        searchRequest.setOffset((pageNum - 1) * pageSize);
        //设置分页页大小
        searchRequest.setLimit(pageSize);
        searchRequest.setHighlightPreTag("<mark>");
        searchRequest.setHighlightPostTag("</mark>");
        String[] highLightAttr = {"summary"};
        searchRequest.setAttributesToHighlight(highLightAttr);
        String[] cropAttr = {"summary"};
        searchRequest.setAttributesToCrop(cropAttr);
        searchRequest.setCropLength(10);
        String[] searchOnAttr = {"summary", "publisherUserNickName"};
        searchRequest.setAttributesToSearchOn(searchOnAttr);
        String[] sortAttr = {"publishTime:desc"};
        searchRequest.setSort(sortAttr);

        String jsonResult = index.rawSearch(searchRequest);
        JSONObject jsonObject = JSONObject.parseObject(jsonResult);
        Integer total = (Integer)jsonObject.get("estimatedTotalHits");
        JSONArray hits = jsonObject.getJSONArray("hits");

        if (CollUtil.isNotEmpty(hits)) {
            List<SearchTopicBO> list = hits.stream().map(hit -> {
                JSONObject hitObj = (JSONObject) hit;
                SearchTopicBO searchTopicBO = new SearchTopicBO();
                Integer id = (Integer) hitObj.get("id");
                searchTopicBO.setId(id.longValue());
                searchTopicBO.setTitle((String) hitObj.get("title"));
                String publishTimeStr = (String) hitObj.get("publishTime");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parseDate = null;
                try {
                    parseDate = simpleDateFormat.parse(publishTimeStr);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                searchTopicBO.setPublishTime(parseDate);
                Integer userId = (Integer) hitObj.get("publisherUserId");
                searchTopicBO.setPublisherUserId(userId.longValue());
                searchTopicBO.setPublisherUserNickName((String) hitObj.get("publisherUserNickName"));

                JSONObject formatted = hitObj.getJSONObject("_formatted");

                String formattedSummary = (String)formatted.get("summary");
                searchTopicBO.setFormattedSummary(formattedSummary);
                return searchTopicBO;
            }).toList();

            return SearchResult.ok(list, Long.valueOf(pageNum), Long.valueOf(pageSize), total.longValue());
        } else {
            return SearchResult.ok(null, Long.valueOf(pageNum), Long.valueOf(pageSize), total.longValue());
        }
    }
}
