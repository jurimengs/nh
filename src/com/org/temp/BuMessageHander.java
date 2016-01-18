package com.org.temp;

import net.sf.json.JSONArray;

/**
 * 实现本接口的类，如果有参数要处理的话，可以添加有参构造方法，参数类型为Map<String,String>
 * @author Administrator
 *
 */
public interface BuMessageHander {
	/**
	 * 返回中奖信息。这里只管生成随机号码，其他不用考虑
	 * @return
	 */
	public JSONArray getMessage();
}
