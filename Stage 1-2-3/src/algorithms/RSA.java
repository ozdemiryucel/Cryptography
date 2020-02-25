package algorithms;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSA {

    public static List<BigInteger> encrypt(String textOfPages, BigInteger[] publicKeyOfReceiver) {

        BigInteger N = publicKeyOfReceiver[0];
        BigInteger e = publicKeyOfReceiver[1];

        List<BigInteger> asciiList = new ArrayList<>();
        char[] charArray = textOfPages.toCharArray();

        for (char c : charArray) {
            asciiList.add(FastModularExponentiation.getMod(BigInteger.valueOf(c), e, N));
        }

        return asciiList;

    }

    public static String decrypt(List<BigInteger> textOfPages, BigInteger[] privateKeyOfReceiver) {

        BigInteger N = privateKeyOfReceiver[0];
        BigInteger d = privateKeyOfReceiver[1];

        char[] cList = new char[textOfPages.size()];
        for (int i = 0; i < textOfPages.size(); i++) {
            cList[i] = (char) FastModularExponentiation.getMod(textOfPages.get(i), d, N).intValue();
        }

        return new String(cList);
    }
}
