package com.cn.jm.core.tool;

import java.util.Iterator;
import java.util.Map;

public class ToolTable {
	
	public static StringBuffer param(Map<String, Object> param) {
		StringBuffer sb = new StringBuffer();
		if(param != null && !param.isEmpty()){
			Iterator it = param.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object value = param.get(key);
				if(sb.length() > 0){
					sb.append(" && ").append(key).append(" = '").append(value).append("'");
				}else{
					sb.append(" where ").append(key).append(" = '").append(value).append("'");
				}
			}
		}
		return sb;
	}
	
	public static StringBuffer paramArray(Map<String, Object> param) {
		StringBuffer sb = new StringBuffer();
		if(param != null && !param.isEmpty()){
			Iterator it = param.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object value = param.get(key);
				if(value.getClass().isArray()){
					if(sb.length() > 0){
						sb.append(" && (");
					}else{
						sb.append(" where (");
					}
					if (value instanceof String[]){
						String [] arr = (String[]) value;
						for(int i = 0; i< arr.length; i++){
							if(i != 0){
								sb.append(" or ").append(key).append(" = '").append(arr[i]).append("'");
							}else{
								sb.append(key).append(" = '").append(arr[i]).append("'");
							}
						}
					}else if(value instanceof int[]){
						int [] arr = (int[]) value;
						for(int i = 0; i< arr.length; i++){
							if(i != 0){
								sb.append(" or ").append(key).append(" = ").append(arr[i]);
							}else{
								sb.append(key).append(" = ").append(arr[i]);
							}
						}
					}
					sb.append(" )");
				}else{
					if(sb.length() > 0){
						sb.append(" && ").append(key).append(" = '").append(value).append("'");
					}else{
						sb.append(" where ").append(key).append(" = '").append(value).append("'");
					}
				}
			}
		}
		return sb;
	}
}
