package algorithms;

import java.math.BigInteger;
import java.util.Random;

public class FermatLittleTheorem {
	
	public static boolean isPrime(BigInteger n) {
        
        if (n.equals(BigInteger.ZERO) 
        		|| n.equals(BigInteger.ONE) 
        		|| n.mod(BigInteger.TWO).equals(BigInteger.ZERO))
            return false;

        if (n.equals(BigInteger.TWO) 
        		|| n.equals(new BigInteger("3")))
            return true;

        BigInteger k = n.subtract(BigInteger.ONE);

        for (int i = 0; i < 1000; i++) {

            BigInteger a = getRandomBigInteger(n.subtract(BigInteger.TWO)).add(BigInteger.TWO);

            a = FastModularExponentiation.getMod(a, k, n);

            if (!a.equals(BigInteger.ONE))
                return false;
        }

        return true;
    }
		
	public static boolean isPrimeOld(BigInteger n) {
		
		if(n.equals(BigInteger.ONE))
			return false;
		if(n.equals(BigInteger.TWO)|| n.equals(new BigInteger("3")))
			return true;
		BigInteger k = n.subtract(BigInteger.ONE);
		BigInteger a = getRandomBigInteger(k.subtract(BigInteger.TWO)).add(BigInteger.TWO);
		boolean prime = false;
		
		BigInteger remainder;
		
		while( !k.equals(BigInteger.ZERO) ) {
			
			remainder = a.modPow(k, n);
			
			if (!remainder.equals(BigInteger.ONE))
				if (k.compareTo(n.subtract(BigInteger.ONE)) < 0)
					if ((remainder.subtract(n)).equals(new BigInteger("-1"))) {
						prime = false;
						break;
					}
					
			if (remainder.subtract(BigInteger.ONE).equals(new BigInteger("-1"))) {
				System.out.println(k);
				prime = true;
				break;
			}
			
			if (remainder.equals(BigInteger.ONE) && k.mod(BigInteger.TWO).equals(BigInteger.ONE)) {
				prime = true;
				break;
			}
						
			k = k.divide(BigInteger.TWO);
			
		}
		
		return prime;
	}
		
	public static BigInteger getRandomBigInteger(BigInteger n) {

		Random rand = new Random();
	    BigInteger result = new BigInteger(n.bitLength(), rand);
	    while( result.compareTo(n) >= 0 )
	        result = new BigInteger(n.bitLength(), rand);
	    
	    return result;
        
    }
	
	public static BigInteger power(BigInteger base, BigInteger exponent) {
		  BigInteger result = BigInteger.ONE;
		  while (exponent.signum() > 0) {
			  if (exponent.testBit(0)) result = result.multiply(base);
			  base = base.multiply(base);
			  exponent = exponent.shiftRight(1);
		  }
		  return result;
	}
	
}
