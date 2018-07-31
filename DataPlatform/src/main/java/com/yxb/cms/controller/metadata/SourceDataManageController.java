package com.yxb.cms.controller.metadata;

import com.yxb.cms.controller.BasicController;
import com.yxb.cms.model.MetaDataResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

/**
 * @author yangxin_ryan
 * metaDataManage model
 */
@Controller
@RequestMapping("/sourceDataManage")
public class SourceDataManageController extends BasicController {

    @RequestMapping(value = "/getDatabases")
    public void getDataBasesInfo(HttpServletRequest request, PrintWriter printWriter) {

        printWriter.println();
        printWriter.close();
    }

    @RequestMapping(value = "/updateDatabases")
    public void updateDataBasesInfo(HttpServletRequest request, PrintWriter printWriter) {

        printWriter.println();
        printWriter.close();
    }

    @RequestMapping(value = "/getSourceTable")
    public MetaDataResult getSourceTable() {
        return null;
    }

    @RequestMapping(value = "/getSourceTableDetail")
    public MetaDataResult getSourceTableDetail() {
        return null;
    }

    @RequestMapping(value = "/getEditTable")
    public MetaDataResult getEditTable() {
        return null;
    }

    @RequestMapping(value = "/getSubject")
    public MetaDataResult getSubject() {
        return null;
    }

    @RequestMapping(value = "/saveEditTable")
    public MetaDataResult saveEditTable() {
        return null;
    }

    @RequestMapping(value = "/getEditWords")
    public MetaDataResult getEditWords() {
        return null;
    }
}
