package com.org.controller;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.org.common.CommonConstant;
import com.org.container.CommonContainer;
import com.org.model.reflect.ReflectUtil;
import com.org.rute.MessageHander;
import com.org.rute.PushMessageRute;
import com.org.services.busi.SandYearService;
import com.org.servlet.CommonController;
import com.org.servlet.SmpHttpServlet;
import com.org.util.SpringUtil;
import com.org.utils.PropertiesUtil;
import com.org.utils.StringUtil;
import com.org.utils.WxUtil;

@Controller
public class SandYearController extends SmpHttpServlet implements CommonController{

	
	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(SandYearController.class);
	
	public void tocj(HttpServletRequest request,HttpServletResponse response)
			throws Exception{
		HttpSession session = request.getSession();
		// �齱����Ա
		if(session.getAttribute("cjmanager") == null) {
			// ���δ��¼
			this.forward("/view/cjlogin.jsp", request, response);
			return;
		}
		
		SandYearService yService = (SandYearService)SpringUtil.getBean("sandYearService");
		JSONObject jsonObject = yService.queryCurrentAward();
		
		String currentAwards = "";
		if(jsonObject == null) {
			currentAwards = "5";
		} else {
			String currentAwardsTemp = jsonObject.getString("currentAward");
			String isStart = jsonObject.getString("isStart");
			if(isStart.equals("0") && !currentAwardsTemp.equals("t")) {
				// �ѳ�
				currentAwards = String.valueOf(Integer.valueOf(currentAwardsTemp) -1);
			} else {
				// δ��
				currentAwards = currentAwardsTemp;
			}
		}
		
		request.getSession().setAttribute("currentAwards", currentAwards);
		this.forward("/view/cj_prize-index.jsp", request, response);	
		return;
	}
	
	public void cjchecklgn(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String enterpwd = request.getParameter("enterpwd");
		String _enterpwd = PropertiesUtil.getValue("award", "enterpwd");
		
		JSONObject noticeData = new JSONObject();
		if(enterpwd.equals(_enterpwd)) {
			noticeData.put("respCode", "10000");
			request.getSession().setAttribute("cjmanager", "logined");
		} else {
			noticeData.put("respCode", "");
			noticeData.put("respMsg", "�������");
		}
		this.write(noticeData, "utf-8", response);				
		return;
	}
	
	public void queryYearMember(HttpServletRequest request,HttpServletResponse response)
			throws Exception{
		String phoneNumber = request.getParameter("phoneNumber");
		String type = "1";
		String name = request.getParameter("userName");
		
		HttpSession session = request.getSession(true);
		
		JSONObject noticeData = new JSONObject();
		
		SandYearService yService = (SandYearService)SpringUtil.getBean("sandYearService");
		JSONObject json = yService.queryYearMember(phoneNumber, type, name);
		
		String respCode = json.getString(CommonConstant.RESP_CODE);
		noticeData.put("respCode", respCode);
		if(respCode.equals("10000")) {
			JSONObject usermeg = json.getJSONObject("usermeg");
			session.setAttribute("usermeg", usermeg);	
		} else {
			String respMsg = json.getString(CommonConstant.RESP_MSG);				
			noticeData.put("respMsg", respMsg);
		}
		this.write(noticeData, "utf-8", response);				
		return;
		
	}

	public void toIndex(HttpServletRequest request,HttpServletResponse response)
			throws Exception{		
		JSONArray arr = (JSONArray) CommonContainer.getData(CommonConstant.TER_12);	
		request.setAttribute("rewardarrary", arr);
		this.forward("/view/index.jsp", request, response);		
		return;		
	}
	
	public void querySameTableMemberList(HttpServletRequest request,HttpServletResponse response)
			throws Exception{
		try {
			String phoneNumber = request.getParameter("phoneNumber");
			String type = "2";
			JSONObject noticeData = new JSONObject();
			HttpSession session = request.getSession(true);
			
			SandYearService yService = (SandYearService)SpringUtil.getBean("sandYearService");
			JSONObject json = yService.queryYearMember(phoneNumber, type, null);

			String respCode = json.getString(CommonConstant.RESP_CODE);
			noticeData.put("respCode", respCode);
			if(respCode.equals("10000")) {
				// ͬ�������û��б�
				JSONArray otherMembers = json.getJSONArray("resultInfo");
				session.setAttribute("otherMembers", otherMembers);
			} else {
				// ������ɹ����������ҳ�棬��ʾ������Ϣ
				String respMsg = json.getString(CommonConstant.RESP_MSG);
				request.setAttribute("respMsg", respMsg);
			}
			this.forward("/view/seat.jsp", request, response);	
			return;
		} catch (Exception e) {
			log.error("������Ϣ��ѯ/ͬ����Ա��ѯʧ�ܣ�"+e.getMessage());
			throw e;
		}
		
	}
	
	/**
	 * ��Ŀ��Ʊ����ѯ
	 * @param request
	 * @param response
	 */
	public void queryProgram(HttpServletRequest request,HttpServletResponse response){
		SandYearService yService = (SandYearService)SpringUtil.getBean("sandYearService");
		JSONObject json = yService.updateProgram("", "", "2");
		JSONArray allProgram = json.getJSONArray("resultInfo");
		
		request.setAttribute("allProgram", allProgram);
		try {
			this.forward("/view/program_vote.jsp", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return;		
	}

	/**
	 * ����Ŀ״̬����ҳ��
	 * @param request
	 * @param response
	 */
	public void toProgramFlag(HttpServletRequest request,HttpServletResponse response){
		SandYearService yService = (SandYearService)SpringUtil.getBean("sandYearService");
		JSONObject json = yService.updateProgram("", "", "2");
		JSONArray allProgram = json.getJSONArray("resultInfo");
		
		request.setAttribute("allProgram", allProgram);
		try {
			this.forward("/view/program_flag.jsp", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return;		
	}

	/**
	 * ����Ŀ״̬����ҳ��
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public void updateProgramStartFlag(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String isstart = request.getParameter("isstart");
		String pnumber = request.getParameter("pnumber");
		if(isstart.equals("y")) {
			//����Ѿ��ǿ�ʼ״̬�ˣ����º����Ϊ�գ���ʶΪδ��ʼ
			isstart = "";
		} else {
			isstart = "y";
		}
		
		SandYearService yService = (SandYearService)SpringUtil.getBean("sandYearService");
		yService.updateProgramStartFlag(isstart, pnumber);
		this.redirect("/sandYear/toProgramFlag.do", response);
		return;		
	}
	
	/**
	 * ͶƱ
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void vote(HttpServletRequest request,HttpServletResponse response)throws Exception{
		// �Ȳ�ѯ���еĽ�ĿƱ��
		SandYearService yService = (SandYearService)SpringUtil.getBean("sandYearService");
		JSONObject usermeg = (JSONObject) request.getSession().getAttribute("usermeg");
		String phoneNumber = usermeg.getString("moible");
		String pnumber = request.getParameter("pnumber");
		
		JSONObject json = yService.updateProgram(phoneNumber, pnumber, "1");
		if(json.getString(CommonConstant.RESP_CODE).equals("10000")) {
			// ���³ɹ��󣬴洢��session pname Լ��Ϊ��Ŀ��id, pnumber Ϊҳ�洫���ͶƱ��Ŀid
			usermeg.put("pname", pnumber);
		}
		JSONArray allProgram = json.getJSONArray("resultInfo");
		
		request.setAttribute("allProgram", allProgram);
		this.forward("/view/program_vote.jsp", request, response);
		return;
	}
	
	public void updateProgram(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String phoneNumber = request.getParameter("phoneNumber");
		String pnumber = request.getParameter("pnumber"); //��Ŀ���
		String type = request.getParameter("type");
		try {
			SandYearService yService = (SandYearService)SpringUtil.getBean("sandYearService");
			JSONObject json = yService.updateProgram(phoneNumber, pnumber, type);
			if("2".equals(type)){
				JSONArray allProgram = json.getJSONArray("resultInfo");
				System.out.println(allProgram.size());
			}
		} catch (Exception e) {
			log.error("����ĿͶƱ/����Ŀ��ѯʧ�ܣ�"+e.getMessage());
			throw e;
		}		
	}
	
	public void queryMyPrize(HttpServletRequest request,HttpServletResponse response){
		String phoneNumber = request.getParameter("phoneNumber");
		SandYearService yService = (SandYearService)SpringUtil.getBean("sandYearService");
		JSONObject json = yService.userDraw(phoneNumber, "", "1", "", "");
		String respCode = json.getString(CommonConstant.RESP_CODE);
		try {
			if(respCode.equals("10000")) {
				request.setAttribute("yearMember", json.getJSONObject("resultInfo"));
				this.forward("/view/prize_winning.jsp", request, response);
			}else{
				this.forward("/view/prize_not_winning.jsp", request, response);
			}
		} catch (Exception e) {
			log.error("�ҵĽ�Ʒ��ѯʧ�ܣ�"+e.getMessage());
		}		
	}
	
	public void lotteryDrawState(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String phoneNumber = request.getParameter("phoneNumber");
		JSONObject noticeData = new JSONObject();
		SandYearService yService = (SandYearService)SpringUtil.getBean("sandYearService");
		JSONObject json = yService.userDraw(phoneNumber, "", "1", "", "");
		System.out.println("json:"+json);
		String respCode = json.getString(CommonConstant.RESP_CODE);
		noticeData.put("respCode", respCode);
		noticeData.put("isprize", json.getJSONObject("resultInfo").getString("isprize"));
		String respMsg = json.getString(CommonConstant.RESP_MSG);				
		noticeData.put("respMsg", respMsg);
		this.write(noticeData, "utf-8", response);				
		return;
	}
	
	public void acceptAward(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String phoneNumber = request.getParameter("phoneNumber");
		try {
			SandYearService yService = (SandYearService)SpringUtil.getBean("sandYearService");
			JSONObject json = yService.acceptAward(phoneNumber);
			String respCode = json.getString(CommonConstant.RESP_CODE);
			if(respCode.equals("10000")) {
				this.redirect("/sandYear/toHelp.do", response);
				//this.forward("/sandYear/toHelp.do", request, response);
			}else{
				request.setAttribute("errmsg",json);
				this.forward("/view/acceptError.jsp", request, response);
			}
		} catch (Exception e) {
			log.error("ɨ���콱ʧ�ܣ�"+e.getMessage());
			throw e;
		} 
	}
	
/*
 * public void userDraw(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject noticeData = new JSONObject();
		
		String level = request.getParameter("level");
		SandYearService yService = (SandYearService)SpringUtil.getBean("sandYearService");
		JSONObject json = yService.userDraw("", level, "2", "", "");
		String respCode = json.getString(CommonConstant.RESP_CODE);
		noticeData.put("respCode", respCode);
		noticeData.put("level", level);
		if("10000".equals(respCode)){
			JSONArray memArray = json.getJSONArray("resultInfo");
			noticeData.put("memArray", memArray);
			log.info(level+"�Ƚ���"+memArray);
		}else{
			String respMsg = json.getString(CommonConstant.RESP_MSG);				
			noticeData.put("respMsg", respMsg);
		}
		this.write(noticeData, "utf-8", response);				
		return;
	}*/

	// TODO 
	public void userDraw(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String,String> paramsMap = this.getParamMap(request);
		String level = paramsMap.get("level").toString();
		SandYearService yService = (SandYearService)SpringUtil.getBean("sandYearService");
		// 0: �ѳ� 1: δ��
		yService.saveCurrentAward(level, "1");
		
		JSONObject noticeData = new JSONObject();
		JSONArray currentAwardUserList = new JSONArray();
		PushMessageRute rute = (PushMessageRute) SpringUtil.getBean("pushMessageRute");
		MessageHander msgHander = rute.rute(paramsMap);
		// 
		currentAwardUserList = msgHander.getMessage();
		
		if(currentAwardUserList != null && !currentAwardUserList.isEmpty()) {
			// �н���Ϣ���浽�ڴ��С�������չ �����浽�ڴ��ͬʱ��Ҳ���浽���ݿ�
			// ���½���״̬�����ݿ�
			yService.saveCurrentAward(level, "0");
			noticeData.put("respCode", "10000");
			noticeData.put("respMsg", "�ɹ�");
			noticeData.put("memArray", currentAwardUserList);
		} else {
			
		}
		
		this.write(noticeData, "utf-8", response);
		return;
		
	}

	public void tobucj(HttpServletRequest request,HttpServletResponse response)
			throws Exception{
		String currentAwards = request.getParameter("currentAwards");
		String buCounts = request.getParameter("buCounts");
		
		request.setAttribute("currentAwards", currentAwards);
		request.getSession().setAttribute("buCounts", buCounts);
		this.forward("/view/bucj.jsp", request, response);	
		return;
	}

	/**
	 * ���齱
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void bucj(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String,String> paramsMap = this.getParamMap(request);
		String level = paramsMap.get("level").toString();
		
		SandYearService yService = (SandYearService)SpringUtil.getBean("sandYearService");
		// 0: �ѳ� 1: δ��
		yService.saveCurrentAward(level, "1");
		
		JSONObject noticeData = new JSONObject();
		JSONArray currentAwardUserList = new JSONArray();
		com.org.temp.BuMessageRute rute = (com.org.temp.BuMessageRute) SpringUtil.getBean("buMessageRute");
		com.org.temp.BuMessageHander msgHander = rute.rute(paramsMap);
		// 
		currentAwardUserList = msgHander.getMessage();
		if(currentAwardUserList != null && !currentAwardUserList.isEmpty()) {
			// �н���Ϣ���浽�ڴ��С�������չ �����浽�ڴ��ͬʱ��Ҳ���浽���ݿ�
			// ���½���״̬�����ݿ�
			yService.saveCurrentAward(level, "0");
			noticeData.put("respCode", "10000");
			noticeData.put("respMsg", "�ɹ�");
			noticeData.put("memArray", currentAwardUserList);
		} else {
			
		}
		
		this.write(noticeData, "utf-8", response);
		return;
		
	}
	
	
	public void toHelp(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		String token = WxUtil.getToken();
		String timestamp = String.valueOf(StringUtil.getTimestamp()); // �������ǩ����ʱ���
		String nonceStr = UUID.randomUUID().toString(); // ���ǩ��������¼1
		String url = request.getRequestURL().toString();
		String signature = WxUtil.localSign(timestamp, nonceStr, url); // �����Ҫʹ�õ�JS�ӿ��б�����JS�ӿ��б����¼2
		String appid = PropertiesUtil.getValue("wx", "appid");
		log.info("toHelp: " + this.getParamMap(request));
		log.info("toHelp: nonceStr:" + nonceStr );
		log.info("toHelp: url:" + url );
		log.info("toHelp: signature:" + signature );
		log.info("toHelp: appid:" + appid );
		
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("nonceStr", nonceStr);
		request.setAttribute("url", url);
		request.setAttribute("signature", signature);
		request.setAttribute("cacheToken", token);
		request.setAttribute("appId", appid);

		request.setAttribute("usermeg", request.getSession().getAttribute("usermeg"));	

		this.forward("/view/help.jsp", request, response);
		return;
	}
	
	public void queryAwardUser(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String moible = request.getParameter("moible");
		SandYearService yService = (SandYearService)SpringUtil.getBean("sandYearService");
		JSONObject res = yService.queryAwardUser(moible);
		if(res.getString(CommonConstant.RESP_CODE).equals("100000")){
			request.setAttribute("aim", res.getJSONObject("aim"));
		}
		
		this.forward("/view/award_query.jsp", request, response);
		return;
	}
	
	public void test(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String aim = request.getParameter("cname");
		String mtd = request.getParameter("mtd");
		String key = request.getParameter("key");
		String value = request.getParameter("value");
		Object obj = ReflectUtil.ref(aim, mtd, key, value);
		JSONObject result = new JSONObject();
		result.put("obj", obj);
		this.write(result, "UTF-8", response);
	}
	
	@Override
	public void post(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}

}
