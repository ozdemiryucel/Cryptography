package model;

import java.math.BigInteger;

import algorithms.DES;
import algorithms.FastModularExponentiation;
import algorithms.Generator;

public class Person {
	
	private BigInteger privateKey;
	private BigInteger publicKey;
	private BigInteger primeNumber;
	private BigInteger generatorOfPrimeNumber;
	
	public Person(BigInteger primeNumber, BigInteger generatorOfPrimeNumber) {
		this.setPrimeNumber(primeNumber);
		this.setGeneratorOfPrimeNumber(generatorOfPrimeNumber);
		setPrivateKey();
		setPublicKey();
		
	}

	private void setPrivateKey() {
		this.privateKey = Generator.getRandomNBitBigInteger(64, false);
	}

	public BigInteger getPublicKey() {
		return publicKey;
	}

	private void setPublicKey() {
		this.publicKey = FastModularExponentiation.getMod(
				generatorOfPrimeNumber, this.privateKey, this.primeNumber);
	}

	private void setPrimeNumber(BigInteger primeNumber) {
		this.primeNumber = primeNumber;
	}

	private void setGeneratorOfPrimeNumber(BigInteger generatorOfPrimeNumber) {
		this.generatorOfPrimeNumber = generatorOfPrimeNumber;
	}
	
	public BigInteger getSharedSecret(BigInteger otherPersonsPublicKey) {
		return FastModularExponentiation.getMod(otherPersonsPublicKey, this.privateKey, this.primeNumber);
	}

	public byte[] encrypt(String text, BigInteger publicKeyOfReceiver) {
		return DES.encrypt(text, getSharedSecret(publicKeyOfReceiver).toByteArray());
	}

	public byte[] decrypt(byte[] encryptedText, BigInteger publicKeyOfSender) {
		return DES.decrypt(encryptedText, getSharedSecret(publicKeyOfSender).toByteArray());
	}


}
