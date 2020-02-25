package model;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algorithms.*;

public class Person {
	
	private BigInteger privateKeyOfDES;
	private BigInteger publicKeyOfDES;
	private BigInteger primeNumber;
	private BigInteger generatorOfPrimeNumber;
	private BigInteger[] privateKeyOfRSA;
	private BigInteger[] publicKeyOfRSA;

	private Map<String, BigInteger> DSAPublicComponents; // QPGY
	private BigInteger x;

	public Map<String, BigInteger> getDSAPublicComponents() {
		return DSAPublicComponents;
	}

	public Person(BigInteger primeNumber, BigInteger generatorOfPrimeNumber) {
		this.setPrimeNumber(primeNumber);
		this.setGeneratorOfPrimeNumber(generatorOfPrimeNumber);

		if (primeNumber != null) {
			setPrivateKeyForDES();
			setPublicKeyForDES();
		}

		setRSAComponents();
		setDSAcomponents();
	}

	public Map<String, String> signMessage(String M) {
		Map<String, String> messageAndSignature = new HashMap<>();
		Map<String, BigInteger> tempMap = DSA.getRandS(M, DSAPublicComponents, x);

		messageAndSignature.put("MESSAGE", M);
		messageAndSignature.put("R", tempMap.get("R").toString());
		messageAndSignature.put("S", tempMap.get("S").toString());

		return messageAndSignature;
	}

	public boolean isSignatureVerified(Map<String, String> signatureAndMessageOfSender, Map<String, BigInteger> otherDSAPublicComponents) {
		String M = signatureAndMessageOfSender.get("MESSAGE");
		Map<String, BigInteger> RS = new HashMap<>();
		RS.put("R", new BigInteger(signatureAndMessageOfSender.get("R")));
		RS.put("S", new BigInteger(signatureAndMessageOfSender.get("S")));

		return DSA.isSignatureVerified(M, RS, otherDSAPublicComponents);
	}

	private void setDSAcomponents() {
		DSAPublicComponents = new HashMap<>();
		Map<String, BigInteger> QPG = DSA.getQPG();
		Map<String, BigInteger> XY = DSA.getXY(QPG);

		DSAPublicComponents.put("Q", QPG.get("Q"));
		DSAPublicComponents.put("P", QPG.get("P"));
		DSAPublicComponents.put("G", QPG.get("G"));

		x = XY.get("X");
		DSAPublicComponents.put("Y", XY.get("Y"));
	}

	private void setRSAComponents() {
		BigInteger p = Generator.getRandomNBitBigInteger(64, true); //secret
		BigInteger q = Generator.getRandomNBitBigInteger(64, true); //secret
		BigInteger N = p.multiply(q); //Step 1 - N is public

		//T is secret
		BigInteger T = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE)); //Step 2

		BigInteger e;
		do {
			e = Generator.getRandomNBitBigInteger(64, false); //secret
		} while (!ExtendedEuclideanAlgorithm.areRelativelyPrime(e, T));

		BigInteger d = ExtendedEuclideanAlgorithm.getMultiplicativeInverse(e, T); //Step 4

		this.privateKeyOfRSA = new BigInteger[2];
		this.publicKeyOfRSA = new BigInteger[2];

		privateKeyOfRSA[0] = N;
		privateKeyOfRSA[1] = d;

		publicKeyOfRSA[0] = N;
		publicKeyOfRSA[1] = e;
	}

	public BigInteger[] getPublicKeyOfRSA() {
		return this.publicKeyOfRSA;
	}

	private void setPrivateKeyForDES() {
		this.privateKeyOfDES = Generator.getRandomNBitBigInteger(64, false);
	}

	public BigInteger getPublicKeyOfDES() {
		return publicKeyOfDES;
	}

	private void setPublicKeyForDES() {
		this.publicKeyOfDES = FastModularExponentiation.getMod(
				generatorOfPrimeNumber, this.privateKeyOfDES, this.primeNumber);
	}

	private void setPrimeNumber(BigInteger primeNumber) {
		this.primeNumber = primeNumber;
	}

	private void setGeneratorOfPrimeNumber(BigInteger generatorOfPrimeNumber) {
		this.generatorOfPrimeNumber = generatorOfPrimeNumber;
	}
	
	public BigInteger getSharedSecret(BigInteger otherPersonsPublicKey) {
		return FastModularExponentiation.getMod(otherPersonsPublicKey, this.privateKeyOfDES, this.primeNumber);
	}

	public byte[] encryptWithDES(String text, BigInteger publicKeyOfReceiver) {
		return DES.encrypt(text, getSharedSecret(publicKeyOfReceiver).toByteArray());
	}

	public byte[] decryptWithDES(byte[] encryptedText, BigInteger publicKeyOfSender) {
		return DES.decrypt(encryptedText, getSharedSecret(publicKeyOfSender).toByteArray());
	}

	public List<BigInteger> encryptWithRSA(String text, BigInteger[] otherPersonsPublicKey) {
		return RSA.encrypt(text, otherPersonsPublicKey);
	}

	public String decryptWithRSA(List<BigInteger> text) {
		return RSA.decrypt(text, this.privateKeyOfRSA);
	}

}
