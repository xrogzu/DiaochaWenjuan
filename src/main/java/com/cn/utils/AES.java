package com.cn.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	// 加密方法
	public String encryptString(String plaintext, String key, String iv) {
		String ciphertext = "";

		try {
			init(key.getBytes("UTF-8"), iv.getBytes("UTF-8"));

			byte[] outdata = encrypt(plaintext.getBytes("UTF-8"));

			ciphertext = new String(outdata, "ISO-8859-1");

		} catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}

		return ciphertext;
	}

	// 解密方法
	public String decryptString(String ciphertext, String key, String iv) {
		String plaintext = "";

		try {
			init(key.getBytes("UTF-8"), iv.getBytes("UTF-8"));

			byte[] indata = decrypt(ciphertext.getBytes("ISO-8859-1"));

			plaintext = new String(indata, "ISO-8859-1");

		} catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}

		return plaintext;
	}

	public byte[] decryptByte(byte[] encryptbyte, String key, String iv) {

		try {
			init(key.getBytes("UTF-8"), iv.getBytes("UTF-8"));

			byte[] indata = decrypt(encryptbyte);

			return indata;
		} catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}

		return null;
	}

	// 加密方法
	public String encryptFile(String filepath, String key, String iv) {
		System.out.println("我们进行了半加密！");
		try {
			init(key.getBytes("UTF-8"), iv.getBytes("UTF-8"));

			FileHelper fileHelper = new FileHelper();

			byte[] src = fileHelper.getContent(filepath); // 要加密的文件转换的字节

			byte[] presrc = new byte[128];
			byte[] srcs = new byte[128 + 16];

			for (int i = 0; i < 128; i++) {
				presrc[i] = src[i];
			}

			srcs = encrypt(presrc);

			byte[] outde = new byte[src.length + 16];

			for (int i = 0; i < outde.length; i++) {
				if (i < 144)
					outde[i] = srcs[i];
				else
					outde[i] = src[i - 16];
			}

			fileHelper.createFile(filepath, outde);

		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
				| IOException e) {
			e.printStackTrace();
		}

		return filepath;
	}

	// 加密方法
	public String encryptFilePart(String filepath, String key, String iv) {
		RandomAccessFile randomFile = null;
		int blocksize = 4096;
		try {
			init(key.getBytes("UTF-8"), iv.getBytes("UTF-8"));

			FileOutputStream fos = new FileOutputStream(filepath + ".temp");

			// 打开一个随机访问文件流，按只读方式
			randomFile = new RandomAccessFile(filepath, "r");

			long beginIndex = 0;

			randomFile.seek(beginIndex);

			byte[] byteforencrypt = new byte[128];
			byte[] encryptbyte = new byte[144];
			byte[] block = new byte[blocksize];

			randomFile.read(byteforencrypt);

			encryptbyte = encrypt(byteforencrypt);

			fos.write(encryptbyte);

			beginIndex = 128;

			while (randomFile.read(block) != -1) {

				fos.write(block);

				randomFile.seek(beginIndex += blocksize);
			}

			fos.close();
			randomFile.close();

			System.out.println("the filepath is " + filepath);
			File file = new File(filepath);
			file.delete();

			File oldfile = new File(filepath + ".temp");
			File newfile = new File(filepath);

			oldfile.renameTo(newfile);

			System.out.println("encrypt part file is ok!");

		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
				| IOException e) {
			e.printStackTrace();
		} finally {
		}

		return filepath;
	}

	// 加密方法
	public String encryptFileAllNew(String filepath, String key, String iv) {
		RandomAccessFile randomFile = null;

		int partsize = 32736;

		try {
			init(key.getBytes("UTF-8"), iv.getBytes("UTF-8"));

			FileOutputStream fos = new FileOutputStream(filepath + ".temp");

			// 打开一个随机访问文件流，按只读方式
			randomFile = new RandomAccessFile(filepath, "r");

			long beginIndex = 0;

			randomFile.seek(beginIndex);

			// byte[] byteforencrypt = new byte[128];
			// byte[] encryptbyte = new byte[144];
			byte[] block = new byte[partsize];

			/*
			 * randomFile.read(byteforencrypt);
			 * 
			 * encryptbyte = encrypt(byteforencrypt);
			 * 
			 * fos.write(encryptbyte);
			 */

			beginIndex = 0;

			while (randomFile.read(block) != -1) {

				fos.write(encrypt(block));

				randomFile.seek(beginIndex += partsize);
			}

			fos.close();
			randomFile.close();

			File file = new File(filepath);
			file.delete();

			File oldfile = new File(filepath + ".temp");
			File newfile = new File(filepath);

			oldfile.renameTo(newfile);

			System.out.println("encrypt all file is ok!");

		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
				| IOException e) {
			e.printStackTrace();
		} finally {
		}

		return filepath;
	}

	public String encryptFileAll(String filepath, String key, String iv) {
		System.out.println("我们进行了全加密！");
		int partsize = 32736;
		int partaftersize = 32752;

		try {
			init(key.getBytes("UTF-8"), iv.getBytes("UTF-8"));

			FileHelper fileHelper = new FileHelper();

			byte[] src = fileHelper.getContent(filepath); // 要加密的文件转换的字节

			int totalfilebyte = src.length;

			System.out.println("the total byte is " + totalfilebyte);

			byte[] total = null;

			// 判断文件总字节数是否大于部分加密字节数
			if (totalfilebyte <= partsize) { // 小于部分加密大小，则全部加密
				total = encrypt(src);
				// 直接生成文件
				fileHelper.createFile(filepath, total);
			} else {
				// 总共可以加密的整块数
				int totalblock = totalfilebyte / partsize;
				// 最后一块不够整数的大小
				int lastblocksize = totalfilebyte % partsize;
				// System.out.println("the one is " + src[totalblock*partsize]);
				byte[] lastblock = new byte[lastblocksize];

				System.arraycopy(src, totalblock * partsize, lastblock, 0, lastblocksize);
				/*
				 * System.out.println("the two is " + lastblock[0]); for(int
				 * i=0; i<src.length; i++) { if(i >= totalblock*partsize && i
				 * <totalblock*partsize + 10) { System.out.println("the one is "
				 * + src[i]); } }
				 * 
				 * for(int i=0; i<lastblock.length; i++) { if(i<10) {
				 * System.out.println("the two is " + lastblock[i]); } }
				 */
				// 计算出来最后加密过的文件总大小
				int totalencryptfilebyte = totalblock * partaftersize + encrypt(lastblock).length;

				byte[] lastencryptblock = encrypt(lastblock);
				/*
				 * for(int i=0; i<lastencryptblock.length; i++) { if(i<10) {
				 * System.out.println("the byte is " + lastencryptblock[i]); } }
				 */

				System.out.println("the total block  is " + totalblock + " the lastencryptblock size is "
						+ encrypt(lastblock).length);
				total = new byte[totalencryptfilebyte];

				for (int i = 0; i < totalblock; i++) {
					byte[] partblock = new byte[partsize];
					System.arraycopy(src, i * partsize, partblock, 0, partsize);
					byte[] partafterblock = encrypt(partblock);
					System.arraycopy(partafterblock, 0, total, i * partaftersize, partaftersize);
				}

				System.arraycopy(lastencryptblock, 0, total, totalblock * partaftersize, lastencryptblock.length);

				byte[] testblock = decrypt(lastencryptblock);

				for (int i = 0; i < testblock.length; i++) {
					if (i < 10) {
						System.out.println("the decrypt byte is " + testblock[i]);
					}
				}

				for (int i = 0; i < lastblock.length; i++) {
					if (i < 10) {
						System.out.println("the previous byte is " + lastblock[i]);
					}
				}

				System.out.println("the encrypt total size  is " + total.length);

				fileHelper.createFile(filepath, total);
			}

		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
				| IOException e) {
			e.printStackTrace();
		}

		return filepath;
	}

	public InputStream decryptFileAll(InputStream fis, String key, String iv) {
		System.out.println("我们进行了全解密！");
		int partaftersize = 32752;
		int partsize = 32736;

		try {
			init(key.getBytes("UTF-8"), iv.getBytes("UTF-8"));

			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();

			byte[] buff = new byte[100];

			int rc = 0;

			while ((rc = fis.read(buff, 0, 100)) > 0) {
				swapStream.write(buff, 0, rc);
			}

			byte[] src = swapStream.toByteArray();

			System.out.println("the src size is " + src.length);

			int totalfilebyte = src.length;

			byte[] total = null;

			// 总共可以解密的整块数
			int totalblock = totalfilebyte / partaftersize;
			// 最后一块不够整数的大小
			int lastblocksize = totalfilebyte % partaftersize;

			byte[] lastblock = new byte[lastblocksize];

			System.arraycopy(src, totalblock * partaftersize, lastblock, 0, lastblocksize);

			// 计算出来最后解密过的文件总大小
			int totaldecryptfilebyte = totalblock * partsize + decrypt(lastblock).length;

			total = new byte[totaldecryptfilebyte];

			for (int i = 0; i < totalblock; i++) {
				byte[] partafterblock = new byte[partaftersize];
				System.arraycopy(src, i * partaftersize, partafterblock, 0, partaftersize);
				byte[] partblock = decrypt(partafterblock);
				System.arraycopy(partblock, 0, total, i * partsize, partsize);
			}

			System.arraycopy(decrypt(lastblock), 0, total, totalblock * partsize, decrypt(lastblock).length);

			return new ByteArrayInputStream(total);

		} catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}

		return null;
	}

	// 解密方法
	public String decryptFile(String filepath, String key, String iv) {
		System.out.println("我们进行了半解密！");
		try {
			init(key.getBytes("UTF-8"), iv.getBytes("UTF-8"));

			FileHelper fileHelper = new FileHelper();

			byte[] src = fileHelper.getContent(filepath); // 要解密的文件转换的字节

			byte[] srcs = new byte[128 + 16];

			for (int i = 0; i < 128 + 16; i++) {
				srcs[i] = src[i];
			}

			byte[] outde = decrypt(srcs);

			byte[] ok = new byte[src.length - 16];

			for (int i = 0; i < ok.length; i++) {
				if (i < 128)
					ok[i] = outde[i];
				else
					ok[i] = src[i + 16];
			}

			fileHelper.createFile(filepath, ok);

		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
				| IOException e) {
			e.printStackTrace();
		}

		return filepath;
	}

	public InputStream decryptFile(InputStream fis, String key, String iv) {
		try {
			init(key.getBytes("UTF-8"), iv.getBytes("UTF-8"));

			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();

			byte[] buff = new byte[100];

			int rc = 0;

			while ((rc = fis.read(buff, 0, 100)) > 0) {
				swapStream.write(buff, 0, rc);
			}

			byte[] src = swapStream.toByteArray();

			byte[] srcs = new byte[128 + 16];

			for (int i = 0; i < 128 + 16; i++) {
				srcs[i] = src[i];
			}

			byte[] outde = decrypt(srcs);

			byte[] ok = new byte[src.length - 16];

			for (int i = 0; i < ok.length; i++) {
				if (i < 128)
					ok[i] = outde[i];
				else
					ok[i] = src[i + 16];
			}

			FileHelper fileHelper = new FileHelper();
			fileHelper.createFile("C:/Users/Chen/Desktop/操行分系统.doc", ok);

			return new ByteArrayInputStream(ok);

		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
				| IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public InputStream decryptFilePart(InputStream fis, String key, String iv) {
		try {
			init(key.getBytes("UTF-8"), iv.getBytes("UTF-8"));

			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();

			byte[] buff = new byte[100];

			int rc = 0;

			while ((rc = fis.read(buff, 0, 100)) > 0) {
				swapStream.write(buff, 0, rc);
			}

			byte[] src = swapStream.toByteArray();

			byte[] srcs = new byte[128 + 16];

			for (int i = 0; i < 128 + 16; i++) {
				srcs[i] = src[i];
			}

			byte[] outde = decrypt(srcs);

			byte[] ok = new byte[src.length - 16];

			for (int i = 0; i < ok.length; i++) {
				if (i < 128)
					ok[i] = outde[i];
				else
					ok[i] = src[i + 16];
			}

			FileHelper fileHelper = new FileHelper();
			fileHelper.createFile("C:/Users/Chen/Desktop/操行分系统.doc", ok);

			return new ByteArrayInputStream(ok);

		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
				| IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException {
		/*
		 * AES aes = null; aes = new AES();
		 * 
		 * String filepath = "C:/Users/Chen/Desktop/操行分系统.doc";
		 * 
		 * aes.encryptFile(filepath, "7qqsmmimfeyjgr79", "7lhspeuk7tua2opa");
		 */

		AES aes = null;
		aes = new AES();
		File file = new File("C:/Users/Chen/Desktop/操行分系统.doc");
		InputStream is = new FileInputStream(file);
		aes.decryptFilePart(is, "7qqsmmimfeyjgr79", "7lhspeuk7tua2opa");
		/*
		 * AES aes = new AES();
		 * 
		 * String str1 = "123456";
		 * 
		 * String miwen = aes.encryptString(str1, "4qcybhikgmbb18g5",
		 * "hjf3zdzspdvk4fbh"); //System.out.println("the miwen is " + miwen);
		 * 
		 * 
		 * AES aes2 = new AES(); System.out.println(aes2.decryptString(miwen,
		 * "4qcybhikgmbb18g5", "hjf3zdzspdvk4fbh"));
		 */

		// System.out.println("=======================AES/CBC/PKCS5Padding=====================");
		// key
		// byte[] key = "4qcybhikgmbb18g5".getBytes("UTF-8");
		// dump("key", key);
		// iv
		// byte[] iv = "hjf3zdzspdvk4fbh".getBytes("UTF-8");
		// dump("iv", iv);

		// byte[] indata = "123456".getBytes("utf-8");

		// AES aes = new AES();
		// System.out.println("the string is " + aes.encryptString("123456",
		// "4qcybhikgmbb18g5", "hjf3zdzspdvk4fbh"));
		// aes.encryptString("123456", "4qcybhikgmbb18g5", "hjf3zdzspdvk4fbh");

		// System.out.println(aes.decryptString(aes.encryptString("123456",
		// "4qcybhikgmbb18g5", "hjf3zdzspdvk4fbh"), "4qcybhikgmbb18g5",
		// "hjf3zdzspdvk4fbh"));
		// dump("indata", indata);
		/*
		 * AES aes = new AES();
		 * 
		 * aes.init(key, iv);
		 * 
		 * byte[] outdata = aes.encrypt(indata);
		 * 
		 * System.out.println("the outdata size is " + outdata.length);
		 * 
		 * String s = new String(outdata, "ISO-8859-1");
		 * 
		 * outdata = aes.decrypt(s.getBytes("ISO-8859-1"));
		 * 
		 * System.out.println(new String(outdata,"utf8"));
		 * 
		 * /*for(int i=0; i<outdata.length; i++) { System.out.print(i);
		 * System.out.println(outdata[i]); }
		 */
		// String s1 = new String(outdata,"utf-8");

		// System.out.println("the press string is :" + s1);

		// byte[] test = s1.getBytes("utf-8");

		// System.out.println("the press string is :" + test);

		// byte[] indata1 = aes.decrypt(test);
		// dump("indata1", indata1);
		// System.out.println("#########desrc:"+new String(indata1,"utf-8"));
		// FileHelper fileHelper = new FileHelper();
		/*
		 * String pathfile = "D:/bgimage.jpg"; String pathfile2 =
		 * "D:/bgimage2.jpg";
		 * 
		 * byte[] src = fileHelper.getContent(pathfile); byte[] srcs = new
		 * byte[128+16];
		 * 
		 * for(int i =0 ;i<128+16;i++){ srcs[i] = src[i]; }
		 * 
		 * byte[] outde = aes.decrypt(srcs); byte[] ok = new
		 * byte[src.length-16]; for(int i = 0;i<ok.length;i++){ if(i<128) ok[i]
		 * = outde[i]; else ok[i] = src[i+16]; }
		 * 
		 * fileHelper.createFile(pathfile2, ok);
		 */

		/*
		 * String [] filename = {"11.wav","11.7z","11.aac","11.bmp","11.gif",
		 * "11.jpg","11.m4a","11.mid","11.ogg","11.pdf","11.png","11.rar",
		 * "11.tif","11.wma","11.xls"}; String path =
		 * "C:/Users/LiZhen/Desktop/chen-jiami/"; String path2 =
		 * "C:/Users/LiZhen/Desktop/chen-jiemi/"; String pathfile = null;
		 * for(String str:filename){ pathfile = path+str; byte[] src =
		 * fileHelper.getContent(pathfile); byte[] outde = aes.decrypt(src);
		 * fileHelper.createFile(path2+str, outde);
		 * System.out.println("######:"+str); }
		 */
		/*
		 * FileHelper fileHelper = new FileHelper();
		 * 
		 * AES aes = new AES();
		 * 
		 * byte[] src =
		 * fileHelper.getContent("C:/Users/Chen/Desktop/我最亲爱的.mp3"); dump("src",
		 * src); byte[] out = aes.encrypt(src);
		 * fileHelper.createFile("C:/Users/Chen/Desktop/我最亲爱的.mp3", out);
		 * dump("outdata", out);
		 * 
		 * byte[] srcen =
		 * fileHelper.getContent("C:/Users/Chen/Desktop/我最亲爱的.mp3");
		 * System.out.println("######:1"+srcen.length); byte[] outde =
		 * aes.decrypt(srcen); System.out.println("######:1"+outde.length);
		 * fileHelper.createFile("C:/Users/Chen/Desktop/我最亲爱的.mp3", outde);
		 * dump("outde",outde);
		 */

		/*
		 * for(int i=0;i<1024;i++){ outde[i] = out[i]; }
		 * fileHelper.createFile("C:/Users/LiZhen/Desktop/jia1024.mp3", outde);
		 */
		// AES aes = new AES();

		// aes.encryptFile("C:/Users/Chen/Desktop/Navicat_for_MySQL_11.0.10_XiaZaiBa
		// (2).exe", "0123456789abcdef", "fedcba9876543210");

		// aes.decryptFile("C:/Users/Chen/Desktop/Navicat_for_MySQL_11.0.10_XiaZaiBa
		// (2).exe", "0123456789abcdef", "fedcba9876543210");
		// System.out.println(aes.encryptString("123456", "4qcybhikgmbb18g5",
		// "hjf3zdzspdvk4fbh"));
	}

	private Cipher enc;
	private Cipher dec;
	private SecretKeySpec keySpec;
	private IvParameterSpec ivSpec;

	public AES() {
	}

	/**
	 * init the AES key. the key must be 128, 192, or 256 bits.
	 * 
	 * @param key
	 *            the AES key.
	 * @param keyoff
	 *            the AES key offset.
	 * @param keylen
	 *            the AES key length, the key length must be 16 bytes because
	 *            SunJCE only support 16 bytes key.
	 * @param iv
	 *            the IV for CBC, the length of iv must be 16 bytes.
	 * @param ivoff
	 *            the iv offset.
	 */
	public void init(byte[] key, int keyoff, int keylen, byte[] iv, int ivoff) {
		keySpec = new SecretKeySpec(key, keyoff, keylen, "AES");
		ivSpec = new IvParameterSpec(iv, ivoff, 16);
	}

	/**
	 * init the AES key. the key must be 16 bytes, because SunJCE only support
	 * 16 bytes key..
	 * 
	 * @param key
	 *            the AES key.
	 * @param iv
	 *            the iv for CBC, iv must be 16 bytes length.
	 */
	public void init(byte[] key, byte[] iv) {
		keySpec = new SecretKeySpec(key, "AES");
		ivSpec = new IvParameterSpec(iv);
	}

	/**
	 * get the maximal cipher data length after encrypted.
	 * 
	 * @param len
	 *            the plain data length.
	 * @return the cipher data length.
	 */
	public int getCipherLen(int len) {
		// for PKCS#1 v1.5 padding
		// max padding BLOCK_SIZE=16.
		int pad = len % 16;
		if (0 == pad) {
			return len + 16;
		}
		return len - pad + 16;
	}

	/**
	 * encrypt the input data to output data. the input data length must be the
	 * times of 16 bytes. and the output data length is equals to the input
	 * data.
	 * 
	 * @param indata
	 *            the input data.
	 * @param inoff
	 *            the input data offset.
	 * @param inlen
	 *            the input data length.
	 * @param outdata
	 *            the output data.
	 * @param outoff
	 *            the output data offset.
	 */
	public void encrypt(byte[] indata, int inoff, int inlen, byte[] outdata, int outoff)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		initEncryptor();
		enc.doFinal(indata, inoff, inlen, outdata, outoff);
	}

	/**
	 * encrypt the input data to output data.
	 * 
	 * @param indata
	 *            the input data.
	 * @param inoff
	 *            the input data offset.
	 * @param inlen
	 *            the input data length.
	 * @return the output encrypted data.
	 */
	public byte[] encrypt(byte[] indata, int inoff, int inlen)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		initEncryptor();
		return enc.doFinal(indata, inoff, inlen);
	}

	/**
	 * encrypt the input data to output data.
	 * 
	 * @param indata
	 *            the input data.
	 * @return the output data.
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] encrypt(byte[] indata) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		initEncryptor();
		return enc.doFinal(indata);
	}

	/**
	 * the maximal plain data length after decrypted.
	 * 
	 * @param len
	 *            the cipher data length that will be decrypted.
	 * @return the maximal plain data length.
	 */
	public int getPlainLen(int len) {
		// for PKCS#1 v1.5 padding
		// len always be times of BLOCK_SIZE=16.
		return len;
	}

	/**
	 * decrypt the input data to output data.
	 * 
	 * @param indata
	 *            the input data.
	 * @param inoff
	 *            the input data offset.
	 * @param inlen
	 *            the input data length.
	 * @param outdata
	 *            the output data.
	 * @param outoff
	 *            the output data offset.
	 */
	public void decrypt(byte[] indata, int inoff, int inlen, byte[] outdata, int outoff)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		initDecryptor();
		dec.doFinal(indata, inoff, inlen, outdata, outoff);
	}

	/**
	 * decrypt the input data to output data.
	 * 
	 * @param indata
	 *            the input data.
	 * @param inoff
	 *            the input data offset.
	 * @param inlen
	 *            the input data length.
	 * @return the output decrypted data.
	 */
	public byte[] decrypt(byte[] indata, int inoff, int inlen)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, ShortBufferException, InvalidAlgorithmParameterException {
		initDecryptor();
		return dec.doFinal(indata, inoff, inlen);
	}

	/**
	 * decrypt the input data to output data.
	 * 
	 * @param indata
	 *            the input cipher data.
	 * @return the output plain data.
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] decrypt(byte[] indata) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		initDecryptor();
		return dec.doFinal(indata);
	}

	private void initEncryptor() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException {
		if (null == enc) {
			enc = Cipher.getInstance("AES/CBC/PKCS5Padding");
			enc.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
		}
	}

	private void initDecryptor() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException {
		if (null == dec) {
			dec = Cipher.getInstance("AES/CBC/PKCS5Padding");
			dec.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		}
	}

}
