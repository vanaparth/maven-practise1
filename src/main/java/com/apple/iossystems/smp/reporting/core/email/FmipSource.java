package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;

import java.math.BigInteger;

/**
 * @author Toch
 */
public enum FmipSource
{
    FMIP("1", "FMIP", getFmipSourceCertificate(ApplicationConfiguration.getFmipCertificate(), "1")),
    REMOTE("2", "SETUP_SERVICE", getFmipSourceCertificate(ApplicationConfiguration.getFmipRemoteCertificate(), "2"));

    private final String code;
    private final String description;
    private final BigInteger certificate;

    private static final String[] REMOTE_REQUEST_REASONS = {"Remove all cards."};

    private FmipSource(String code, String description, BigInteger certificate)
    {
        this.code = code;
        this.description = description;
        this.certificate = certificate;
    }

    private static FmipSource getDefaultSource()
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

    public static FmipSource fromCode(String code)
    {
        for (FmipSource e : values())
        {
            if (e.code.equals(code))
            {
                return e;
            }
        }

        return getDefaultSource();
    }

    public static FmipSource fromCertificate(BigInteger certificate)
    {
        for (FmipSource e : values())
        {
            if (e.certificate.equals(certificate))
            {
                return e;
            }
        }

        return getDefaultSource();
    }

    public static FmipSource fromRequestReason(String requestReason)
    {
        return ((requestReason != null) && listContainsRequestReason(REMOTE_REQUEST_REASONS, requestReason.trim())) ? REMOTE : FMIP;
    }

    private static boolean listContainsRequestReason(String[] requestReasons, String requestReason)
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

    private static BigInteger getFmipSourceCertificate(String certificate, String defaultValue)
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
        }

        return value;
    }
}