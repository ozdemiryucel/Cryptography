package algorithms;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

public class DSA {

    public static Map<String, BigInteger> getXY(Map<String, BigInteger> QPG) {
        Map<String, BigInteger> XY = new HashMap<>();
        BigInteger x = FermatLittleTheorem.getRandomBigInteger(QPG.get("Q"));
        BigInteger y = FastModularExponentiation.getMod(QPG.get("G"), x, QPG.get("P"));

        XY.put("X", x);
        XY.put("Y", y);

        return XY;
    }

    public static Map<String, BigInteger> getQPG() {
        Map<String, BigInteger> QPG = new HashMap<>();
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance("DSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        KeyPair keypair = null;
        if (keyGen != null) {
            keyGen.initialize(1024);
            keypair = keyGen.genKeyPair();
        }

        DSAPrivateKey privateKey = null;
        if (keypair != null) {
            privateKey = (DSAPrivateKey) keypair.getPrivate();
        }

        DSAParams dsaParams = null;
        if (privateKey != null) {
            dsaParams = privateKey.getParams();
        }
        if (dsaParams != null) {
            QPG.put("Q", dsaParams.getQ());
            QPG.put("P", dsaParams.getP());
            QPG.put("G", dsaParams.getG());
        }

        return QPG;

    }

    public static boolean isSignatureVerified(String M, Map<String, BigInteger> RS, Map<String, BigInteger> otherDSAPublicComponents) {

        BigInteger q = otherDSAPublicComponents.get("Q");
        BigInteger g = otherDSAPublicComponents.get("G");
        BigInteger p = otherDSAPublicComponents.get("P");
        BigInteger y = otherDSAPublicComponents.get("Y");

        BigInteger r = RS.get("R");
        BigInteger s = RS.get("S");

        BigInteger w = ExtendedEuclideanAlgorithm.getMultiplicativeInverse(s,q);
        BigInteger u1 = SHA3(M).multiply(w).mod(q);
        BigInteger u2 = r.multiply(w).mod(q);

        BigInteger v = (FastModularExponentiation.getMod(g,u1,p)
                .multiply(FastModularExponentiation.getMod(y,u2,p))
                .mod(p))
                .mod(q);

        return v.equals(r);

    }

    public static Map<String, BigInteger> getRandS(String M, Map<String, BigInteger> DSAcomponents, BigInteger x) {

        Map<String, BigInteger> RS = new HashMap<>();

        BigInteger q = DSAcomponents.get("Q");
        BigInteger g = DSAcomponents.get("G");
        BigInteger p = DSAcomponents.get("P");

        BigInteger r;
        BigInteger s;
        do {
            BigInteger k = FermatLittleTheorem.getRandomBigInteger(q);
            r = FastModularExponentiation.getMod(g, k, p).remainder(q);
            s = ExtendedEuclideanAlgorithm.getMultiplicativeInverse(k,q).multiply(SHA3(M).add(x.multiply(r))).mod(q);
        } while (s.equals(BigInteger.ZERO));

        RS.put("R", r);
        RS.put("S", s);

        return RS;
    }

    private static BigInteger SHA3(String message) {

        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance("SHA3-256");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        byte[] hashbytes = null;
        if (digest != null) {
            hashbytes = digest.digest(message.getBytes(StandardCharsets.UTF_8));
        }

        return new BigInteger(hashbytes != null ? hashbytes : new byte[0]);

    }

}
