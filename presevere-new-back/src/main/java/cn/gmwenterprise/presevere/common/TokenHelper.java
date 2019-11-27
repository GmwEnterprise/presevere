package cn.gmwenterprise.presevere.common;

import cn.gmwenterprise.presevere.config.security.Authentication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

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
            log.error(e.getMessage());
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
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 测试一下jose-jwt的比较通用的功能。
     * 实际上我写的Authetication已经实现了这个功能，没必要修改
     *
     * @param args args
     */
    public static void main(String[] args) {
        Authentication authentication = new Authentication() {{
            setUserId(100);
            setLoginIp("127.0.0.1");
            setLoginDatetime(LocalDateTime.now());
            setTimeout(Constants.TIMEOUT_ON);
            setPlatform(Platform.BROWSER);
        }};
        try {
            Objects.requireNonNull(authentication.getUserId());
            log.info("传入userId: {}, 生成token中...", authentication.getUserId());

            // RSA signatures require a public and private RSA key pair, the public key
            // must be made known to the JWS recipient in order to verify the signatures
            RSAKey rsaJWK = new RSAKeyGenerator(2048)
                .keyID("123")
                .generate();
            RSAKey rsaPublicJWK = rsaJWK.toPublicJWK();

            // Create RSA-signer with the private key
            JWSSigner signer = new RSASSASigner(rsaJWK);

            // Prepare JWT with claims set
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder()
                .claim("userId", authentication.getUserId())
                .claim("ip", authentication.getLoginIp())
                .claim("platform", authentication.getPlatform())
                .claim("timeout", authentication.getTimeout())
                .issuer("http://gmwenterprise.github.io/")
                .issueTime(new Date());
            JWTClaimsSet claimsSet;
            if (authentication.timeout()) {
                long expirationMills = 500L; // System.currentTimeMillis() + Constants.DEFAULT_TOKEN_TIMEOUT * 60 * 1000;
                claimsSet = builder.expirationTime(new Date(expirationMills)).build();
            } else {
                claimsSet = builder.build();
            }

            SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaJWK.getKeyID()).build(),
                claimsSet
            );

            // Compute the RSA signature
            signedJWT.sign(signer);

            Thread.sleep(1000);

            String token = signedJWT.serialize();

            // On the consumer side, parse the JWS and verify its RSA signature
            signedJWT = SignedJWT.parse(token);

            JWSVerifier verifier = new RSASSAVerifier(rsaPublicJWK);
            assert signedJWT.verify(verifier);

            assert authentication.getUserId().equals(signedJWT.getJWTClaimsSet().getClaim("userId"));
            assert "http://gmwenterprise.github.io/".equals(signedJWT.getJWTClaimsSet().getIssuer());

            System.out.println(signedJWT.getJWTClaimsSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
