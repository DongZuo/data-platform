package com.yxb.cms.service.input;

import java.util.Date;
import java.util.List;

import com.yxb.cms.model.input.TbDailyInput;


public interface TbDailyInputService {
    public TbDailyInput getDailyInput(String date, Integer utmSourceId);
    
    public TbDailyInput getDailyInputByDescription(Date date, String description);
    
    public List<TbDailyInput> getAllDailyInputs(Date date);
    
    public List<TbDailyInput> getDailyInputs(int pageSize, int page);
    
    public int getTotalCount();
    
    public int addDailyInput(TbDailyInput tbDailyInput);
    
    public int removeDailyInput(Date date, Integer utmSourceId);
    
    public int updateDailyInput(TbDailyInput tbDailyInput);
}
