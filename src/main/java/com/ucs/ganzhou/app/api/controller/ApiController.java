package com.ucs.ganzhou.app.api.controller;

import com.ucs.ganzhou.app.api.model.Finance;
import com.ucs.ganzhou.app.api.model.OrderInfoRequestModel;
import com.ucs.ganzhou.app.api.model.OrderInfoResponseModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.ucs.ganzhou.app.api.service.*;

import java.text.SimpleDateFormat;
import java.util.*;
import com.ucs.ganzhou.app.api.utils.*;

@Controller
public class ApiController {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Logger logger = Logger.getLogger(ApiController.class);
    @Autowired
    private FinanceService financeService;

    /*
    1获取推荐项目(一个)
     */
    @RequestMapping(value = "/getRecommendProduct", method ={ RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String getRecommendProduct()
    {
        String  requestId = UUID.randomUUID().toString();
        logger.debug(String.format("【%s】getRecommendProduct IN：null",requestId));
        Map<String,String> responseObject = new LinkedHashMap<String,String>();
        Finance finance =  financeService.getRecommendProduct();
        if(finance == null){
            responseObject.put("code",ConstUtils.FAIL);
            responseObject.put("msg","no record");
        }
        else {
            responseObject.put("code",ConstUtils.OK);
            responseObject.put("msg","");
            responseObject.put("projectName ",finance.getProjectName());
            responseObject.put("rate ", String.valueOf(finance.getRate()));
            responseObject.put("duration ", String.valueOf(finance.getDuration()));
            responseObject.put("amount ", String.valueOf(finance.getAmount()));
            responseObject.put("beginTime ", simpleDateFormat.format(finance.getBeginTime()));
            responseObject.put("minBuyAmount ", String.valueOf(finance.getMinBuyAmount()));
        }
        String xmlResponse = XmlUtils.callMapToXML(responseObject);
        logger.debug(String.format("【%s】getRecommendProduct OUT：%s",requestId,xmlResponse));
        return xmlResponse;
    }
    /*
     获取订单信息(单笔)
     */
    @RequestMapping(value = "/getOrderInfo", method ={ RequestMethod.POST})
    @ResponseBody
    public  String getOrderInfo(@RequestBody String requestBody){
        String  requestId = UUID.randomUUID().toString();
        logger.debug(String.format("【%s】getRecommendProduct IN：%s",requestId,requestBody));

        Map<String,String> requsetParms = XmlUtils.Dom2Map(requestBody);
        OrderInfoRequestModel orderInfo = new OrderInfoRequestModel();
        orderInfo.setOrderNo(requsetParms.get("orderNo"));
        orderInfo.setCustomerId(requsetParms.get("customerId"));
        orderInfo.setSerialNo(requsetParms.get("serialNo"));

        OrderInfoResponseModel orderInfoResponseModel = financeService.getOrderInfo(orderInfo);
        Map<String,String> responseObject = new LinkedHashMap<String,String>();
        if(orderInfoResponseModel == null){
            responseObject.put("code",ConstUtils.FAIL);
            responseObject.put("msg","no record");
        }
        else {
            responseObject.put("code",ConstUtils.OK);
            responseObject.put("msg","");
            responseObject.put("createTime ",simpleDateFormat.format(orderInfoResponseModel.getCreateTime()));
            responseObject.put("amount ", String.valueOf(orderInfoResponseModel.getAmount()));
            responseObject.put("orderStatus ", String.valueOf(orderInfoResponseModel.getOrderStatus()));
        }
        String xmlResponse = XmlUtils.callMapToXML(responseObject);
        logger.debug(String.format("【%s】getRecommendProduct OUT：%s",requestId,xmlResponse));
        return xmlResponse;
    }
    /*
    查询账户投融资平台资产
     */
    @RequestMapping(value = "/getCustomerAssets", method ={ RequestMethod.POST})
    @ResponseBody
   public String getCustomerAssets(@RequestBody String requestBody)
   {
       String  requestId = UUID.randomUUID().toString();
       logger.debug(String.format("【%s】getRecommendProduct IN：%s",requestId,requestBody));

       Map<String,String> requsetParms = XmlUtils.Dom2Map(requestBody);
       String customerId = requsetParms.get("customerId");
       Map<String,String> customerAssetsMap = financeService.getCustomerAssets(customerId);
       Map<String,Object> responseObject = new LinkedHashMap<String,Object>();
       if(customerAssetsMap.isEmpty()){
           responseObject.put("code",ConstUtils.FAIL);
           responseObject.put("msg","no record");
       }
       else {
           responseObject.put("code",ConstUtils.OK);
           responseObject.put("msg","");

           Map<String,String> dataList = new LinkedHashMap<String,String>();
           dataList.put("productId","LYT0001");
           dataList.put("productName","乐易投");
           dataList.put("totalIncome",customerAssetsMap.get("totalIncome"));
           dataList.put("possessionAsset",customerAssetsMap.get("possessionAsset"));
           dataList.put("frozenAmount",customerAssetsMap.get("frozenAmount"));

           Map<String,Object> data = new LinkedHashMap<String,Object>();
           data.put("dataSize",1);
           data.put("totalAmount",customerAssetsMap.get("totalAmount"));
           data.put("dataList",dataList);

           responseObject.put("data",data);
       }
       String xmlResponse = XmlUtils.callMapToXML(responseObject);
       logger.debug(String.format("【%s】getRecommendProduct OUT：%s",requestId,xmlResponse));
       return  xmlResponse;
   }

}
