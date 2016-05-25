package com.metapx.pixeltransfer.repository.local;

import java.io.File;
import java.io.PrintWriter;

import com.metapx.pixeltransfer.repository.local.HashCalculator;

import junit.framework.TestCase;

public class HashCalculatorTest extends TestCase {

	public void testCalculateStringDigest() throws Exception {
		final File file = File.createTempFile("temp", ".tmp");
		
		try (final PrintWriter writer = new PrintWriter(file.getAbsolutePath(), "UTF-8")) {
			writer.println("This is a temp file");
		}
		
		assertEquals("b4cb6570c34770fd8ab8600cdc33096c", HashCalculator.calculateStringDigest(file));
	}
}
