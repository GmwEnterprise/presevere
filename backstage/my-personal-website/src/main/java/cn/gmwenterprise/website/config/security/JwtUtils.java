package cn.gmwenterprise.website.config.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

import java.util.Map;

@Slf4j
public final class JwtUtils {
    private static final byte[] SECRET = "409939b5c5274e9195d0565d34f4f0f5".getBytes();
    /**
     * 过期时间
     */
    private static final String KEY_EXPIRATION_TIME = "exp";

    public static String createToken(Map<String, Object> payloadMap) {
        return createTokenWithExpire(payloadMap, 0L);
    }

    public static String createTokenWithExpire(Map<String, Object> payloadMap, long expire) {
        payloadMap.put(KEY_EXPIRATION_TIME, expire);
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        Payload payload = new Payload(new JSONObject(payloadMap));
        JWSObject jwsObject = new JWSObject(header, payload);
        JWSSigner signer;
        try {
            signer = new MACSigner(SECRET);
            jwsObject.sign(signer);
            return jwsObject.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
            throw new RuntimeException("不应该发生的token生成错误");
        }
    }

    public static Map<String, Object> parseToken(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            Payload payload = jwsObject.getPayload();
            JWSVerifier jwsVerifier = new MACVerifier(SECRET);
            if (jwsObject.verify(jwsVerifier)) {
                JSONObject jsonObject = payload.toJSONObject();
                if (jsonObject.containsKey(KEY_EXPIRATION_TIME)) {
                    Long expire = (Long) jsonObject.get(KEY_EXPIRATION_TIME);
                    if (expire != 0L && System.currentTimeMillis() >= expire) {
                        return null;
                    }
                }
                return jsonObject;
            }
            return null;
        } catch (Exception e) {
            log.error("token解析失败! token:[{}]", token);
            return null;
        }
    }
}
