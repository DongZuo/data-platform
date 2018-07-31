package com.yxb.cms.controller.input;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yxb.cms.dao.input.MarketFeeAccountRegMapper;
import com.yxb.cms.model.input.MarketFeeAccountReg;

@Controller
public class PrepaidController {
    @Autowired
    private MarketFeeAccountRegMapper marketFeeAccountRegMapper;
    
    @ResponseBody
    @RequestMapping(value = "/listPrepaids", method = RequestMethod.POST)
    public Map<String, Object> listPrepaids(String dateStart,String dateEnd, String dept, String preid,Integer rows, Integer page) {
    	Map<String, Object> params = new HashMap<String, Object>();	
        Map<String, Object> result = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Date d = new Date();
		if(dateStart == null || "".equals(dateStart)){
			params.put("dateStart",sdf.format(new Date(d.getTime() - 14 * 24 * 60 * 60 * 1000)));
		}else{
			params.put("dateStart",dateStart);
		}
		if(dateStart == null || "".equals(dateStart)){
			params.put("dateEnd",sdf.format(new Date(d.getTime()+ 1 * 24 * 60 * 60 * 1000)));
		}else{
			params.put("dateEnd",dateEnd);
		}
        if (dept == null) {
        	dept = "";
        }
        if (preid == null || "".equals(preid)) {
        	preid = "";
        } 
        params.put("dept", dept);
        params.put("pointLogId", preid);
        params.put("rows",rows);
		params.put("offset", (page - 1) * rows);
        List<MarketFeeAccountReg> marketFeeAccountRegs = marketFeeAccountRegMapper.selectListByPrimaryKey(params);
        
        result.put("total", marketFeeAccountRegMapper.getCount(params));
        result.put("rows", marketFeeAccountRegs);
        return result;
        //	and p.time_ BETWEEN #{dateStart,jdbcType=DATE} AND #{dateEnd,jdbcType=DATE}
    }
    
    @ResponseBody
    @RequestMapping(value = "/updatePrepaids", method = RequestMethod.POST)
    public Map<String, Object> updatePrepaids(String rows) {
        List<MarketFeeAccountReg> marketFeeAccountRegs = JSON.parseArray(rows, MarketFeeAccountReg.class);
        Map<String, Object> result = new HashMap<String, Object>();
        int retCode = 1;
        try {
            for (MarketFeeAccountReg marketFeeAccountReg : marketFeeAccountRegs) {
                // 按日期和utmSourceId检查，如果没有则插入，否则更新
//                if (marketFeeAccountReg.getTime() == null) {
//                    retCode = 0;
//                    result.put("errMsg", "请选择日期。");
//                    break;
//                }
             	Map<String, Object> params = new HashMap<String, Object>();	
                Integer pointLogId = marketFeeAccountReg.getId();
                params.put("pointLogId", pointLogId);
                MarketFeeAccountReg marketFeeAccountRegData = marketFeeAccountRegMapper.selectByPrimaryKey(params);
                boolean exists = true;
                int code = 0;
                if (marketFeeAccountRegData == null) {
                    exists = false;
                    marketFeeAccountRegData = new MarketFeeAccountReg();
                }
                    marketFeeAccountRegData.setAmount(marketFeeAccountReg.getAmount());
                    marketFeeAccountRegData.setBanlance(marketFeeAccountReg.getBanlance());
                    marketFeeAccountRegData.setMarketRegAmount(marketFeeAccountReg.getMarketRegAmount());
                    marketFeeAccountRegData.setOperationRegAmount(marketFeeAccountReg.getOperationRegAmount());
                    marketFeeAccountRegData.setOtherRegAmount(marketFeeAccountReg.getOtherRegAmount());
                    marketFeeAccountRegData.setTime(marketFeeAccountReg.getTime());
                    marketFeeAccountRegData.setTradeId(marketFeeAccountReg.getTradeId());
                    marketFeeAccountRegData.setWealthRegAmount(marketFeeAccountReg.getWealthRegAmount());
                    marketFeeAccountRegData.setCustomerRegAmount(marketFeeAccountReg.getCustomerRegAmount());
                    marketFeeAccountRegData.setDataRegAmount(marketFeeAccountReg.getDataRegAmount());
                    marketFeeAccountRegData.setId(marketFeeAccountReg.getId());

                    if (exists) {
                    	code = marketFeeAccountRegMapper.deleteByPrimaryKey(pointLogId);
                    }                    
                        code = marketFeeAccountRegMapper.insert(marketFeeAccountRegData);
                        
                if (code == 0) {
                    retCode = 0;
                }
            }
        } catch (Exception e) {
            retCode = 0;
            result.put("errMsg", "保存充值记录数据失败。");
            e.printStackTrace();
        }
        result.put("success", retCode == 1);
        return result;
    }
}
