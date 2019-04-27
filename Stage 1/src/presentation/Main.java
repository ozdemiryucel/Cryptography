/**
 * Yucel Ozdemir
 * 220201009
 */

package presentation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import algorithms.*;
import dataAccess.FileOperations;
import model.Person;

public class Main {
	
	public static void Run() {
		
		//1
		BigInteger randomPrimeNumber = Generator.getRandomNBitBigInteger(64, true);
		System.out.println("Random prime number: " + randomPrimeNumber);
		
		BigInteger generator = Generator.getGeneratorOf(randomPrimeNumber);
		System.out.println("Generator: " + generator);
		System.out.println();
		
		Person sender = new Person(randomPrimeNumber, generator);
		Person receiver = new Person(randomPrimeNumber, generator);
		
		System.out.println("Public key of sender is " + sender.getPublicKey());
		System.out.println("Public key of receiver is " + receiver.getPublicKey());
		
		//2
		BigInteger sharedSecretOfSender = sender.getSharedSecret(receiver.getPublicKey());
		BigInteger sharedSecretOfReceiver = receiver.getSharedSecret(sender.getPublicKey());
		
		if(sharedSecretOfSender.equals(sharedSecretOfReceiver)) {
			System.out.println("Shared secrets are equal!");
			System.out.println("Shared secret is " + sharedSecretOfSender);
			System.out.println("Number of bytes of shared secret are " 
								+ sharedSecretOfSender.toByteArray().length);
		}
		
		//3
		List<String> textList = new ArrayList<String>();
		String text1 = FileOperations.readPages("1pages.txt");
		String text2 = FileOperations.readPages("10pages.txt");
		String text3 = FileOperations.readPages("100pages.txt");
		String text4 = FileOperations.readPages("1000pages.txt");
		
		textList.add(text1);
		textList.add(text2);
		textList.add(text3);
		textList.add(text4);
		
		List<String> decryptedTextList = new ArrayList<String>();
		
		long startTime;
		long endTime;
		for (int i=0; i < textList.size(); i++) {
			System.gc();
			startTime = System.currentTimeMillis();
			byte[] encryptedText = sender.encrypt(textList.get(i), receiver.getPublicKey());
					//DES.encrypt(text, sharedSecretOfSender.toByteArray());
//			System.out.println(new String(encryptedText));
			byte[] decryptedText = receiver.decrypt(encryptedText, sender.getPublicKey());
//			System.out.println(MemoryUsage.getMemoryUsage());
					//DES.decrypt(encryptedText, sharedSecretOfReceiver.toByteArray());
//			System.out.println(new String(decryptedText));
			endTime = System.currentTimeMillis();
			System.out.println("\nText " + (int) Math.pow(10, i) 
					+ " execution time: " + (endTime - startTime) + " miliseconds");
			System.out.println(MemoryUsage.getMemoryUsage());
			
			decryptedTextList.add(new String(decryptedText));
		}
				
		
		System.out.println();
		for (int i = 0; i < decryptedTextList.size(); i++) {
			if(decryptedTextList.get(i).equals(textList.get(i)))
				System.out.println("text" + (i+1) + " and decrypted text" + (i+1) + " same!");
			else
				System.out.println("text" + (i+1) + " and decrypted text" + (i+1) + " different!!!!!");
		}
		
	}
	
	
	
	public static void main(String[] args) {
		
		Run();
		
	}
}
