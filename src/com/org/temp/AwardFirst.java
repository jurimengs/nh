package com.org.temp;

import java.util.Map;

import net.sf.json.JSONArray;

import com.org.common.CommonConstant;

/**
 * 一等奖
 */
public class AwardFirst extends ParentAward implements BuMessageHander{
	private Map<String,String> paramsMap = null;
	public AwardFirst(){}
	public AwardFirst(Map<String,String> paramsMap){
		this.paramsMap = paramsMap;
	}
	
	@Override
	public JSONArray getMessage() {
		String level = paramsMap.get("level");

		// 为补抽奖做的兼容。
		String buCounts = paramsMap.get("buCounts");
		int awardCount = Integer.valueOf(buCounts);
		
		return bucj(CommonConstant.FIRST_USERLIST, level, awardCount);
	}
}
