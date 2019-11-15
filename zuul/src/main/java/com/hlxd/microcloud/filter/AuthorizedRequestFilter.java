package com.hlxd.microcloud.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hlxd.microcloud.entity.UserInfo;
import com.hlxd.microcloud.util.JedisPoolUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


//@Component
public class AuthorizedRequestFilter extends ZuulFilter {

	private List<String> whitelist = new ArrayList<String>();
	private List<String> regexlist = new ArrayList<String>();
	private static final String _JSON_CONTENT = "application/json; charset=UTF-8";
	private static final String _HTML_CONTENT = "text/html; charset=UTF-8";
	private static final String _403_JSON = "{'code': '403', 'msg': '访问被拒绝，客户端未授权！'}";
	private static final String _403_HTML = "<html><body><div style='text-align:center'><h1 style='margin-top: 10px;'>403 Forbidden!</h1><hr><span>@lichmama</span></div></body></html>";



	@Override
	public Object run() {
		// TODO Auto-generated method stub
		HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		RequestContext currentContext= RequestContext.getCurrentContext();
		String Authorization = request.getHeader("Authorization");
		String url = request.getRequestURI();

		//Object Authorizations = jedis.get("auth:"+Authorization);
		//jedis.get()

		/*String auth = "zdmin:zdmin";
		byte[] encodeauth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
		String authHeaders = "Basic "+ new String(encodeauth);*/
		//currentContext.addZuulRequestHeader("Authorization", request.getHeader("Authorization"));
		return request;
	}

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub	
		return true;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub 
		return 0; //数字越大权限等级越低
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		//1.pre: 在请求发出之前执行过滤
		//2.route: 在进行路由请求的时候执行过滤
		//3.post: 在路由发送请求信息时执行过滤
		//3.error: 在请求失败时执行过滤
		return "pre";
	}

}
