package algorithms;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FastModularExponentiation {
	
	public static BigInteger getMod(BigInteger base, BigInteger exponentiation, BigInteger modulus) {
		
		List<BigInteger> listOfTwosPowersOfExps = getPowersOfTwos(exponentiation);
		
		BigInteger mult = BigInteger.ONE;
		
		for (BigInteger exp : listOfTwosPowersOfExps) {
			BigInteger mod = getModPrivate(base, exp, modulus);
			mult = mult.multiply(mod);
		}		
		
		return mult.mod(modulus);
	}
	
	
	
	private static BigInteger getModPrivate(BigInteger base, BigInteger exp, BigInteger modulus) {		
		while(!exp.equals(BigInteger.ONE)) {
			if(base.equals(BigInteger.ONE))
				return BigInteger.ONE;
			base = getNegativeSubtraction(base, modulus);
			exp = exp.divide(BigInteger.TWO);
			base = base.pow(2);			
		}
		
		return base.mod(modulus);
	}
	
	private static BigInteger getNegativeSubtraction(BigInteger n, BigInteger modulus) {
		return n.mod(modulus).subtract(modulus);
	}

	private static List<BigInteger> getPowersOfTwos(BigInteger n) {
		
		List<BigInteger> oneList = new ArrayList<>();
		List<BigInteger> actualList = new ArrayList<>();
		
		while(true) {
			BigInteger remainder = n.mod(BigInteger.TWO);
			BigInteger quotient = n.divide(BigInteger.TWO);
			
			if(quotient.equals(BigInteger.ONE)) {
				oneList.add(remainder);
				oneList.add(quotient);
				break;
			}
			
			else {
				oneList.add(remainder);
				n = quotient;
			}
		}
		
		
		for (int i = 0; i < oneList.size(); i++)
			if (oneList.get(i).equals(BigInteger.ONE))
				actualList.add((BigInteger.TWO).pow(i));
		
		Collections.reverse(actualList);
		
		return actualList;
	}

}
