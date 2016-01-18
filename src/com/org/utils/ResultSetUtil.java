package com.org.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.org.model.reflect.ReflectDbModel;


public class ResultSetUtil {

	public static <T> List<T> parseResultSet(ResultSet rs, T entity)
			throws SQLException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		List<T> list = new ArrayList<T>();
		ResultSetMetaData rsmd = rs.getMetaData();
		// 列数
		int columnCounts = rsmd.getColumnCount();
		//
		ReflectDbModel model = new ReflectDbModel();
		Method m = null;
		while (rs.next()) {
			// 这个地方相当于每用一次就new一次,否则数据会覆盖上一次的数据
			for (int i = 1; i <= columnCounts; i++) {
				initReflectDbModel(rs, model, i);
				if (model.getValue() != null && model.getValue() != "") {
					try {
						m = entity.getClass().getDeclaredMethod("set" + model.getKey(),
								model.getValue().getClass());
						m.invoke(entity, model.getValue());
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						System.out.println(e.getMessage() + ": NoSuchMethodException");
					}
				}
			}
			list.add(entity);
		}
		return list;
	}

	/**
	 * 将表字段名以驼峰命名法命名
	 * @param fieldName
	 * @param toUpper true 转, false 不转驼峰
	 * @return
	 */
	public static JSONArray parseResultSetToJSONArray(ResultSet rs, boolean collumToUpper)
			throws SQLException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		JSONArray list = new JSONArray();
		ResultSetMetaData rsmd = rs.getMetaData();
		// 列数
		int columnCounts = rsmd.getColumnCount();
		//
		JSONObject jo = null;
		while (rs.next()) {
			jo = new JSONObject();
			for (int i = 1; i <= columnCounts; i++) {
				String key = rsmd.getColumnName(i);
				//  转实例名
				key = StringUtil.toEntityName(rsmd.getColumnName(i), collumToUpper);
				Object value = rs.getObject(i);
				value = (value == null) ? "" : value.toString();
				jo.put(key, value);
			}
			list.add(jo);
		}
		return list;
	}
	
	public static void initReflectDbModel(ResultSet rs, ReflectDbModel model,
			int index) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		String key = rsmd.getColumnName(index);
		// 转实例名
		key = StringUtil.toEntityName(rsmd.getColumnName(index), false);
		
		String paramType = rsmd.getColumnClassName(index);
		Object value = rs.getObject(index);

		// 首字母大写
		key = String.valueOf(key.charAt(0)).toUpperCase() + key.substring(1);
		paramType = paramType.substring(paramType.lastIndexOf(".") + 1);

		model.setKey(key);
		model.setParamType(paramType);
		model.setValue(value);
	}
	
	public static void setStatmentParams(PreparedStatement ps, Map<Integer, Object> params) throws SQLException{
		for (Iterator<Integer> iterator = params.keySet().iterator(); iterator
				.hasNext();) {
			Integer key = Integer.valueOf(iterator.next().toString());
			ps.setObject(key, params.get(key));
		}
	}
}
