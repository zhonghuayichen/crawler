package com.crawlermanage.service.searchengine;

import com.crawler.domain.json.Result;
import com.crawler.se.sougou.domin.json.SearchEngineSouGou;
import com.crawler.se.sougou.htmlparser.SouGouSearchParser;
import com.crawler.storm.def.FunctionCallParam;
import com.crawler.storm.def.FunctionDefine;
import com.crawler.storm.def.WebParam;
import com.crawlermanage.service.aspect.CrawlerEngine;
import com.google.gson.Gson;
import com.module.domain.WebPageResponse;
import com.module.htmlunit.definition.UtilDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zxz on 2016/1/20.
 */
@Component("sESouGouService")
public class SESouGouService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SESouGouService.class);

    @Autowired
    private CrawlerEngine crawlerEngine;

    @Autowired
    private SouGouSearchParser souGouSearchParser;

    @Cacheable(value="dataCache", key="'SESouGouService.getListSearchResult' + #url + '&isDebug=' + #isDebug", unless="#result==null || #isDebug==true || #result.data==null")
    public Result<List<SearchEngineSouGou>> getListSearchResult(String url, boolean isDebug, String logback) {
        LOGGER.info("getListSearchResult url:{}", url);

        List<SearchEngineSouGou> searchEngineSouGous = null;
        Result<List<SearchEngineSouGou>> result = new Result<List<SearchEngineSouGou>>();

        FunctionCallParam fcm = new FunctionCallParam();
        fcm.setFunction(FunctionDefine.CRAWLERENGINE);
        WebParam wep = new WebParam();
        wep.setLogback(logback);
        wep.setUrl(url);
        wep.setUnit(UtilDefinition.JSOUP);
        fcm.setWebEngineParam(wep);

        String param = fcm.toJson();
        LOGGER.info("getArticles param:{}", param);

        result = crawlerEngine.execute(param, result);

        Gson gson = new Gson();
        WebPageResponse wpr = gson.fromJson(result.getHtml(), WebPageResponse.class);

        searchEngineSouGous = souGouSearchParser.searchListParser(wpr.getHtml());

        result.setData(searchEngineSouGous);
        result.debugMode(isDebug);

        return result;
    }
}
