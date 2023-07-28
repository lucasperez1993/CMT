package util;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.io.UnsupportedEncodingException;

public class MD5
{
    public static String getEncodedString(final String key) {
        return getEncodedString(key.getBytes());
    }
    
    public static String getEncodedString(final String key, final String charsetName) {
        byte[] uniqueKey;
        try {
            uniqueKey = key.getBytes(charsetName);
        }
        catch (UnsupportedEncodingException e) {
            throw new Error(charsetName + " char set not supported");
        }
        return getEncodedString(uniqueKey);
    }
    
    protected static String getEncodedString(final byte[] uniqueKey) {
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
        }
        catch (NoSuchAlgorithmException e) {
            throw new Error("no MD5 support in this VM");
        }
        final StringBuffer hashString = new StringBuffer();
        for (int i = 0; i < hash.length; ++i) {
            final String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1) {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            }
            else {
                hashString.append(hex.substring(hex.length() - 2));
            }
        }
        return hashString.toString();
    }
}
