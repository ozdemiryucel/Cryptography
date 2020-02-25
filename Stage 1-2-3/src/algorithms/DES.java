package algorithms;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DES {
	
	public static byte[] encrypt(String textOfPages, byte[] bytesOfSharedSecret) {
		byte[] encryptedText = null;
		
		try {
			DESKeySpec dks = new DESKeySpec(bytesOfSharedSecret);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			SecretKey myDesKey = null;
			
			myDesKey = skf.generateSecret(dks);
			
		    Cipher desCipher;
	
		    // Create the cipher 
		    desCipher = Cipher.getInstance("DES");
		    
		    // Initialize the cipher for encryption
		    desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
	
		    //sensitive information
		    byte[] text = textOfPages.getBytes();
	
		    // Encrypt the text
		    encryptedText = desCipher.doFinal(text);
		    
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return encryptedText;	
	}
	
	public static byte[] decrypt(byte[] encryptedText, byte[] bytesOfSharedSecret ) {
		
		byte[] decryptedText = null;
		
		try {
			DESKeySpec dks = new DESKeySpec(bytesOfSharedSecret);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			SecretKey myDesKey = skf.generateSecret(dks);
			
		    Cipher desCipher;
	
		    // Create the cipher 
		    desCipher = Cipher.getInstance("DES");
		    
		    desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
	
		    // Decrypt the text
		    decryptedText = desCipher.doFinal(encryptedText);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return decryptedText;
	}
	
}
