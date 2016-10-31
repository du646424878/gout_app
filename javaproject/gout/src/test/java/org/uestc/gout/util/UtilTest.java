package org.uestc.gout.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilTest {

	@Test
	public void testEncrypt() {
		System.out.println(Util.strEncrypt("admin"));
	}

	@Test
	public void testUUID() {
		System.out.println(Util.generateUUID());
	}

}
