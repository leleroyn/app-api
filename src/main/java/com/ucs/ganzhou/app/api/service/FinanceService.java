package com.ucs.ganzhou.app.api.service;


import com.ucs.ganzhou.app.api.model.Finance;
import com.ucs.ganzhou.app.api.mapper.FinanceMapper;
import com.ucs.ganzhou.app.api.model.OrderInfoRequestModel;
import com.ucs.ganzhou.app.api.model.OrderInfoResponseModel;
import com.ucs.ganzhou.app.api.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class FinanceService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private FinanceMapper  financeMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Value("${ucs.ganzhou.app.api.recommend-project-cache-key}")
    private  String recommendProjectCacheKey;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Logger logger = LoggerFactory.getLogger(FinanceService.class);
    public Finance getRecommendProduct() throws  Exception
    {
        Finance finance = null;
        String cacheRecommendProjectJson = null;
        try {
            cacheRecommendProjectJson = stringRedisTemplate.opsForValue().get(recommendProjectCacheKey);
        }catch (Exception ex){
            logger.error("redis 链接异常.");
        }
        if( cacheRecommendProjectJson != null && !cacheRecommendProjectJson.isEmpty() ) {
            Map<String, Object> cacheRecommendProjectMap = JSONUtils.json2map(cacheRecommendProjectJson);
            if(!cacheRecommendProjectMap.isEmpty()){
                ArrayList financeArr=(ArrayList)cacheRecommendProjectMap.get("FinanceList");
                Map<String,Object> financeMap = (Map<String,Object>)financeArr.get(0);
                logger.debug(String.format("从redis库中获取推荐项目成功:%s",cacheRecommendProjectJson));
                finance = new Finance();
                finance.setAmount(Float.parseFloat(financeMap.get("TotalAmount").toString()));
                finance.setBeginTime(simpleDateFormat.parse(financeMap.get("ProjectBeginTime").toString().replace("T"," ")));
                finance.setDuration(Integer.parseInt(financeMap.get("Duration").toString()));
                finance.setMinBuyAmount(Float.parseFloat(financeMap.get("MinAmount").toString()));
                finance.setProjectName(financeMap.get("ProjectName").toString());
                finance.setRate(Float.parseFloat(financeMap.get("Interest").toString())/100);
            }
        }
        else{
            finance = financeMapper.getRecommendProduct();
        }
        return  finance;
    }

    public OrderInfoResponseModel getOrderInfo(OrderInfoRequestModel orderInfoRequestModel)
    {
        return financeMapper.getOrderInfo(orderInfoRequestModel);
    }

    public Map<String,String> getCustomerAssets(String customerId){
        Map<String,String> resultMap = new LinkedHashMap<>();

        float totalAmount =financeMapper.getInvestAmount(customerId);
        resultMap.put("totalAmount",Float.toString(totalAmount));
        float totalIncome =financeMapper.getTotalIncome(customerId);
        resultMap.put("totalIncome",Float.toString(totalIncome));
        float possessionAsset =financeMapper.getPossessionAsset(customerId);
        resultMap.put("possessionAsset",Float.toString(possessionAsset));
        float frozenAmount =financeMapper.getFrozenAmount(customerId);
        resultMap.put("frozenAmount",Float.toString(frozenAmount));

        return resultMap;
    }
}
