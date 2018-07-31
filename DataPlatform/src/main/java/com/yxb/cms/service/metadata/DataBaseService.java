package com.yxb.cms.service.metadata;

import com.yxb.cms.dao.meta.MetaDBTableMapper;
import com.yxb.cms.model.MetaDataResult;
import com.yxb.cms.model.metadata.TreeParent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;


/**
 * @author yangxin_ryan
 */
@Component
public class DataBaseService {
    private static final Logger LOG = LoggerFactory.getLogger(DataBaseService.class);

    @Autowired
    MetaDBTableMapper metaDBTableMapper;


    public MetaDataResult getDataBases() {
        MetaDataResult metaDataResult = new MetaDataResult();
        List<TreeParent> data = new ArrayList<>();
        metaDBTableMapper.getDBTableName();


        return metaDataResult;
    }
}
