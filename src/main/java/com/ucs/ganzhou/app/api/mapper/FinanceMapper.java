package com.ucs.ganzhou.app.api.mapper;
import  com.ucs.ganzhou.app.api.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Mapper
public interface FinanceMapper {
    Finance getRecommendProduct();
    OrderInfoResponseModel getOrderInfo(OrderInfoRequestModel orderInfoRequestModel);

    Float getInvestAmount(String customerId);
    Float getTotalIncome(String customerId);
    Float getPossessionAsset(String customerId);
    Float getFrozenAmount(String customerId);


}
