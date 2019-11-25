package cn.gmwenterprise.presevere.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TokenHelper {
    private static final Logger log = LoggerFactory.getLogger(TokenHelper.class);

    private static ObjectMapper objectMapper = null;
    private static MACSigner signer = null;
    private static MACVerifier verifier = null;
    private static final String SECRET_KEY = "409939b5c5274e9195d0565d34f4f0f5";
    private static final JWSHeader COMMON_HEADER = new JWSHeader(JWSAlgorithm.HS256);

    static {
        try {
            signer = new MACSigner(SECRET_KEY);
            verifier = new MACVerifier(SECRET_KEY);
        } catch (JOSEException ignored) {
        }
    }

    private static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = BeanHelper.getBean(ObjectMapper.class);
        }
        return objectMapper;
    }

    public static String generateToken(Object payload) {
        log.info("生成token调用");
        try {
            JWSObject jwsObject = new JWSObject(
                COMMON_HEADER,
                new Payload(getObjectMapper().writeValueAsString(payload))
            );
            jwsObject.sign(signer);
            return jwsObject.serialize();
        } catch (Exception e) {
            return null;
        }
    }

    public static <R> R parseToken(String token, Class<R> type) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            if (jwsObject.verify(verifier)) {
                String json = jwsObject.getPayload().toString();
                return getObjectMapper().readValue(json, type);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
