package com.yxb.cms.controller.input;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.data.util.ExcelPOI;
import com.yxb.cms.model.input.SemStatistics;
import com.yxb.cms.service.input.ISemStatisticsService;


/**
 * @Description: 报表控制类
 * @author ZHAOLIANG
 * @date 2015年9月11日 上午11:00:03
 */
@Controller
@RequestMapping("/semStatistics")
public class SemStatisticsController {
	@Resource
	private ISemStatisticsService semStatisticsService;
	/**
	 * @Description: SEM报表查询
	 * @param request
	 * @return String
	 */
	@RequestMapping(value= "/getSemPage",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPage(@RequestBody Map<String,Object> body){
		String utmsource = null;//body.get("utmsource").toString();
		//String condition = body.get("condition");
		ArrayList<String> date = (ArrayList)body.get("date");
		String dateStart = date.get(0);
		String dateEnd = date.get(1);
		ArrayList<String> compareDate = (ArrayList)body.get("compareDate");
		String compareDateStart = compareDate.get(0);
		String compareDateEnd = compareDate.get(1);
		String description = body.get("description").toString();
		String category = body.get("parentCategory").toString();
		
		Map<String, Object> params = new HashMap<String, Object>();	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(dateStart == null || dateStart.equals("")){
			dateStart = sdf.format(new Date());
			dateEnd = dateStart;
		}
		if(compareDateStart == null || compareDateStart.equals("")){
			compareDateStart = sdf.format(new Date());
			Calendar theCa = Calendar.getInstance();
			 theCa.setTime(new Date());
			 theCa.add(theCa.DATE, -1);
			 compareDateStart = sdf.format(theCa.getTime());
			compareDateEnd = compareDateStart;
		}
		/*if("today".equals(condition) || condition == null || "".equals(condition)){
			dateStart = sdf.format(new Date());
			dateEnd = dateStart;
		}else if("yesterday".equals(condition)){
			Calendar theCa = Calendar.getInstance();
			 theCa.setTime(new Date());
			 theCa.add(theCa.DATE, -1);
			 dateStart = sdf.format(theCa.getTime());
			dateEnd = dateStart;
		}else if("7days".equals(condition)){
			dateEnd = sdf.format(new Date());
			Calendar theCa = Calendar.getInstance();
			 theCa.setTime(new Date());
			 theCa.add(theCa.DATE, -6);
			 dateStart = sdf.format(theCa.getTime());
		}else if("30days".equals(condition)){
			dateEnd = sdf.format(new Date());
			Calendar theCa = Calendar.getInstance();
			 theCa.setTime(new Date());
			 theCa.add(theCa.DATE, -29);
			 dateStart = sdf.format(theCa.getTime());
		}*/
		/*try {		
		if("the_day_before".equals(condition)|| condition == null || condition.equals("null") || "".equals(condition)){
				if(dateStart.equals("")||dateStart==null){
					dateStart = sdf.format(new Date());
				}
				Date fromdate = sdf.parse(dateStart);
				Calendar newdate = Calendar.getInstance();
				newdate.setTime(fromdate);
				newdate.set(Calendar.DATE, newdate.get(Calendar.DATE) - 1);
				compareDateStart = sdf.format(newdate.getTime());
				compareDateEnd = compareDateStart;
		}else if("last_week".equals(condition)){
			if(dateStart.equals("")||dateStart==null){
				dateStart = sdf.format(new Date());
			}
			Date fromdate = sdf.parse(dateStart);
			Calendar newdate = Calendar.getInstance();
			newdate.setTime(fromdate);
			newdate.set(Calendar.DATE, newdate.get(Calendar.DATE) - 7);
			compareDateStart = sdf.format(newdate.getTime());
			compareDateEnd = compareDateStart;
		}
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		params.put("countTimeStart",dateStart.replaceAll("-", ""));
		params.put("countTimeEnd",dateEnd.replaceAll("-", ""));
		params.put("category", category);
		params.put("utmsource", utmsource);
		params.put("description", description);
		List<SemStatistics> list =  semStatisticsService.selectSemStatisticsList(params);
		int i = 0;
		for (SemStatistics semStatistics : list) {
			semStatistics.setKey(String.valueOf(i));
			i++;
		}
//		SimpleDateFormat sdfFlag =new SimpleDateFormat("HH");
//		String time=sdfFlag.format(new Date());
//		list.remove(Integer.valueOf(time)-1);
		params.put("countTimeStart",compareDateStart.replaceAll("-", ""));
		params.put("countTimeEnd",compareDateEnd.replaceAll("-", ""));
		List<SemStatistics> comparelist =  semStatisticsService.selectSemStatisticsList(params);
		int j = 100;
		for (SemStatistics semStatistics : comparelist) {
			semStatistics.setKey(String.valueOf(j));
			j++;
		}
		//分页 int count = semStatisticsService.countSemStatisticsList(params);
		Map<String, Object> data = new HashMap<String, Object>();
		//分页 result.put("total",count);
		List<SemStatistics> rowslist = new ArrayList<SemStatistics>();
		rowslist.addAll(list);
		rowslist.addAll(comparelist);
		data.put("rows",rowslist);
		 Map<String,Object> result = new HashMap<String, Object>();
			result.put("code", 0);
			result.put("msg", "数据查询成功");
			result.put("data", data);
		return result;
	}
	
	
	/**
	 * @Description: SEM报表导出
	 * @param request
	 * @return String
	 */
	@RequestMapping(value= "/excelSource")
	@ResponseBody
	public void exceSource(HttpServletResponse response,String condition,String dateStart,String dateEnd,String compareDateStart,String compareDateEnd,String description, String parentCategory) {
		String utmsource = null;
		Map<String, Object> params = new HashMap<String, Object>();	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(dateStart == null || dateStart.equals("")){
			dateStart = sdf.format(new Date());
			dateEnd = dateStart;
		}
		if(compareDateStart == null || compareDateStart.equals("")){
			compareDateStart = sdf.format(new Date());
			Calendar theCa = Calendar.getInstance();
			 theCa.setTime(new Date());
			 theCa.add(theCa.DATE, -1);
			 compareDateStart = sdf.format(theCa.getTime());
			compareDateEnd = compareDateStart;
		}
		params.put("countTimeStart",dateStart.replaceAll("-", ""));
		params.put("countTimeEnd",dateEnd.replaceAll("-", ""));
		params.put("description", description);
		params.put("category", parentCategory);
		params.put("utmsource", utmsource);
		List<Map<String, Object>> rowslist = new ArrayList<Map<String, Object>>();
		List<SemStatistics> list =  semStatisticsService.selectSemStatisticsList(params);
		for (SemStatistics semStatistics : list) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("统计区间",semStatistics.getFlagDate()==null?"":semStatistics.getFlagDate());
			map.put("统计时间",semStatistics.getTimeFlag()==null?"":semStatistics.getTimeFlag());
			map.put("渠道描述",semStatistics.getDescription()==null?"":semStatistics.getDescription());
			map.put("注册人数",semStatistics.getTotalRegUser()==null?"":semStatistics.getTotalRegUser());
			map.put("开户人数",semStatistics.getTotalOpenUser()==null?"":semStatistics.getTotalOpenUser());
			map.put("首投人数",semStatistics.getTotalLender()==null?"":semStatistics.getTotalLender());
			map.put("首投金额",semStatistics.getTotalLenderMoney()==null?"":semStatistics.getTotalLenderMoney());
			map.put("人均首投金额",semStatistics.getAvgLenderMoney()==null?"":semStatistics.getAvgLenderMoney());
			map.put("充值人数",semStatistics.getTotalPayUser()==null?"":semStatistics.getTotalPayUser());
			map.put("充值金额",semStatistics.getTotalPayMoney()==null?"":semStatistics.getTotalPayMoney());
			map.put("人均充值金额",semStatistics.getAvgPayMoney()==null?"":semStatistics.getAvgPayMoney());									
			rowslist.add(map);
		}
		params.put("countTimeStart",compareDateStart.replaceAll("-", ""));
		params.put("countTimeEnd",compareDateEnd.replaceAll("-", ""));
		List<SemStatistics> comparelist =  semStatisticsService.selectSemStatisticsList(params);
		for (SemStatistics semStatistics : comparelist) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("统计区间",semStatistics.getFlagDate()==null?"":semStatistics.getFlagDate());
		map.put("统计时间",semStatistics.getTimeFlag()==null?"":semStatistics.getTimeFlag());
		map.put("渠道描述",semStatistics.getDescription()==null?"":semStatistics.getDescription());
		map.put("注册人数",semStatistics.getTotalRegUser()==null?"":semStatistics.getTotalRegUser());
		map.put("开户人数",semStatistics.getTotalOpenUser()==null?"":semStatistics.getTotalOpenUser());
		map.put("首投人数",semStatistics.getTotalLender()==null?"":semStatistics.getTotalLender());
		map.put("首投金额",semStatistics.getTotalLenderMoney()==null?"":semStatistics.getTotalLenderMoney());
		map.put("人均首投金额",semStatistics.getAvgLenderMoney()==null?"":semStatistics.getAvgLenderMoney());
		map.put("充值人数",semStatistics.getTotalPayUser()==null?"":semStatistics.getTotalPayUser());
		map.put("充值金额",semStatistics.getTotalPayMoney()==null?"":semStatistics.getTotalPayMoney());
		map.put("人均充值金额",semStatistics.getAvgPayMoney()==null?"":semStatistics.getAvgPayMoney());
		rowslist.add(map);
		}	
		//分页 int count = semStatisticsService.countSemStatisticsList(params);
		//分页 result.put("total",count);
		ExcelPOI<Object> ex = new ExcelPOI<Object>(); 
	 	   Object[] keys = null;
	 		if(rowslist.size()>0){
	 			Map<String, Object> map = rowslist.get(0);
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
	 			ex.exportExcel(headers, rowslist, out);
	 			out.close();  
	 			ExcelPOI.download(response,"a.xls","定制化报表");
	 		} catch (Exception e) {
	 			e.printStackTrace();
	 		}
	}
	
	public static void main(String[] args) {
		SimpleDateFormat sdfFlag =new SimpleDateFormat("HH");
		String time=sdfFlag.format(new Date());
		System.out.println(Integer.valueOf(time)-1);
	}
}
