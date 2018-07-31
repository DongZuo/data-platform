package com.yxb.cms.service.input;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxb.cms.dao.input.TbSubUtmSourceMapper;
import com.yxb.cms.model.input.TbSubUtmSource;


@Service
public class TbSubUtmSourceServiceImpl implements TbSubUtmSourceService {
    @Autowired
    private TbSubUtmSourceMapper tbSubUtmSourceMapper;

    @Override
    public List<TbSubUtmSource> getAllSubUtmSources() {
        return tbSubUtmSourceMapper.selectAll();
    }

    @Override
    public List<TbSubUtmSource> getSubUtmSources(String utmSource, String description, String plan, String unit, String keyword, int pageSize, int page) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("utmSourceCond", "%" + utmSource + "%");
        paramMap.put("descriptionCond", "%" + description + "%");
        paramMap.put("planCond", "%" + plan + "%");
        paramMap.put("unitCond", "%" + unit + "%");
        paramMap.put("keywordCond", "%" + keyword + "%");
        paramMap.put("offset", (page - 1) * pageSize);
        paramMap.put("rows", pageSize);
        return tbSubUtmSourceMapper.select(paramMap);
    }

    @Override
    public List<TbSubUtmSource> getSubUtmSourcesExcel(String utmSource, String description, String plan, String unit, String keyword) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("utmSourceCond", "%" + utmSource + "%");
        paramMap.put("descriptionCond", "%" + description + "%");
        paramMap.put("planCond", "%" + plan + "%");
        paramMap.put("unitCond", "%" + unit + "%");
        paramMap.put("keywordCond", "%" + keyword + "%");
        return tbSubUtmSourceMapper.selectExcel(paramMap);
    }
    
    @Override
    public int getTotalCount(String utmSource, String description, String plan, String unit, String keyword) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("utmSourceCond", "%" + utmSource + "%");
        paramMap.put("descriptionCond", "%" + description + "%");
        paramMap.put("planCond", "%" + plan + "%");
        paramMap.put("unitCond", "%" + unit + "%");
        paramMap.put("keywordCond", "%" + keyword + "%");
        return tbSubUtmSourceMapper.selectCount(paramMap);
    }

    @Override
    public int addSubUtmSource(TbSubUtmSource tbSubUtmSource) {
        return tbSubUtmSourceMapper.insert(tbSubUtmSource);
    }

    @Override
    public int removeSubUtmSource(int id) {
        return tbSubUtmSourceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateSubUtmSource(TbSubUtmSource tbSubUtmSource) {
        return tbSubUtmSourceMapper.updateByPrimaryKey(tbSubUtmSource);
    }
    
}
