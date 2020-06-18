package com.zsq.SpringBootDemo.config.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zsq.SpringBootDemo.modules.account.entity.Resource;
import com.zsq.SpringBootDemo.modules.account.entity.Role;
import com.zsq.SpringBootDemo.modules.account.entity.User;
import com.zsq.SpringBootDemo.modules.account.service.ResourceService;
import com.zsq.SpringBootDemo.modules.account.service.RoleService;
import com.zsq.SpringBootDemo.modules.account.service.UserService;

/**
 * realm身份验证器
 * @author ZengShiQi
 *
 */
@Component
public class MyRealm extends AuthorizingRealm{

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * 拿到每个用户的角色信息
	 */
	
	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		
		//获取用户的用户名
		String userName = (String) principals.getPrimaryPrincipal();
		User user = userService.getUserByUserName(userName);
		if(user == null) {
			throw new UnknownAccountException("This user name do not exist");
		}
		
		List<Role> roles = roleService.getRolesByUserId(user.getUserId());
		//遍历角色的身份信息
		for (Role role : roles) {
			//添加的是role的name,添加到身份认证器中
			simpleAuthorizationInfo.addRole(role.getRoleName());
			List<Resource> resources = resourceService.getResourcesByRoleId(role.getRoleId());
			//资源验证,通过role_resource表里面对应的roleid查出每个角色所包含的资源，用来对controller层上限制的方法来进行匹配
			for (Resource resource : resources) {
				simpleAuthorizationInfo.addStringPermission(resource.getPermission());
			}
		}
		return simpleAuthorizationInfo;
	}

	/**
	 * 登录页面输入用户名和密码----控制层调用login方法-----service层的login方法
	 * 
	 * service层先获取subject组件，包装令牌，调用subject的login方法，
	 * 
	 * 将包装好的令牌（token）传入myrealm ----获取用户名查询数据库------将令牌和身份验证器对比
	 * 
	 * 成功则继续执行，失败则抛出异常信息
	 */
	
	//身份验证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName = (String) token.getPrincipal();
		User user = userService.getUserByUserName(userName);
		if(user == null) {
			throw new UnknownAccountException("This user name do not exist");
		}
		//身份验证器
		return new SimpleAuthenticationInfo(user.getUserName(),user.getPassword(),getName());
	}

}
