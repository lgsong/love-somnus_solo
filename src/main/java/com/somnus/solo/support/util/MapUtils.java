package com.somnus.solo.support.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class MapUtils {

	public static String map2url(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder();
		if(HashMap.class.isInstance(map)){
			for (Map.Entry<String, ?> entry : map.entrySet()) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				sb.append(String.format("%s=%s", entry.getKey().toString(), entry
						.getValue().toString()));
			}
		}else if(MultiValueMap.class.isInstance(map)){
			for (String key : map.keySet()) {
				List<?> list = (List<?>)map.get(key);
				for(Object data:list){
					if (sb.length() > 0) {
						sb.append("&");
					}
					sb.append(String.format("%s=%s", key, data));
				}
			}
		}
		
		return sb.toString();
	}

	public static MultiValueMap<String,String> url2Map(String url) {
		MultiValueMap<String,String> multiMap = new LinkedMultiValueMap<String,String>();
		if (StringUtils.isBlank(url)) {
			return multiMap;
		}
		String[] params = url.split("&");
		for (int i = 0; i < params.length; i++) {
			String[] p = params[i].split("=");
			if (p.length == 2) {
				multiMap.add(p[0], p[1]);
			}
		}
		return multiMap;
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("number", "1");
		map.put("name", "wang");
		map.put("card", "65432");
		System.out.println(map2url(map));
		
		MultiValueMap<String,String> multiMap = new LinkedMultiValueMap<String,String>();
		multiMap.add("number", "1");
		multiMap.add("number", "30");
		multiMap.add("name", "wang");
		multiMap.add("card", "12344");
		multiMap.add("card", "65432");
		System.out.println(map2url(multiMap));
		
		MultiValueMap<String, String> maps = url2Map("name=wang&card=12344&card=65432&number=1&number=30");
		System.out.println(maps);
	}

}
