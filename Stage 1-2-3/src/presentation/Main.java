/*
  Yucel Ozdemir
  220201009
 */

package presentation;

import java.math.BigInteger;
import java.util.*;

import algorithms.*;
import dataAccess.FileOperations;
import model.Person;

public class Main {

	public static void Stage1() {

		//1
		BigInteger randomPrimeNumber = Generator.getRandomNBitBigInteger(64, true);
		System.out.println("Random prime number: " + randomPrimeNumber);

		BigInteger generator = Generator.getGeneratorOf(randomPrimeNumber);
		System.out.println("Generator: " + generator);
		System.out.println();

		Person sender = new Person(randomPrimeNumber, generator);
		Person receiver = new Person(randomPrimeNumber, generator);

		System.out.println("Public key of sender is " + sender.getPublicKeyOfDES());
		System.out.println("Public key of receiver is " + receiver.getPublicKeyOfDES());

		//2
		BigInteger sharedSecretOfSender = sender.getSharedSecret(receiver.getPublicKeyOfDES());
		BigInteger sharedSecretOfReceiver = receiver.getSharedSecret(sender.getPublicKeyOfDES());

		if (sharedSecretOfSender.equals(sharedSecretOfReceiver)) {
			System.out.println("Shared secrets are equal!");
			System.out.println("Shared secret is " + sharedSecretOfSender);
			System.out.println("Number of bytes of shared secret are "
					+ sharedSecretOfSender.toByteArray().length);
		}

		//3
		List<String> textList = new ArrayList<>();
		String text1000 = FileOperations.readPages("1000pages.txt");
		String text100 = FileOperations.readPages("100pages.txt");
		String text10 = FileOperations.readPages("10pages.txt");
		String text1 = FileOperations.readPages("1pages.txt");

		textList.add(text1000);
		textList.add(text100);
		textList.add(text10);
		textList.add(text1);

		List<String> decryptedTextList = new ArrayList<>();

		long startTime;
		long endTime;
		long previousMemoryUsage;
		long latestMemoryUsage;

		System.out.println(textList.size());
		for (int i = 0; i < textList.size(); i++) {
			System.gc();

			startTime = System.nanoTime();
			previousMemoryUsage = MemoryUsage.getMemoryUsageKB();

			byte[] encryptedText = sender.encryptWithDES(textList.get(i), receiver.getPublicKeyOfDES());
			byte[] decryptedText = receiver.decryptWithDES(encryptedText, sender.getPublicKeyOfDES());

			latestMemoryUsage = MemoryUsage.getMemoryUsageKB();

			endTime = System.nanoTime();

			System.out.println("\nText " + (int) Math.pow(10, (textList.size() - 1) - i)
					+ " execution time: " + (endTime - startTime) + " nanoseconds");
			System.out.println("Memory usage: " + (latestMemoryUsage - previousMemoryUsage) + " kB");

			decryptedTextList.add(new String(decryptedText));
		}


		System.out.println();
		int textNumber;
		for (int i = 0; i < decryptedTextList.size(); i++) {
			textNumber = (int) Math.pow(10, (textList.size() - 1) - i);
			if (decryptedTextList.get(i).equals(textList.get(i)))
				System.out.println("text" + textNumber + " and decrypted text" + textNumber + " same!");
			else
				System.out.println("text" + textNumber + " and decrypted text" + textNumber + " different!!!!!");
		}

	}

	public static void Stage2() {

		Person sender = new Person(null, null);
		Person receiver = new Person(null, null);

		List<String> textList = new ArrayList<>();
		String text1000 = FileOperations.readPages("1000pages.txt");
		String text100 = FileOperations.readPages("100pages.txt");
		String text10 = FileOperations.readPages("10pages.txt");
		String text1 = FileOperations.readPages("1pages.txt");

		textList.add(text1000);
		textList.add(text100);
		textList.add(text10);
		textList.add(text1);

		List<String> decryptedTextList = new ArrayList<>();

		long startTime;
		long endTime;
		long previousMemoryUsage;
		long latestMemoryUsage;

		System.out.println("Sender's public key: [N,e] = " + Arrays.toString(sender.getPublicKeyOfRSA()));
		System.out.println("Receiver's public key: [N,e] = " + Arrays.toString(receiver.getPublicKeyOfRSA()));


		for (int i = 0; i < textList.size(); i++) {
			System.gc();

			startTime = System.nanoTime();
			previousMemoryUsage = MemoryUsage.getMemoryUsageKB();

			List<BigInteger> encryptedText = sender.encryptWithRSA(textList.get(i),
																	receiver.getPublicKeyOfRSA());
			String decryptedText = receiver.decryptWithRSA(encryptedText);

			latestMemoryUsage = MemoryUsage.getMemoryUsageKB();
			endTime = System.nanoTime();

			System.out.println("\nText " + (int) Math.pow(10, (textList.size() - 1) - i)
					+ " execution time: " + (int) ((endTime - startTime)/Math.pow(10,9)) + " seconds");
			System.out.println("Memory usage: " + (latestMemoryUsage - previousMemoryUsage)/1024 + " MB");

			decryptedTextList.add(decryptedText);

		}

		System.out.println();
		int textNumber;
		for (int i = 0; i < decryptedTextList.size(); i++) {
			textNumber = (int) Math.pow(10, (textList.size() - 1) - i);
			if (decryptedTextList.get(i).equals(textList.get(i)))
				System.out.println("text" + textNumber + " and decrypted text" + textNumber + " same!");
			else
				System.out.println("text" + textNumber + " and decrypted text" + textNumber + " different!!!!!");
		}
	}

	public static void Stage3() {

		Person sender = new Person(null, null);
		Person receiver = new Person(null, null);

		System.out.println("Sender's public key is " + sender.getDSAPublicComponents().get("Y"));
		System.out.println("Receiver's public key is " + receiver.getDSAPublicComponents().get("Y"));

		List<String> textList = new ArrayList<>();
		String text1000 = FileOperations.readPages("1000pages.txt");
		String text100 = FileOperations.readPages("100pages.txt");
		String text10 = FileOperations.readPages("10pages.txt");
		String text1 = FileOperations.readPages("1pages.txt");

		textList.add(text1000);
		textList.add(text100);
		textList.add(text10);
		textList.add(text1);


		for (int i = 0; i < textList.size(); i++) {


			Map<String, String> signatureAndMessageOfSender = sender.signMessage(textList.get(i));
			boolean isVerified = receiver.isSignatureVerified(signatureAndMessageOfSender,
																sender.getDSAPublicComponents());


			if (isVerified)
				System.out.println("\nText " + (int) Math.pow(10, (textList.size() - 1) - i)
									+ " is verified");
			else
				System.out.println("\nText " + (int) Math.pow(10, (textList.size() - 1) - i)
									+ " is not verified");


		}
	}

	public static void main(String[] args) {
//		Stage1();
//		Stage2();
		Stage3();
	}
}
