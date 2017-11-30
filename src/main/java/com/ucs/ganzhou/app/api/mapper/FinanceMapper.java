package com.ucs.ganzhou.app.api.mapper;
import  com.ucs.ganzhou.app.api.model.*;

import java.util.Map;

public interface FinanceMapper {
    public  Finance getRecommendProduct();
    public OrderInfoResponseModel getOrderInfo(OrderInfoRequestModel orderInfoRequestModel);
    public Map<String,String> getCustomerAssets(String customerId);
}
