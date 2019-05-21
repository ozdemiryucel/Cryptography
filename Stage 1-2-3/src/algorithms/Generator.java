package algorithms;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Generator {
	
	public static BigInteger getGeneratorOf(BigInteger p) {
		BigInteger pMinusOne = p.subtract(BigInteger.ONE);
		List<BigInteger> primeFactors = getPrimeFactorsOf(pMinusOne);
		boolean generatorFlag;
		
		for (BigInteger g = BigInteger.TWO; g.compareTo(p) == -1; g = g.add(BigInteger.ONE)) {
			generatorFlag = true;
			for (BigInteger primeFactor : primeFactors) {
				if (FastModularExponentiation.getMod(g, pMinusOne.divide(primeFactor), p).equals(BigInteger.ONE)) {
						generatorFlag = false;
						break;
				}
			}
			
			if (generatorFlag)
				return g;
		}
		
		return new BigInteger("-1");
	}
	
	
	
	private static List<BigInteger> getPrimeFactorsOf(BigInteger n) {
		
		List<BigInteger> primeFactors = new ArrayList<BigInteger>();
	    while (n.mod(BigInteger.TWO).equals(BigInteger.ZERO) && n.compareTo(BigInteger.ZERO) == 1) {
	        primeFactors.add(BigInteger.TWO);
	        n = n.divide(BigInteger.TWO);
	    }
	    
	    for (BigInteger i = new BigInteger("3");
	    		i.multiply(i).compareTo(n) == -1 || i.multiply(i).equals(n);
	    		i = i.add(BigInteger.TWO)) {
	        while (n.mod(i).equals(BigInteger.ZERO)) {
	            primeFactors.add(i);
	            n = n.divide(i);
	        }
	    }
	    if (n.compareTo(BigInteger.ONE) == 1)
	        primeFactors.add(n);
	    
	    Set<BigInteger> set = new HashSet<BigInteger>(primeFactors);
	    primeFactors.clear();
	    primeFactors.addAll(set);
	    
	    Collections.sort(primeFactors);
	    
//	    for (BigInteger bigInteger : primeFactors) {
//			System.out.println("Prime factor: " + bigInteger);
//		}
	    return primeFactors;
	}

	
	public static BigInteger getRandomPrimeNDigit(int n) {
		
		String bigNumber = "";
		for (int i = 0; i < n; i++) {
			bigNumber = bigNumber.concat("9");
		}
		BigInteger number = null;
		
		while (true) {
			number = FermatLittleTheorem.getRandomBigInteger(new BigInteger(bigNumber));
			if(String.valueOf(number).length() == bigNumber.length()) {
				if(FermatLittleTheorem.isPrime(number))
					break;
			}
		}
				
		return number;
		
	}
	
	public static BigInteger getRandomNBitBigInteger(int N, boolean doYouWantPrime) {

        Random random = new Random();
        BigInteger randomNumber = null;
        
        while (true) {
        	randomNumber = new BigInteger(N,random);
        	if (randomNumber.bitLength() != N)
        		continue;
        	if (!doYouWantPrime) 
				break;
        	else if (FermatLittleTheorem.isPrime(randomNumber)) {
				break;
			}
			
        }
        
        return randomNumber;
    }

}
