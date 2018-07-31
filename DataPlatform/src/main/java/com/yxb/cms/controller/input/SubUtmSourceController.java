package com.yxb.cms.controller.input;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.data.util.ExcelPOI;
import com.data.util.PasswordEncoder;
import com.data.util.PoiExt;
import com.yxb.cms.domain.vo.TbSubUtmSourceVo;
import com.yxb.cms.model.input.TbSubUtmSource;
import com.yxb.cms.model.input.TbUtmSource;
import com.yxb.cms.service.input.TbSubUtmSourceService;
import com.yxb.cms.service.input.TbUtmSourceService;

@Controller
@RequestMapping("/subChannelManage")
public class SubUtmSourceController {
    @Autowired
    private TbSubUtmSourceService tbSubUtmSourceService;
    @Autowired
    private TbUtmSourceService tbUtmSourceService;
    
    @ResponseBody
    @RequestMapping(value = "/getChannelSecond", method = RequestMethod.POST)
    public Map<String, Object> listSubUtmSources(@RequestBody Map<String,String> body) {
    	String utmSource = body.get("sourceName");
    	String description = body.get("parentName");
    	String plan = body.get("plan");
    	String unit = body.get("unit");
    	String keyword = body.get("keyword");
    	Integer rows = Integer.valueOf(body.get("pageSize"));
    	Integer page = Integer.valueOf(body.get("page"));
        List<TbUtmSource> tbUtmSources = tbUtmSourceService.getAllUtmSources();
        Map<String, Object> data = new HashMap<String, Object>();
        if (utmSource == null) {
            utmSource = "";
        }
        if (description == null) {
            description = "";
        }
        if (plan == null) {
            plan = "";
        }
        if (unit == null) {
            unit = "";
        }
        if (keyword == null) {
            keyword = "";
        }
        try {
            utmSource = java.net.URLDecoder.decode(utmSource, "UTF-8");
            description = java.net.URLDecoder.decode(description, "UTF-8");
            plan = java.net.URLDecoder.decode(plan, "UTF-8");
            unit = java.net.URLDecoder.decode(unit, "UTF-8");
            keyword = java.net.URLDecoder.decode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        data.put("total",100);
        //data.put("total", tbSubUtmSourceService.getTotalCount(utmSource, description, plan, unit, keyword));
        List<TbSubUtmSourceVo> tbSubUtmSourceVos = new ArrayList<TbSubUtmSourceVo>();
        for (TbSubUtmSource tbSubUtmSource : tbSubUtmSourceService.getSubUtmSources(utmSource, description, plan, unit, keyword, rows, page)) {
            if(tbSubUtmSource !=null && tbSubUtmSource.getId() != null){
                TbSubUtmSourceVo tbSubUtmSourceVo = new TbSubUtmSourceVo();
                tbSubUtmSourceVo.setId(tbSubUtmSource.getId());
                tbSubUtmSourceVo.setKey(tbSubUtmSource.getId().toString());
                tbSubUtmSourceVo.setParentId(tbSubUtmSource.getParentId());
                for (TbUtmSource tbUtmSource : tbUtmSources) {
                    if (tbSubUtmSourceVo.getParentId().toString().equals(tbUtmSource.getId().toString())) {
                        tbSubUtmSourceVo.setParentName(tbUtmSource.getDescription());
                        break;
                    }
                }
                tbSubUtmSourceVo.setSourceName(tbSubUtmSource.getSourceName());
                tbSubUtmSourceVo.setPlan(tbSubUtmSource.getPlan());
                tbSubUtmSourceVo.setUnit(tbSubUtmSource.getUnit());
                tbSubUtmSourceVo.setKeyword(tbSubUtmSource.getKeyword());
                tbSubUtmSourceVo.setLandingPage(tbSubUtmSource.getLandingPage());
                tbSubUtmSourceVos.add(tbSubUtmSourceVo);
            }
        }
        data.put("rows", tbSubUtmSourceVos);
        Map<String,Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		result.put("msg", "数据查询成功");
		result.put("data", data);       
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/postChannelSecondAdd", method = RequestMethod.POST)
    public Map<String, Object> addSubUtmSource(@RequestBody Map<String,String> body) {
//    	  "parentName": "2345导航名站",--------------------渠道描述
//    	   "sourceName": "pc_mz_2345",--------------------utmSource
//    	   "plan": null,               --------------------计划
//    	   "unit": null,               --------------------单元
//    	   "keyword": 1,               --------------------关键字
//    	   "key":1                     --------------------该条数据的唯一标识
    	TbSubUtmSource tbSubUtmSource = new TbSubUtmSource();
    	tbSubUtmSource.setParentId(Integer.valueOf(body.get("parentId")));
    	tbSubUtmSource.setSourceName(body.get("parentName"));
    	tbSubUtmSource.setPlan(body.get("plan"));
    	tbSubUtmSource.setUnit(body.get("unit"));
    	tbSubUtmSource.setKeyword(body.get("keyword"));
    	
        int retCode = 0;
        try {
            tbSubUtmSource.setSourceName(tbSubUtmSource.getSourceName().replace("\n", ""));
            retCode = tbSubUtmSourceService.addSubUtmSource(tbSubUtmSource);
        }catch (Exception e) {
            e.printStackTrace();
        }
        Map<String,Object> result = new HashMap<String, Object>();
        if(retCode == 1){
      		result.put("code", 0);
      		result.put("msg", "数据写入成功");
      		result.put("data", "");       
        }else{
        	result.put("code", -1);
      		result.put("msg", "数据写入失败");
      		result.put("data", "");  
        }
        return result;
    }
    
//    @ResponseBody
//    @RequestMapping(value = "/addSubUtmSources", method = RequestMethod.POST)
//    public String addSubUtmSources(HttpServletRequest request, Integer parentId, MultipartFile keywordFile) {
//        int retCode = 0;
//        String path = request.getSession().getServletContext().getRealPath("/upload");
//        String fileName = keywordFile.getOriginalFilename().trim();
//        String baseUtmSource = tbUtmSourceService.getUtmSource(parentId).getAbbreviation();
//        if (fileName != "" && fileName.endsWith(".txt")) {
//        	try {
//        	String salt = "RRD";
//  			  PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "MD5");
//        	    File localFile = new File(path, fileName);
//        	    keywordFile.transferTo(localFile);
//        	    List<String> lines = new ArrayList<String>();
//        	    List<TbSubUtmSource> tbSubUtmSources = new ArrayList<TbSubUtmSource>();
//        	    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(localFile), "UTF-8"));
//        	    String line = null;
//        	    int lineNo = 0;
//                while ((line = reader.readLine()) != null) {
//                    lineNo++;
//                    if (lineNo == 1) {
//                        continue;
//                    }
//                    String[] arr = line.split("\t");
//                    String plan = arr[0];
//                    String unit = arr[1];
//                    String keyword = arr[2];
//                    String landingPage = arr[3];
//                    if (!landingPage.startsWith("http")) {
//                        continue;
//                    }
//                    String utmSource = baseUtmSource + "_"+encoderMd5.encode(plan + unit + keyword);
//                    //String utmSource = baseUtmSource + "_" + String.valueOf((plan + unit + keyword).hashCode()+createRandom());
//                    lines.add(String.format("%s\t%s\t%s\t%s?utmSource=%s", plan, unit, keyword, landingPage, utmSource));
//                    TbSubUtmSource tbSubUtmSource = new TbSubUtmSource();
//                    tbSubUtmSource.setParentId(parentId);
//                    tbSubUtmSource.setSourceName(utmSource);
//                    tbSubUtmSource.setPlan(plan);
//                    tbSubUtmSource.setUnit(unit);
//                    tbSubUtmSource.setKeyword(keyword);
//                    tbSubUtmSources.add(tbSubUtmSource);
//                }
//                reader.close();
//                
//                File resultFile = new File(path, "result.txt");
//				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(resultFile), "UTF-8"));
//				for (String s : lines) {
//				    writer.write(s);
//				    writer.newLine();
//				}
//				writer.close();
//				
//				retCode = 1;
//				for (TbSubUtmSource tbSubUtmSource : tbSubUtmSources) {
//				    int code = tbSubUtmSourceService.addSubUtmSource(tbSubUtmSource);
//				    if (code == 0) {
//				        retCode = 0;
//				    }
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//        }
//        return retCode == 1 ? "success" : "failed";
//    }
   
    
    @RequestMapping(value = "/fileDown")
    @ResponseBody
    public void fileDownload(HttpServletResponse response, HttpServletRequest request, String fileName) throws IOException {
        String fileNameEncode = new String(fileName.getBytes("iso-8859-1"), "utf-8");

        String fileDir = request.getSession().getServletContext().getRealPath("/upload/") +
                File.separator + fileNameEncode;
//        System.out.println(fileDir);
        OutputStream os = null;
        try {
            // path是指欲下载的文件的路径。
            File file = new File(fileDir);
            FileInputStream fis = new FileInputStream(fileDir);
            response.reset();// 清空输出流
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.addHeader("Content-Disposition", "attachment;filename="
                    + fileName);
            response.addHeader("Content-Length", "" + file.length());
            os = response.getOutputStream();// 取得输出流

//            response.setContentType("application/vnd.ms-excel");
            byte[] mybyte = new byte[8192];
            int len = 0;
            while ((len = fis.read(mybyte)) != -1) {
                os.write(mybyte, 0, len);
            }
            fis.close();
            os.close();
        }catch (IOException e) {
            throw e;
        }

    }
    
    /**
     * 导入EXCEL文件解析
     * @param request
     * @param parentId
     * @param keywordFile
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/importChannelSecond", method = RequestMethod.POST)
    public Map<String, Object> addSubUtmSourcesExcel(HttpServletRequest request, Integer parentId, MultipartFile keywordFile) {
    	 int retCode = 0;
         String baseUtmSource = tbUtmSourceService.getUtmSource(parentId).getAbbreviation();
        String fileName = keywordFile.getOriginalFilename().trim();
        if (fileName != "" && fileName.endsWith(".xlsx")) {
    		PoiExt poiExt = new PoiExt();
    		Workbook wb = null;
    		try {
    			 String salt = "RRD";
    			  PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "MD5");
    			List<String> lines = new ArrayList<String>();
        	    List<TbSubUtmSource> tbSubUtmSources = new ArrayList<TbSubUtmSource>();
    			wb = poiExt.readExcel(keywordFile.getInputStream(),fileName);
    			Sheet sheet = wb.getSheetAt(0);
//    			Row row = sheet.getRow(0);
    			int rowNum = sheet.getPhysicalNumberOfRows(); 	//查询总行数
//    			int colNum = row.getPhysicalNumberOfCells();	//查询总列数
    			// 遍历行 从第二行开始遍历
    			if(rowNum>1){
    				for (int i = 1; i < rowNum; i++) {
    					Row currRow = sheet.getRow(i);
    					if(currRow!=null){
    						try {
    							//单条导入
    								currRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
    								currRow.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
    								currRow.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
    								currRow.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
    								currRow.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
    								String utmMedium = poiExt.getCellFormatValue(currRow.getCell(1));
    							 	String plan = poiExt.getCellFormatValue(currRow.getCell(2));
    			                    String unit = poiExt.getCellFormatValue(currRow.getCell(3));
    			                    String keyword = poiExt.getCellFormatValue(currRow.getCell(4));
    			                    String landingPage = poiExt.getCellFormatValue(currRow.getCell(5));
    			                    if(landingPage.equals("") || landingPage == null){
    			                    	break;
    			                    }
//    			    			    String utmSource = baseUtmSource + "_"+encoderMd5.encode(plan + unit + keyword);
    			                    String utmSource = baseUtmSource + "&utm_medium="+utmMedium.hashCode()+"&utm_campaign="+plan.hashCode()+"&utm_content="+unit.hashCode()+"&utm_term="+keyword.hashCode()+"_"+System.currentTimeMillis()+(int)(Math.random()*100);
    			                    //String utmSource = baseUtmSource + "_" + String.valueOf((plan + unit + keyword).hashCode()+createRandom());   			                   
    			                    TbSubUtmSource tbSubUtmSource = new TbSubUtmSource();
    			                    tbSubUtmSource.setParentId(parentId);
    			                    tbSubUtmSource.setSourceName(utmSource);
    			                    tbSubUtmSource.setBase_utm_source(baseUtmSource);
    			                    tbSubUtmSource.setPlan(plan);
    			                    tbSubUtmSource.setUnit(unit);
    			                    tbSubUtmSource.setKeyword(keyword);
    			                    tbSubUtmSource.setUtmMedium(utmMedium);
    			                    tbSubUtmSource.setLandingPage(landingPage);
    			                    tbSubUtmSources.add(tbSubUtmSource);
    			                    try{
    			    				    int code = tbSubUtmSourceService.addSubUtmSource(tbSubUtmSource);
    			    				    if (code == 0) {
    			    				        retCode = 0;
    			    				    }else{
    		    			                if(landingPage.indexOf("?")>-1){
    		   			                    	 lines.add(String.format("%s\t%s\t%s\t%s\t%s&utm_source=%s",utmMedium, plan, unit, keyword, landingPage, utmSource));	
    		   			                    }else{
    		   			                    	 lines.add(String.format("%s\t%s\t%s\t%s\t%s?utm_source=%s",utmMedium, plan, unit, keyword, landingPage, utmSource));
    		   			                    }
    			    				    }
    			    					}catch(Exception e){
    			    						continue;
    			    					}    

    						} catch (Exception e) {    
    							continue;
    						}
    					}
    				}  				
    				//后续逻辑操作
//    				for (TbSubUtmSource tbSubUtmSource : tbSubUtmSources) {
//    					try{
//    				    int code = tbSubUtmSourceService.addSubUtmSource(tbSubUtmSource);
//    				    if (code == 0) {
//    				        retCode = 0;
//    				    }
//    					}catch(Exception e){
//    						continue;
//    					}
//    				}
    				String path = request.getSession().getServletContext().getRealPath("/upload");
                    File resultFile = new File(path, "result.txt");
    				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(resultFile), "UTF-8"));
    				for (String s : lines) {
    				    writer.write(s);
    				    writer.newLine();
    				}
    				writer.close();
    				retCode = 1;
    				lines.clear();
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}finally{
    			try {
    				wb.close();
    				keywordFile.getInputStream().close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
        }
        Map<String,Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		result.put("msg", "数据导入成功");
		result.put("data", "");       
        return result;
    }
 
    
    /**  
     * 导出表格数据 
     */       
    @RequestMapping(value = "/excelSubUtmSource")    
    @ResponseBody  
    public void excel(@RequestBody Map<String,String> body,HttpSession session,HttpServletResponse response){
    	String utmSource = body.get("utmSource");
    	String description = body.get("description");
    	String plan = body.get("plan");
    	String unit = body.get("unit"); 
    	String keyword = body.get("keyword");
    	
    	if (utmSource == null) {
            utmSource = "";
        }
        if (description == null) {
            description = "";
        }
        if (plan == null) {
            plan = "";
        }
        if (unit == null) {
            unit = "";
        }
        if (keyword == null) {
            keyword = "";
        }
        try {
            utmSource = java.net.URLDecoder.decode(utmSource, "UTF-8");
            description = java.net.URLDecoder.decode(description, "UTF-8");
            plan = java.net.URLDecoder.decode(plan, "UTF-8");
            unit = java.net.URLDecoder.decode(unit, "UTF-8");
            keyword = java.net.URLDecoder.decode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    	List<TbUtmSource> tbUtmSources = tbUtmSourceService.getAllUtmSources();
        List<Map<String, Object>> tbSubUtmSourceVos = new ArrayList<Map<String, Object>>();
        for (TbSubUtmSource tbSubUtmSource : tbSubUtmSourceService.getSubUtmSourcesExcel(utmSource, description, plan, unit, keyword)) {
        	Map<String, Object> map = new LinkedHashMap<String, Object>();
            TbSubUtmSourceVo tbSubUtmSourceVo = new TbSubUtmSourceVo();
            tbSubUtmSourceVo.setId(tbSubUtmSource.getId());
            tbSubUtmSourceVo.setParentId(tbSubUtmSource.getParentId());
            for (TbUtmSource tbUtmSource : tbUtmSources) {
                if (tbSubUtmSourceVo.getParentId() == tbUtmSource.getId()) {
                    tbSubUtmSourceVo.setParentName(tbUtmSource.getDescription());
                    break;
                }
            }
            map.put("sourceName",tbSubUtmSource.getSourceName()==null?"":tbSubUtmSource.getSourceName());
            map.put("渠道描述", tbSubUtmSourceVo.getParentName()==null?"":tbSubUtmSourceVo.getParentName());
            map.put("计划", tbSubUtmSource.getPlan()==null?"":tbSubUtmSource.getPlan());
            map.put("单元", tbSubUtmSource.getUnit()==null?"":tbSubUtmSource.getUnit());
            map.put("关键词", tbSubUtmSource.getKeyword()==null?"":tbSubUtmSource.getKeyword());
				tbSubUtmSourceVos.add(map);
        }
        
 	   ExcelPOI<Object> ex = new ExcelPOI<Object>(); 
 	   Object[] keys = null;
 		if(tbSubUtmSourceVos.size()>0){
 			Map<String, Object> map = tbSubUtmSourceVos.get(0);
 				Set<String> keyset = map.keySet();
 				 keys = keyset.toArray();
 		}
 		String[] headers = new String[keys.length];
 		int flag = 0;
 		for (Object object : keys) {
 			headers[flag] = object.toString();
 			flag++;
 		}
 		OutputStream out = null;
 		try {
 			out = new FileOutputStream("a.xls");
 			ex.exportExcel(headers, tbSubUtmSourceVos, out);
 			out.close();  
 			ExcelPOI.download(response,"a.xls","定制化报表");
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
    } 
    
    @ResponseBody
    @RequestMapping(value = "/deleteChannelSecond", method = RequestMethod.POST)
    public Map<String, Object> removeSubUtmSource(@RequestBody Map<String,Object> body) {
    	 Map<String,Object> result = new HashMap<String, Object>();
         ArrayList<String> keys = (ArrayList)body.get("key");
        int retCode = 0;
        for (String string : keys) {
   		 try {
   			 	retCode = tbSubUtmSourceService.removeSubUtmSource(Integer.valueOf(string));
   	        } catch (Exception e) {
   	            e.printStackTrace();
   	            result.put("code", -1);
   	    		result.put("msg", "删除失败");
   	    		result.put("data", ""); 
   	        }	
		}
		result.put("code", 0);
		result.put("msg", "删除成功");
		result.put("data", "");       
       return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/postChannelEditSecond", method = RequestMethod.POST)
    public Map<String, Object> editSubUtmSource(@RequestBody Map<String,String> body) {
    	TbSubUtmSource tbSubUtmSource = new TbSubUtmSource();
    	tbSubUtmSource.setParentId(Integer.valueOf(body.get("parentId")));
    	tbSubUtmSource.setSourceName(body.get("sourceName"));
    	//tbSubUtmSource.setSourceName(body.get("parentName"));
    	tbSubUtmSource.setPlan(body.get("plan"));
    	tbSubUtmSource.setUnit(body.get("unit"));
    	tbSubUtmSource.setKeyword(body.get("keyword"));
    	tbSubUtmSource.setKey(body.get("key"));
    	tbSubUtmSource.setId(Integer.valueOf(body.get("id")));
        int retCode = 0;
        try {
            tbSubUtmSource.setSourceName(tbSubUtmSource.getSourceName().replace("\n", ""));
            retCode = tbSubUtmSourceService.updateSubUtmSource(tbSubUtmSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String,Object> result = new HashMap<String, Object>();
        if(retCode == 1){
      		result.put("code", 0);
      		result.put("msg", "数据修改成功");
      		result.put("data", "");       
        }else{
        	result.put("code", -1);
      		result.put("msg", "数据修改失败");
      		result.put("data", "");  
        }
        return result;
    }
    
    public String createRandom(){
    	int x1;
    	int x2;
    	int x3;
    	int x4;
    	int x5;
    	x1=(int)(Math.random()*10);
		x2=(int)(Math.random()*10);
		x3=(int)(Math.random()*10);
		x4=(int)(Math.random()*10);
		x5=(int)(Math.random()*10);
		return String.valueOf(x1)+String.valueOf(x2)+String.valueOf(x3)+String.valueOf(x4)+String.valueOf(x5);
	}
    
    /**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public Map<String,Object> convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class<? extends Object> type = bean.getClass();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i]; 
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
}
