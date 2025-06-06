package DnD.JavaCsvToJson;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Digest 
{
	private static BigInteger aDigestIndex = BigInteger.valueOf(0);

	public static String mNewDigestIndex(int pLength)
	{
		String vResult = "";
		try 
		{
            MessageDigest vMessageDigest = MessageDigest.getInstance("SHA-256");
            byte[] vByteArray = vMessageDigest.digest(aDigestIndex.toByteArray());
            aDigestIndex = aDigestIndex.add(BigInteger.valueOf(1));
            StringBuilder vHexString = new StringBuilder(2 * vByteArray.length);
            for(byte vByte : vByteArray)
            {
            	String vHex = Integer.toHexString(0xff & vByte);
            	if(vHex.length() == 1)
            	{
            		vHexString.append('0');
            	}
            	vHexString.append(vHex);
            }
            vResult = vHexString.toString();
            if(vResult.length() > pLength)
            {
            	vResult = vResult.substring(vResult.length() - pLength);
            }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return vResult;
	}
}
