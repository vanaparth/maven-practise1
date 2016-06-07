package com.apple.iossystems.smp.reporting.core.common;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Toch
 */
enum SMPEnvironment
{
    PRODUCTION("1", Collections.singletonList(".prod")),
    STAGE("2", Collections.singletonList(".prod.stg")),
    CERTIFICATION("3", Collections.singletonList(".prod.cert")),
    QA("4", Arrays.asList(".qa", ".dev", ".perf")),
    UNKNOWN("", Collections.<String>emptyList());

    private final String code;
    private final List<String> podNameValues;

    SMPEnvironment(String code, List<String> podNameValues)
    {
        this.code = code;
        this.podNameValues = podNameValues;
    }

    String getCode()
    {
        return code;
    }

    static SMPEnvironment fromPodName(String podName)
    {
        SMPEnvironment result = UNKNOWN;
        String resultString = "";

        if (StringUtils.isNotBlank(podName))
        {
            podName = podName.toLowerCase().trim();

            for (SMPEnvironment smpEnvironment : values())
            {
                for (String podNameValue : smpEnvironment.podNameValues)
                {
                    if (podName.contains(podNameValue))
                    {
                        if ((result == UNKNOWN) || (resultString.length() < podNameValue.length()))
                        {
                            result = smpEnvironment;
                            resultString = podNameValue;
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }
}