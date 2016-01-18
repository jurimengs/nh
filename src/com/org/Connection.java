package com.org;

import net.sf.json.JSONObject;


public interface Connection<T> {
	public String getId();

	public JSONObject executeQuery(JSONObject requestJson);

	/**
	 * 返回对应数据库的实际连接对象
	 * @return
	 */
	public T getRealConnection();

	public void close(T obj);
}
