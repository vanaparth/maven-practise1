package com.apple.iossystems.smp.reporting.core.common;

import com.apple.iossystems.smp.domain.keystone.SMPKeystoneConstants;

/**
 * @author Toch
 */
public class SMPEnvironmentConfiguration
{
    private static final SMPEnvironment SMP_ENVIRONMENT = getSMPEnvironment();

    private SMPEnvironmentConfiguration()
    {
    }

    private static SMPEnvironment getSMPEnvironment()
    {
        SMPEnvironment smpEnvironment = SMPEnvironment.fromPodName(SMPKeystoneConstants.SMP_XPOD_VALUE);

        return (smpEnvironment != null) ? smpEnvironment : SMPEnvironment.UNKNOWN;
    }

    public static String getEnvironmentName()
    {
        return SMP_ENVIRONMENT.getName();
    }
}