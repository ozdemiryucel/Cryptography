package algorithms;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class EuclidExtendedAlgorithm {
	
	private static List<BigInteger> xList = new ArrayList<BigInteger>();
	private static List<BigInteger> returnList = new ArrayList<BigInteger>();
	
	/**
	 * @param a
	 * @param b
	 * @return List<BigInteger> {GCD, X, Y}
	 */
	public static List<BigInteger> getGcdXandY(BigInteger a, BigInteger b) {
		
		xList.clear();
		returnList.clear();
		
		BigInteger gcd = null;
		
		if (a.equals(BigInteger.ONE) || b.equals(BigInteger.ONE)) {
			gcd = BigInteger.ONE;
			returnList.add(gcd);
			
			if(a.equals(BigInteger.ONE)) {
				returnList.add(BigInteger.ONE);
				returnList.add(BigInteger.ZERO);
			}
			else if(b.equals(BigInteger.ONE)) {
				returnList.add(BigInteger.ZERO);
				returnList.add(BigInteger.ONE);
			}
			
			return returnList;
		}
		
		
		if(a.compareTo(b) == 1)
			gcd = getGcdOf(a, b);
		else if(b.compareTo(a) == 1)
			gcd = getGcdOf(b, a);
		
		
		BigInteger lastX = getLastX();
		
		BigInteger y = null;
		
		if(a.compareTo(b) == 1)
			y = gcd.subtract(lastX.multiply(b)).divide(a);
		else if(b.compareTo(a) == 1)
			y = gcd.subtract(lastX.multiply(a)).divide(b);
		

		returnList.add(gcd);
		
		if(a.compareTo(b) == 1) {
			returnList.add(y);
			returnList.add(lastX);
		}
		else if (b.compareTo(a) == 1) {
			returnList.add(lastX);
			returnList.add(y);
		}
		
		return returnList;
	}
	
	public static BigInteger getGcdOf(BigInteger a, BigInteger b) {
		
		BigInteger mod = a.mod(b);
		
		if(mod.compareTo(BigInteger.ZERO) == 0)
			return b;
		
		xList.add(a.divide(b));
		
		return getGcdOf(b, mod);
		
	}
	
	private static BigInteger getLastX() {
		
		BigInteger leftX = BigInteger.ONE;
		BigInteger rightX = BigInteger.ZERO;
		
		BigInteger result = (BigInteger.ZERO.subtract(xList.get(0)).multiply(leftX))
				.add(rightX);
		
		for (int i = 1; i < xList.size(); i++) {
			rightX = leftX;
			leftX = result;
			
			
			result = (new BigInteger("0").subtract(xList.get(i)).multiply(leftX))
					.add(rightX);
		}
		
		return result;
		
		
	}

	public static BigInteger getMultiplicativeInverse(BigInteger a, BigInteger b) {
		List<BigInteger> list = getGcdXandY(a, b);

        if (!list.get(0).equals(BigInteger.ONE)) {
           return new BigInteger("-1");
        }

        BigInteger multiplicativeInverse = list.get(1).mod(b).add(b).mod(b);
        
        return multiplicativeInverse;
	}
	
	public static boolean areRelativelyPrime(BigInteger a, BigInteger b) {
		
		boolean relativelyPrime;
		
		if(getGcdOf(a, b).equals(BigInteger.ONE))
			relativelyPrime = true;
		else
			relativelyPrime = false;
		
		return relativelyPrime;
			
	}
}
