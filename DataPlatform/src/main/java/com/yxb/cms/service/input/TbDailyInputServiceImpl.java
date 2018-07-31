package com.yxb.cms.service.input;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxb.cms.dao.input.TbDailyInputMapper;
import com.yxb.cms.model.input.TbDailyInput;

@Service
public class TbDailyInputServiceImpl implements TbDailyInputService {
    @Autowired
    private TbDailyInputMapper tbDailyInputMapper;
    
    @Override
    public TbDailyInput getDailyInput(String date, Integer utmSourceId) {
        return tbDailyInputMapper.selectByPrimaryKey(date, utmSourceId);
    }
    
    @Override
    public TbDailyInput getDailyInputByDescription(Date date, String description) {
        return tbDailyInputMapper.selectByPrimaryKeyByDescription(date, description);
    }

    @Override
    public List<TbDailyInput> getAllDailyInputs(Date date) {
        return tbDailyInputMapper.selectByDate(date);
    }

    @Override
    public List<TbDailyInput> getDailyInputs(int pageSize, int page) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("offset", (page - 1) * pageSize);
        paramMap.put("rows", pageSize);
        return tbDailyInputMapper.select(paramMap);
    }

    @Override
    public int getTotalCount() {
        return tbDailyInputMapper.selectCount();
    }

    @Override
    public int addDailyInput(TbDailyInput tbDailyInput) {
        return tbDailyInputMapper.insert(tbDailyInput);
    }

    @Override
    public int removeDailyInput(Date date, Integer utmSourceId) {
        return tbDailyInputMapper.deleteByPrimaryKey(date, utmSourceId);
    }

    @Override
    public int updateDailyInput(TbDailyInput tbDailyInput) {
        return tbDailyInputMapper.updateByPrimaryKey(tbDailyInput);
    }
}
