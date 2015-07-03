package ferramentas;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {

	private static MessageDigest criptografia;
	
	public static String criptografarDado(String dado) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		
		criptografia = MessageDigest.getInstance("MD5");
		byte[] dadoCriptografadoEmBytes = criptografia.digest(dado.getBytes("UTF-8"));
		
		StringBuilder conversorByteParaHexaDecimal = new StringBuilder();
		for (byte b : dadoCriptografadoEmBytes) {
		  conversorByteParaHexaDecimal.append(String.format("%02X", 0xFF & b));
		}
		return conversorByteParaHexaDecimal.toString();
	}
	
}
