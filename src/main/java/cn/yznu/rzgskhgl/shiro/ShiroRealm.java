package cn.yznu.rzgskhgl.shiro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.service.IResourceService;
import cn.yznu.rzgskhgl.service.IUserService;

/**
 * shiro验证
 * @author 张伟
 *
 */
public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private IUserService userService;  
	@Autowired
	private IResourceService resourceService;  
	/*
	 * 登录信息和用户验证信息验证(non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		String username = (String)token.getPrincipal();  				//得到用户名 
	    String password = new String((char[])token.getCredentials()); 	//得到密码
		System.out.println(username+"********shiro-------"+password);
	    if(null != username && null != password){
	    	return new SimpleAuthenticationInfo(username, password, getName());
	    }else{
	    	return null;
	    }
	     
	}
	
	/*
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法(non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String username = (String)principals.getPrimaryPrincipal();  
		User user = userService.getUserByName(username);
		List<String> roles = userService.listRoleSnByUser(user);
		List<Resource> reses = resourceService.listResourceByUser(user);
		List<String> permissions = new ArrayList<String>();
		for(Resource r:reses) {
			permissions.add(r.getUrl());
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(new HashSet<String>(roles));
		info.setStringPermissions(new HashSet<String>(permissions));
		return info;
	}

}
