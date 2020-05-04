package utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    /*盐，秘钥*/
    private String key= "itcast";
    /*过期时间的时长，这里推荐一个小时*/
    private long ttl = 3600000;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    /*
     * 1  生成JWT
     */
    public String createJWT(String id, String subject, String roles) {
        System.out.println(this.key);
        /*当前时间*/
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        /*构建JwtBuilder*/
        JwtBuilder builder = Jwts.builder()
                /*设置用户的id*/
                .setId(id)
                /*设置用户名*/
                .setSubject(subject)
                /*设置签发时间*/
                .setIssuedAt(now)
                /*1. 签名方式  2.盐 */
                .signWith(SignatureAlgorithm.HS256, key).claim("roles", roles);
        /*有过期时间*/
        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }

    /*
     *  2 解析JWT
     */
    public Claims parseJWT(String jwtStr) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }
}
