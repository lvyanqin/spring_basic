package lynn.util.auth;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lynn.util.enums.FailureEnum;
import lynn.util.result.Result;
import lynn.util.result.ResultUtil;

import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;

public class UserTokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(UserTokenUtil.class);
    /**
     * token key
     */
    public static final String TOKEN_KEY = "ajflkdjslfjalfjafafldsjfld";
    public static final String TOKEN_HEADER_NAME = "Authorization";
    public static final String ACCOUNT_HEADER_NAME = "ACCOUNT_ID";
    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private static final String HMAC_256 = "HmacSHA256";
    private static final String KEY_ROLE_IDS = "roleIds";// 用户角色id集
    private static final String KEY_USERNAME = "username";// 用户名
    private static final String KEY_CLIENT_TYPE = "client";// 客户端类型
    private static final String KEY_SYSTEM_VERSION = "sysVersion";// 系统类型
    private static final String KEY_APP_VERSION = "appVersion";// 系统类型
    private static final String KEY_ACCOUNT_ID = "accountId";// 用户ID
    private static final String KEY_EXPIRE_TIME = "expireTime";// 过期时间
    private static final String KEY_PROJECT_ID = "projectId";// 所属项目
    private static final String KEY_DEPARTMENT_ID = "departmentId";// 部门ID
    private static final String KEY_OPEN_ID = "openId";// OPENID
    private static final String KEY_UNIONID = "unionId";// OPENID
    private static final String KEY_CLAIMS = "claims";
    private static final String KEY_V = "v";
    public static final String ISSUER = "YYC_USER";
    public static final int APP_TOKEN_EXPIRE_SECOND = 60 * 60 * 24 * 7;

    private static SecretKeySpec key = new SecretKeySpec(TOKEN_KEY.getBytes(UTF8_CHARSET), HMAC_256);

    /**
     *
     */
    public static String generateToken(long accountId, String username, Integer client, String sysVersion,
                                       String appVersion, String roleIds, Integer departmentId, Integer projectId, Date expireTime, String clientType) {
        String token = "";

        try {
            Map<String, String> authPayload = new HashMap<String, String>();
            token = Jwts.builder()
                    .setIssuer(ISSUER)
                    .claim(KEY_V, 1)
                    .claim(KEY_ACCOUNT_ID, accountId)
                    .claim(KEY_APP_VERSION, appVersion)
                    .claim(KEY_SYSTEM_VERSION, sysVersion)
                    .claim(KEY_USERNAME, username)
                    .claim(KEY_EXPIRE_TIME, expireTime.getTime())
                    .claim(KEY_ROLE_IDS, roleIds)
                    .claim(KEY_DEPARTMENT_ID, departmentId)
                    .claim(KEY_PROJECT_ID, projectId)
                    .claim(KEY_CLIENT_TYPE, client)
                    .claim("clientType",clientType)
                    .setIssuedAt(new Date())
                    .claim(KEY_CLAIMS, authPayload)
                    .setExpiration(expireTime)
                    .signWith(SignatureAlgorithm.HS256, key).compact();

        } catch (Exception e) {
            logger.warn("generateToken:", e);
        }

        return token;
    }

    public static Date getExpiryDate(int second) {
        // 根据当前日期，来得到到期日期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 解析token
     *
     * @param token token字符串
     */
    public static Result parseToken(String token) {
        Map map = new HashMap();
        try {
            if (StrUtil.isBlank(token)) {
                logger.warn("从request中获取不到token");
                return ResultUtil.failure(FailureEnum.PARAMETER_FAILURE);
            }
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            Long accountId = claims.get(KEY_ACCOUNT_ID) == null ? null : Long.valueOf(claims.get(KEY_ACCOUNT_ID) + "");
            String roleIds = claims.get(KEY_ROLE_IDS) == null ? "" : (String) claims.get(KEY_ROLE_IDS);
            Long expireTime = claims.get(KEY_EXPIRE_TIME) == null ? null : (Long) claims.get(KEY_EXPIRE_TIME);
            Integer client = claims.get(KEY_CLIENT_TYPE) == null ? 1 : (Integer) claims.get(KEY_CLIENT_TYPE);
            String sysVersion = claims.get(KEY_SYSTEM_VERSION) == null ? "" : (String) claims.get(KEY_SYSTEM_VERSION);
            String appVersion = claims.get(KEY_APP_VERSION) == null ? "" : (String) claims.get(KEY_APP_VERSION);
            String username = claims.get(KEY_USERNAME) == null ? "" : (String) claims.get(KEY_USERNAME);
            Long departmentId = claims.get(KEY_PROJECT_ID) == null ? null : Long.valueOf(claims.get(KEY_PROJECT_ID).toString());
            Long projectId = claims.get(KEY_DEPARTMENT_ID) == null ? null : Long.valueOf(claims.get(KEY_DEPARTMENT_ID).toString());
            String openId = claims.get(KEY_OPEN_ID) == null ? null : (String) claims.get(KEY_OPEN_ID);
            String unionId = claims.get(KEY_UNIONID) == null ? null : (String) claims.get(KEY_UNIONID);
            String clientType = claims.get("clientType") == null ? null : (String) claims.get("clientType");


            map.put("accountId", accountId);
            map.put("roleIds", roleIds);
            map.put("expireTime", expireTime);
            map.put("client", client);
            map.put("sysVersion", sysVersion);
            map.put("appVersion", appVersion);
            map.put("username", username);
            map.put("departmentId", departmentId);
            map.put("projectId", projectId);
            map.put("openId", openId);
            map.put("unionId", unionId);
            map.put("clientType", clientType);
        } catch (ExpiredJwtException e) {
            logger.warn("token 已过期");
            return ResultUtil.failure(FailureEnum.TOKEN_EXPIRED_FAILURE);
        } catch (SignatureException e) {
            logger.warn("token 签名错误");
            // 在解析JWT字符串时，如果密钥不正确，将会解析失败，抛出SignatureException异常，说明该JWT字符串是伪造的
            return ResultUtil.failure(FailureEnum.TOKEN_PARSE_FAILURE);
        } catch (Exception e) {
            logger.warn("token 解析未知异常");
            return ResultUtil.failure(FailureEnum.TOKEN_PARSE_EXCEPTION);
        }
        return ResultUtil.success(map);
    }

    public static String wechatUserToken(String authorization) {
        String token = authorization.substring(6);
        if (StringUtils.isEmpty(authorization) || authorization.length() <= 6 ) {
            Result result = ResultUtil.failure(FailureEnum.TOKEN_WITHOUT_FAILURE);
            return "";
        }
        byte[] decode = Base64.getDecoder().decode(token);
        try {
            token = new String(decode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.warn("wechatuser token 解析失败！");
            return "";
        }
        token = token.substring(0, token.length() - 1);
        return token;
    }
    
    public static class TokenBuilder {
    	private final Long accountId;
        private final String roleIds;
        private final Date expireTime;
        private final Integer client;
        private final String sysVersion;
        private final String appVersion;
        private final String username;
        private final Integer departmentId;
        private final Integer projectId;

        private final String openId;
        private final String unionId;

        public TokenBuilder() {
        	this.accountId = null;
        	this.roleIds = null;
        	this.expireTime = null;
        	this.client = null;
        	this.sysVersion = null;
        	this.appVersion = null;
        	this.username = null;
        	this.departmentId = null;
        	this.projectId = null;
        	this.openId = null;
        	this.unionId = null;
        }
        
        public TokenBuilder(Long accountId, String roleIds, Date expireTime, Integer client,
        		String sysVersion, String appVersion, String username, Integer departmentId, Integer projectId, String openId, String unionId) {
        	this.accountId = accountId;
        	this.roleIds = roleIds;
        	this.expireTime = expireTime;
        	this.client = client;
        	this.sysVersion = sysVersion;
        	this.appVersion = appVersion;
        	this.username = username;
        	this.departmentId = departmentId;
        	this.projectId = projectId;
            this.openId = openId;
            this.unionId = unionId;
        }
        
        public TokenBuilder accountId(Long accountId) {
        	return new TokenBuilder(accountId, this.roleIds, this.expireTime, this.client, this.sysVersion, this.appVersion, this.username, this.departmentId, this.projectId, this.openId, this.unionId);
        }
        
        public TokenBuilder roleIds(String roleIds) {
        	return new TokenBuilder(this.accountId, roleIds, this.expireTime, this.client, this.sysVersion, this.appVersion, this.username, this.departmentId, this.projectId, this.openId, this.unionId);
        }
        
        public TokenBuilder expireTime(Date expireTime) {
        	return new TokenBuilder(this.accountId, this.roleIds, expireTime, this.client, this.sysVersion, this.appVersion, this.username, this.departmentId, this.projectId, this.openId, this.unionId);
        }
        
        public TokenBuilder client(Integer client) {
        	return new TokenBuilder(this.accountId, this.roleIds, this.expireTime, client, this.sysVersion, this.appVersion, this.username, this.departmentId, this.projectId, this.openId, this.unionId);
        }
        
        public TokenBuilder sysVersion(String sysVersion) {
        	return new TokenBuilder(this.accountId, this.roleIds, this.expireTime, this.client, sysVersion, this.appVersion, this.username, this.departmentId, this.projectId, this.openId, this.unionId);
        }
        
        public TokenBuilder appVersion(String appVersion) {
        	return new TokenBuilder(this.accountId, this.roleIds, this.expireTime, this.client, this.sysVersion, appVersion, this.username, this.departmentId, this.projectId, this.openId, this.unionId);
        }
        
        public TokenBuilder username(String username) {
        	return new TokenBuilder(this.accountId, this.roleIds, this.expireTime, this.client, this.sysVersion, this.appVersion, username, this.departmentId, this.projectId, this.openId, this.unionId);
        }
        
        public TokenBuilder departmentId(Integer departmentId) {
        	return new TokenBuilder(this.accountId, this.roleIds, this.expireTime, this.client, this.sysVersion, this.appVersion, this.username, departmentId, this.projectId, this.openId, this.unionId);
        }
        
        public TokenBuilder projectId(Integer projectId) {
        	return new TokenBuilder(this.accountId, this.roleIds, this.expireTime, this.client, this.sysVersion, this.appVersion, username, this.departmentId, projectId, this.openId, this.unionId);
        }

        public TokenBuilder openId(String openId) {
            return new TokenBuilder(this.accountId, this.roleIds, this.expireTime, this.client, this.sysVersion, this.appVersion, username, this.departmentId, projectId, openId, this.unionId);
        }

        public TokenBuilder unionId(String unionId) {
            return new TokenBuilder(this.accountId, this.roleIds, this.expireTime, this.client, this.sysVersion, this.appVersion, username, this.departmentId, this.projectId, this.openId, unionId);
        }
        
        public String build() {
        	String token = "";

            try {
                Map<String, String> authPayload = new HashMap<String, String>();
                token = Jwts.builder()
                        .setIssuer(ISSUER)
                        .claim(KEY_V, 1)
                        .claim(KEY_ACCOUNT_ID, this.accountId)
                        .claim(KEY_APP_VERSION, this.appVersion)
                        .claim(KEY_SYSTEM_VERSION, this.sysVersion)
                        .claim(KEY_USERNAME, this.username)
                        .claim(KEY_EXPIRE_TIME, this.expireTime.getTime())
                        .claim(KEY_ROLE_IDS, this.roleIds)
                        .claim(KEY_DEPARTMENT_ID, this.departmentId)
                        .claim(KEY_PROJECT_ID, this.projectId)
                        .claim(KEY_CLIENT_TYPE, this.client)
                        .claim(KEY_OPEN_ID, this.openId)
                        .claim(KEY_UNIONID, this.unionId)
                        .setIssuedAt(new Date())
                        .claim(KEY_CLAIMS, authPayload)
                        .setExpiration(this.expireTime)
                        .signWith(SignatureAlgorithm.HS256, key).compact();

            } catch (Exception e) {
                logger.warn("generateToken:", e);
            }

            return token;
        }
    }

}
