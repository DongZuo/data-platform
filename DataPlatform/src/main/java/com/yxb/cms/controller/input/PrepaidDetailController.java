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

import com.yxb.cms.dao.input.FeeAccountBalanceMapper;
import com.yxb.cms.model.input.FeeAccountBalance;
@Controller
public class PrepaidDetailController {
    @Autowired
    private FeeAccountBalanceMapper feeAccountBalanceMapper;
    
    @ResponseBody
    @RequestMapping(value = "/listPrepaidDetails", method = RequestMethod.POST)
    public Map<String, Object> listPrepaids(String dateStart,String dateEnd, String dept,Integer rows, Integer page) {
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
			params.put("dateEnd",sdf.format(new Date(d.getTime() + 1 * 24 * 60 * 60 * 1000)));
		}else{
			params.put("dateEnd",dateEnd);
		}
        if (dept == null) {
        	dept = "";
        }
        params.put("dept", dept);
        params.put("rows",rows);
		params.put("offset", (page - 1) * rows);
        List<FeeAccountBalance> marketFeeAccountRegs = feeAccountBalanceMapper.selectByPrimaryKey(params);
        
        result.put("total", feeAccountBalanceMapper.getCount(params));
        result.put("rows", marketFeeAccountRegs);
        return result;
        //	and p.time_ BETWEEN #{dateStart,jdbcType=DATE} AND #{dateEnd,jdbcType=DATE}
    }
}
