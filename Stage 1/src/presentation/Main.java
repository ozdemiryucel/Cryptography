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
		String text1000 = FileOperations.readPages("1000pages.txt");
		String text100 = FileOperations.readPages("100pages.txt");
		String text10 = FileOperations.readPages("10pages.txt");
		String text1 = FileOperations.readPages("1pages.txt");
		
		textList.add(text1000);
		textList.add(text100);
		textList.add(text10);
		textList.add(text1);
		
		List<String> decryptedTextList = new ArrayList<String>();
		
		long startTime;
		long endTime;
		long previousMemoryUsage;
		long latestMemoryUsage;
		
		for (int i=0; i < textList.size(); i++) {
			System.gc();
			
			startTime = System.nanoTime();
			previousMemoryUsage = MemoryUsage.getMemoryUsageKB();
			
			byte[] encryptedText = sender.encrypt(textList.get(i), receiver.getPublicKey());
					//DES.encrypt(text, sharedSecretOfSender.toByteArray());
//			System.out.println(new String(encryptedText));
			byte[] decryptedText = receiver.decrypt(encryptedText, sender.getPublicKey());
//			System.out.println(MemoryUsage.getMemoryUsage());
					//DES.decrypt(encryptedText, sharedSecretOfReceiver.toByteArray());
//			System.out.println(new String(decryptedText));
			latestMemoryUsage = MemoryUsage.getMemoryUsageKB();
			
			endTime = System.nanoTime();
			
			System.out.println("\nText " + (int) Math.pow(10, (textList.size()-1)-i) 
					+ " execution time: " + (endTime - startTime) + " nanoseconds");
			System.out.println("Memory usage: " + (latestMemoryUsage - previousMemoryUsage) + " kB");
			
			decryptedTextList.add(new String(decryptedText));
		}
				
		
		System.out.println();
		int textNumber;
		for (int i = 0; i < decryptedTextList.size(); i++) {
			textNumber = (int) Math.pow(10, (textList.size()-1)-i);
			if(decryptedTextList.get(i).equals(textList.get(i)))
				System.out.println("text" + textNumber + " and decrypted text" + textNumber + " same!");
			else
				System.out.println("text" + textNumber + " and decrypted text" + textNumber + " different!!!!!");
		}
		
	}
	
	
	
	public static void main(String[] args) {
		
		Run();
		
	}
}
