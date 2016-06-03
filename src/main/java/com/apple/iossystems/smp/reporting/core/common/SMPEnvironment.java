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
    QA("qa", Arrays.asList(".qa", ".dev", ".perf")),
    PRODUCTION("production", Collections.singletonList(".prod")),
    CERTIFICATION("certification", Collections.singletonList(".prod.cert")),
    STAGING("staging", Collections.singletonList(".prod.stg")),
    UNKNOWN("unknown", Collections.<String>emptyList());

    private final String name;
    private final List<String> podNameValues;

    SMPEnvironment(String name, List<String> podNameValues)
    {
        this.name = name;
        this.podNameValues = podNameValues;
    }

    String getName()
    {
        return name;
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