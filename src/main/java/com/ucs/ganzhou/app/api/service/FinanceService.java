package com.ucs.ganzhou.app.api.service;

import com.ucs.ganzhou.app.api.model.Finance;
import com.ucs.ganzhou.app.api.mapper.FinanceMapper;
import com.ucs.ganzhou.app.api.model.OrderInfoRequestModel;
import com.ucs.ganzhou.app.api.model.OrderInfoResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FinanceService {
    @Autowired
    private FinanceMapper  financeMapper;

    public Finance getRecommendProduct()
    {
         Finance finance = financeMapper.getRecommendProduct();
         return  finance;
    }

    public OrderInfoResponseModel getOrderInfo(OrderInfoRequestModel orderInfoRequestModel)
    {
        OrderInfoResponseModel orderInfoResponseModel = financeMapper.getOrderInfo(orderInfoRequestModel);
        return  orderInfoResponseModel;
    }

    public Map<String,String> getCustomerAssets(String customerId){
        return financeMapper.getCustomerAssets(customerId);
    }
}
