package algorithms;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSA {

//    private static BigInteger e = null;
//    private static BigInteger d = null;
//    private static BigInteger N = null;
//
//    private static void init() {
//        BigInteger p = Generator.getRandomNBitBigInteger(64, true);
//        BigInteger q = Generator.getRandomNBitBigInteger(64, true);
//        N = p.multiply(q);
//
//        BigInteger T = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
//
//        while (true) {
//            e = Generator.getRandomNBitBigInteger(64, false);
//            if (ExtendedEuclideanAlgorithm.areRelativelyPrime(e, T))
//                break;
//        }
//
//        d = ExtendedEuclideanAlgorithm.getMultiplicativeInverse(e, T);
//    }

    public static List<BigInteger> encrypt(String textOfPages, BigInteger[] publicKeyOfReceiver) {

//        if (e == null || d == null || N == null) {
//            init();
//        }

        BigInteger N = publicKeyOfReceiver[0];
        BigInteger e = publicKeyOfReceiver[1];

        List<BigInteger> asciiList = new ArrayList<>();
        char[] charArray = textOfPages.toCharArray();

        for (char c : charArray) {
            asciiList.add(FastModularExponentiation.getMod(BigInteger.valueOf(c), e, N));
        }

        return asciiList;

//        char[] cList = new char[asciiList.size()];
//        for (int i = 0; i < asciiList.size(); i++) {
//            cList[i] = (char) FastModularExponentiation.getMod(asciiList.get(i), e, N).intValue();
//            System.out.println(cList[i]);
//        }
//
//        return new String(cList);


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
