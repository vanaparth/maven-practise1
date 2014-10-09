package com.apple.iossystems.smp.reporting.core.timer;

import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class StopWatch
{
    private static final Logger LOGGER = Logger.getLogger(StopWatch.class);

    private long startTime;
    private long endTime;

    private State state = State.INIT;

    private StopWatch()
    {
    }

    public static StopWatch getInstance()
    {
        return new StopWatch();
    }

    public void start()
    {
        long time = System.currentTimeMillis();

        if (setState(State.INIT, State.START, "Start not allowed"))
        {
            startTime = time;
        }
    }

    public void stop()
    {
        long time = System.currentTimeMillis();

        if (setState(State.START, State.STOP, "Stop not allowed"))
        {
            endTime = time;
        }
    }

    private boolean setState(State startState, State endState, String errorMessage)
    {
        boolean result = true;

        if (state == startState)
        {
            state = endState;
        }
        else
        {
            result = false;

            LOGGER.info(errorMessage);
        }

        return result;
    }

    public long getTimeMillis()
    {
        return (endTime - startTime);
    }

    private enum State
    {
        INIT,
        START,
        STOP;

        private State()
        {
        }
    }
}