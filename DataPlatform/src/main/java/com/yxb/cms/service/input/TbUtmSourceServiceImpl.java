package com.yxb.cms.service.input;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxb.cms.dao.input.TbUtmSourceMapper;
import com.yxb.cms.model.input.TbUtmSource;


@Service
public class TbUtmSourceServiceImpl implements TbUtmSourceService {
    @Autowired
    private TbUtmSourceMapper tbUtmSourceMapper;

    @Override
    public List<TbUtmSource> getAllUtmSources() {
        return tbUtmSourceMapper.selectAll();
    }

    @Override
    public List<TbUtmSource> getUtmSources(String description, String category,String parentCategory, String platform, String chargeType, String abbreviation, int pageSize, int page) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("descriptionCond", "%" + description + "%");
        paramMap.put("categoryCond", category);
        paramMap.put("parentCategoryCond", parentCategory);
        paramMap.put("platformCond", platform);
        paramMap.put("chargeTypeCond", chargeType);
        paramMap.put("abbreviationCond", "%" + abbreviation + "%");
        paramMap.put("offset", (page - 1) * pageSize);
        paramMap.put("rows", pageSize);
        return tbUtmSourceMapper.select(paramMap);
    }
    
    @Override
    public TbUtmSource getUtmSource(int id) {
        return tbUtmSourceMapper.selectByPrimaryKey(id);
    }
    
    @Override
    public TbUtmSource getUtmSourceByDescription(String description) {
        return tbUtmSourceMapper.selectByPrimaryKeyByDescription(description);
    }

    @Override
    public int getTotalCount(String description, String category,String parentCategory,String platform, String chargeType, String abbreviation) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("descriptionCond", "%" + description + "%");
        paramMap.put("categoryCond", category);
        paramMap.put("parentCategoryCond", parentCategory);
        paramMap.put("platformCond", platform);
        paramMap.put("chargeTypeCond", chargeType);
        paramMap.put("abbreviationCond", "%" + abbreviation + "%");
        return tbUtmSourceMapper.selectCount(paramMap);
    }

    @Override
    public int addUtmSource(TbUtmSource tbUtmSource) {
        return tbUtmSourceMapper.insert(tbUtmSource);
    }

    @Override
    public int removeUtmSource(int id) {
        return tbUtmSourceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateUtmSource(TbUtmSource tbUtmSource) {
        return tbUtmSourceMapper.updateByPrimaryKey(tbUtmSource);
    }

}
