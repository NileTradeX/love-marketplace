package com.love.mq.service.goods;

import com.algolia.search.DefaultSearchClient;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.love.mq.model.AlgoliaIndexUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GoodsAlgoliaIndexService {

    private final Logger logger = LoggerFactory.getLogger(GoodsAlgoliaIndexService.class);

    @Value("${algolia.appId}")
    private String appId;

    @Value("${algolia.adminApiKey}")
    private String adminApiKey;

    public void algoliaIndexUpdate(AlgoliaIndexUpdateDTO dto) {
        logger.error("receive goods update: {}", dto.getId());
        try (SearchClient searchClient = DefaultSearchClient.create(appId, adminApiKey)) {
            SearchIndex<AlgoliaIndexUpdateDTO> index = searchClient.initIndex("goods_index", AlgoliaIndexUpdateDTO.class);
            index.saveObject(dto).waitTask();
        } catch (IOException ignored) {

        }
    }

    public void algoliaIndexDelete(String objectId) {
        logger.error("receive goods delete: {}", objectId);
        try (SearchClient searchClient = DefaultSearchClient.create(appId, adminApiKey)) {
            SearchIndex<AlgoliaIndexUpdateDTO> index = searchClient.initIndex("goods_index", AlgoliaIndexUpdateDTO.class);
            index.deleteObject(objectId);
        } catch (IOException ignored) {

        }
    }

}
