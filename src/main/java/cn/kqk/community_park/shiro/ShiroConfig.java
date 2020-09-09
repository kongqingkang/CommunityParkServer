package cn.kqk.community_park.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther: cpb
 * @Date: 2018/8/10 11:32
 * @Description:
 */
@Configuration
@Slf4j
public class ShiroConfig {
//    private final ShiroProject shiroProject;
//    @Autowired(required = false)
//    public ShiroConfig(ShiroProject shiroProject) {
//        this.shiroProject = shiroProject;
//    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //如果httyOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
        simpleCookie.setHttpOnly(true);
        //记住我cookie生效时间,默认30天 ,单位秒：60 * 60 * 24 * 30
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    @Bean
    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        // 设置登录过期时间
        mySessionManager.setGlobalSessionTimeout(1000 * 60 * 60 * 24);
        return mySessionManager;
    }

    /**
     * cookie管理器;
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //rememberme cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度（128 256 512 位），通过以下代码可以获取
        //KeyGenerator keygen = KeyGenerator.getInstance("AES");
        //SecretKey deskey = keygen.generateKey();
        //System.out.println(Base64.encodeToString(deskey.getEncoded()));
        //byte[] cipherKey = Base64.decode("wGiHplamyXlVB11UXWol8g==");
        //cookieRememberMeManager.setCipherKey(cipherKey);
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    /**
     * 设置shiro拦截器
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        log.info("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // authc: 所有url都必须认证通过才可以访问;
        // anon:  所有url都都可以匿名访问;
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/img/**","anon");
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/images/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/lib/**","anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/admin/login", "anon");
        filterChainDefinitionMap.put("/admin/index", "anon");
        filterChainDefinitionMap.put("/admin/userIdentify", "anon");
        filterChainDefinitionMap.put("/admin/parkIdentify", "anon");
        filterChainDefinitionMap.put("/admin/userList", "anon");
        filterChainDefinitionMap.put("/admin/parkList", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/getUserReview", "anon");
        filterChainDefinitionMap.put("/getParkReview", "anon");
        filterChainDefinitionMap.put("/getRentInput", "anon");
        filterChainDefinitionMap.put("/getRentOutInput", "anon");
        filterChainDefinitionMap.put("/rentinfo", "anon");
        filterChainDefinitionMap.put("/rentoutinfo", "anon");
        filterChainDefinitionMap.put("/editRentInput", "anon");
        filterChainDefinitionMap.put("/editRentOutInput", "anon");
        filterChainDefinitionMap.put("/about", "anon");
        filterChainDefinitionMap.put("/message", "anon");
        filterChainDefinitionMap.put("/archivesRenout", "anon");
        filterChainDefinitionMap.put("/archivesParkReview", "anon");
        filterChainDefinitionMap.put("/archives", "anon");
        filterChainDefinitionMap.put("/editPassword", "anon");
        filterChainDefinitionMap.put("/reg", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/index", "anon");
        filterChainDefinitionMap.put("/**", "corsAuthenticationFilter");

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.html"页面
        shiroFilterFactoryBean.setLoginUrl("/unLogin");
        // 登录成功后要跳转的链接
        // shiroFilterFactoryBean.setSuccessUrl("/index");
        // 设置未授权的过滤器
        shiroFilterFactoryBean.getFilters().put("perms", new PermFailFilter());
        shiroFilterFactoryBean.getFilters().put("corsAuthenticationFilter", new CORSAuthenticationFilter());

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 核心的安全事务管理器
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 注入缓存管理器
        securityManager.setCacheManager(ehCacheManager());
        // 注入Cookie(记住我)管理器(remenberMeManager)
        securityManager.setRememberMeManager(rememberMeManager());
        //设置Realm，用于获取认证凭证
        securityManager.setRealm(userRealm());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 身份认证Realm，此处的注入不可以缺少。否则会在UserRealm中报空指针
     *
     * @return
     */
    @Bean
    public UserRealm userRealm() {
//        shiroProject.setAuthenticationTokenClass(MyAuthenticationToken.class);
//        shiroProject.setCredentialsMatcher(hashedCredentialsMatcher());
        UserRealm userRealm = new UserRealm();
        userRealm.setAuthenticationTokenClass(AuthenticationToken.class);
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }

    /**
     * 由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * 哈希密码比较器。在ShiroRealm中用作参数使用
     * 登陆时会比较用户输入的密码，跟数据库密码配合盐值salt解密后是否一致。
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // HashedCredentialsMatcher hashedCredentialsMatcher = new RetryLimitCredentialsMatcher(ehCacheManager());
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }

    /**
     * shiro缓存管理器;
     * 需要注入对应的其它的实体类中：
     * 1、安全管理器：securityManager
     * 可见securityManager是整个shiro的核心；
     *
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:org/apache/shiro/cache/ehcache/ehcache.xml");
        return cacheManager;
    }

    /**
     * AuthorizationAttributeSourceAdvisor，shiro里实现的Advisor类，
     * 内部使用AopAllianceAnnotationsAuthorizingMethodInterceptor来拦截用以下注解的方法。
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;否则@RequiresRoles等注解无法生效
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
     * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。
     * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 自动创建代理
     *
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    /**
     * 让shiro在thymeleaf中生效
     * @return
     */
    // @Bean
    // public ShiroDialect shiroDialect() {
    //     return new ShiroDialect();
    // }
}
