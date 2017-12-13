package com.ucs.ganzhou.app.api.mapper;
import  com.ucs.ganzhou.app.api.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface FinanceMapper {
    Finance getRecommendProduct();
    OrderInfoResponseModel getOrderInfo(OrderInfoRequestModel orderInfoRequestModel);
    Map<String,String> getCustomerAssets(String customerId);
}
