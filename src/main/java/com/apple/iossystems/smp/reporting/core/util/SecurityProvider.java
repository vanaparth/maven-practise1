package com.apple.iossystems.smp.reporting.core.util;

import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfigurationManager;
import com.apple.iossystems.smp.utils.CryptoUtils;
import com.apple.iossystems.smp.utils.PBKDF2Util;
import org.apache.log4j.Logger;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.Charset;

/**
 * @author Toch
 */
public class SecurityProvider
{
    private static final Logger LOGGER = Logger.getLogger(SecurityProvider.class);

    private static final int HASH_ITERATIONS = 10;

    private static final char[] PASSWORD = ApplicationConfigurationManager.getHashPassword().toCharArray();

    private SecurityProvider()
    {
    }

    public static String getHash(String input)
    {
        String output = "";

        try
        {
            byte[] salt = CryptoUtils.getDigest("SHA-256", input.getBytes(Charset.forName("UTF-8")));
            byte[] bytes = PBKDF2Util.hashWithDigest(new SHA256Digest(), PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(PASSWORD), salt, HASH_ITERATIONS, 32);

            output = new String(Hex.encode(bytes));
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }

        return output;
    }
}