package com.metapx.pixeltransfer.repository.local;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Calculates digests of files.
 */
public final class HashCalculator {

	/**
	 * Calculates a digest of the given file.
	 * @param file
	 * @return the digest
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] calculateByteDigest(File file) throws IOException, NoSuchAlgorithmException {
		final MessageDigest md = MessageDigest.getInstance("MD5");
		try (final InputStream is = Files.newInputStream(file.toPath(), StandardOpenOption.READ);
		     final DigestInputStream dis = new DigestInputStream(is, md)) {
		  final byte[] buf = new byte[1024];
		  
		  while (dis.read(buf) != -1) {
			  // do nothing
		  }
		}
		return md.digest();
	}
	
	/**
	 * Calculates a digest of the given file.
	 * @param file
	 * @return a string with the digest
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String calculateStringDigest(File file) throws NoSuchAlgorithmException, IOException {
       final byte[] b = calculateByteDigest(file);
       String result = "";

       for (int i=0; i < b.length; i++) {
           result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
       }
       return result;
	}
}
