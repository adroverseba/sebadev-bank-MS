package com.sebadevbank.usersservice.util;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@Component
public class TokenUtil {
//    @Value("${keycloak.client-secret}")
    private String PUBLIC_KEY_BASE64 ="MIICtTCCAZ0CBgGUtK+WjTANBgkqhkiG9w0BAQsFADAeMRwwGgYDVQQDDBNzcHJpbmdib290LWtleWNsb2FrMB4XDTI1MDEzMDAwNDgxM1oXDTM1MDEzMDAwNDk1M1owHjEcMBoGA1UEAwwTc3ByaW5nYm9vdC1rZXljbG9hazCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMf2nvS6QEbroYjyYRr4XvuWdBu7UFiv76kMHZfaBiQuc2GOsy40npuJ6aKt/Srht+Ww8qq0w2PFDhCDNg2hw0scDbZ3+m13mbgBU2YZZkv6H2M6w9XO+Bm4r9lwnMyWwdgDDn4RPnXPPOuyi4A48FALupC9cwEpai151DJKFR5NPIpyI0JaPG/IC2RB753oi4t1jc9LOXljFt/OU0ZSxU+ywrgVvcpRsW+Kxwzqvqf0qWknSAngqYhTDNNwMEK64AQAOX1nI9qTnctReNKXM7xGdc3AdV77dneIP9EWNg73fNdlqPCZNdEueqoVwoJ97VAZacBDGHOCYPDoGIbUD0kCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAsIVP+msxLSWATo2X8qVrE0GgqCLnPX5RxkmMQmM8kZSWTMBpyMzs198q4Gz3K1uieEfCBJsrLh5O4utOJsCK+iI6Zzsrm6BfjCKDaXoI/csC089u4IG7E8hpLCNegB7Y4JrMmkg5vJtUYToWbUcyI7x0j/ftWMe8oyACEBPjeTJvWFuvnV7gMGmsaaie+Alv1fKfv4LRXy5YxxCulNbAb2AbfQT9w3Y5xYEGaq3M8byjgZrqiqpa1KHDqSyFT+CQG9yJXmbItSZ0vQk5cXZcY7rin7O927ae4dw1SQA9JdYNnXoXPJQkTbDVzrxlWASLPXoHkcNy2d22iL68HW2IIw==";

    public String extractUserIdFromToken(String token) {
        try {
            // 🔹 Convierte la clave Base64 en un certificado X.509
            String pem = "-----BEGIN CERTIFICATE-----\n"
                    + PUBLIC_KEY_BASE64 + "\n-----END CERTIFICATE-----";

            // 🔹 Procesa el certificado para obtener la clave pública
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(
                    new ByteArrayInputStream(pem.getBytes()));

            PublicKey publicKey = certificate.getPublicKey();

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject(); // Devuelve el "sub" del token
        } catch (SignatureException e) {
            System.err.println("❌ Token inválido: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("❌ Token inválido: " + e.getMessage());
            return null;
        }
    }
}


