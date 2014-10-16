package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfigurationManager;
import org.apache.log4j.Logger;

import java.math.BigInteger;

/**
 * @author Toch
 */
public enum FmipSource
{
    FMIP("1", "FMIP", initFmipSourceCertificate(ApplicationConfigurationManager.getFmipCertificate(), "1")),
    REMOTE("2", "SETUP_SERVICE", initFmipSourceCertificate(ApplicationConfigurationManager.getFmipRemoteCertificate(), "2"));

    private final String code;
    private final String description;
    private final BigInteger certificate;

    private static final Logger LOGGER = Logger.getLogger(FmipSource.class);

    private static final String[] REMOTE_REQUEST_REASONS = {"Remove all cards."};

    private FmipSource(String code, String description, BigInteger certificate)
    {
        this.code = code;
        this.description = description;
        this.certificate = certificate;
    }

    private static FmipSource getUnknownSource()
    {
        return FMIP;
    }

    public String getCode()
    {
        return code;
    }

    public String getDescription()
    {
        return description;
    }

    public static FmipSource getFmipSourceFromCode(String code)
    {
        for (FmipSource e : FmipSource.values())
        {
            if (e.code.equals(code))
            {
                return e;
            }
        }

        return getUnknownSource();
    }

    public static FmipSource getFmipSourceFromCertificate(BigInteger certificate)
    {
        for (FmipSource e : FmipSource.values())
        {
            if (e.certificate.equals(certificate))
            {
                return e;
            }
        }

        return getUnknownSource();
    }

    public static FmipSource getFmipSourceFromRequestReason(String requestReason)
    {
        if (requestReason != null)
        {
            requestReason = requestReason.trim();

            if (requestReasonInRequestReasonList(requestReason, REMOTE_REQUEST_REASONS))
            {
                return REMOTE;
            }
        }

        return FMIP;
    }

    private static boolean requestReasonInRequestReasonList(String requestReason, String[] requestReasons)
    {
        for (String entry : requestReasons)
        {
            if (entry.equalsIgnoreCase(requestReason))
            {
                return true;
            }
        }

        return false;
    }

    private static BigInteger initFmipSourceCertificate(String certificate, String defaultValue)
    {
        return (!certificate.equals("0")) ? certificateToBigInteger(certificate) : certificateToBigInteger(defaultValue);
    }

    private static BigInteger certificateToBigInteger(String certificate)
    {
        BigInteger value;

        try
        {
            value = new BigInteger(certificate, 16);
        }
        catch (Exception e)
        {
            value = new BigInteger("0");

            LOGGER.error(e);
        }

        return value;
    }
}