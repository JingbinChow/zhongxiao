package com.fh.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fh.entity.system.Menu;
import com.fh.entity.system.User;
import com.fh.util.Const;
/**
 * 
* 类名称：LoginHandlerInterceptor.java
* 类描述： 
* @author FH
* 作者单位： 
* 联系方式：
* 创建时间：2014年11月28日
* @version 1.6
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		if(path.matches(Const.NO_INTERCEPTOR_PATH)){
			return true;
		}else{
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			if(user!=null){
				
				//判断是否拥有当前点击菜单的权限（内部过滤,防止通过url进入跳过菜单权限）
				/**
				 * 根据点击的菜单的xxx.do去菜单中的URL去匹配，当匹配到了此菜单，判断是否有此菜单的权限，没有的话跳转到登录页面
				 */
				Boolean b = true;
				List<Menu> menuList = (List)session.getAttribute(Const.SESSION_allmenuList); //获取菜单列表
				path = path.substring(1, path.length());
				for(int i=0;i<menuList.size();i++){
					for(int j=0;j<menuList.get(i).getSubMenu().size();j++){
						if(menuList.get(i).getSubMenu().get(j).getMENU_URL().split(".do")[0].equals(path.split(".do")[0])){
							if(!menuList.get(i).getSubMenu().get(j).isHasMenu()){
								response.sendRedirect(request.getContextPath() + Const.LOGIN);
								return false;
							}
						}
					}
				}
				return true;
			}else{
				//登陆过滤
				response.sendRedirect(request.getContextPath() + Const.LOGIN);
				return false;		
				//return true;
			}
		}
	}
	
}
