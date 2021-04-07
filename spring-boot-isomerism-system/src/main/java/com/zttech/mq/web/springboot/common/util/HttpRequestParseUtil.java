package com.zttech.mq.web.springboot.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryAttribute;

public class HttpRequestParseUtil {

	public static Map<String, String> parse(FullHttpRequest req) throws IOException {
		Map<String, String> parmMap = new HashMap<>();
		if(req.method().equals(HttpMethod.GET)) {
			Map<String, String> params = new HashMap<>();
			QueryStringDecoder decoder = new QueryStringDecoder(req.uri());
			decoder.parameters().entrySet().forEach(entry -> {
				params.put(entry.getKey(), entry.getValue().get(0));
			});
			parmMap = params;
		} else if(req.method().equals(HttpMethod.POST)) {
//			ByteBuf content = req.content();
//			StringBuilder buf = new StringBuilder(); 
//			buf.append(content.toString(CharsetUtil.UTF_8)); 
//			String paraStr = buf.toString();
//			
//			if(!paraStr.equals("")) {
//				for(String item : paraStr.split("&")) {
//					String[] split = item.split("=");
//					parmMap.put(split[0], split[1]);
//				}
//			}
//			
			parmMap = getRequestParams(req);
		}
		
		return parmMap;
	}
	
	private static Map<String, String> getRequestParams(FullHttpRequest request) {
	    HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), request);
	    List<InterfaceHttpData> httpPostData = decoder.getBodyHttpDatas();
	    Map<String, String> params = new HashMap<>();

	    for (InterfaceHttpData data : httpPostData) {
	        if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
	            MemoryAttribute attribute = (MemoryAttribute) data;
	            params.put(attribute.getName(), attribute.getValue());
	        }
	    }
	    return params;
	}
}
