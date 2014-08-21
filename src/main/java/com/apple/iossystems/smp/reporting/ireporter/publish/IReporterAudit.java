package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Toch
 */
public class IReporterAudit
{
    private final int sentCount;

    private final int failedCount;

    private final int backlogCount;

    private final int lostCount;

    private IReporterAudit(Builder builder)
    {
        sentCount = builder.sentCount;
        failedCount = builder.failedCount;
        backlogCount = builder.backlogCount;
        lostCount = builder.lostCount;
    }

    public String toJson()
    {
        return new GsonBuilder().create().toJson(createAuditRequest());
    }

    private AuditRequest createAuditRequest()
    {
        AuditRequest result = new AuditRequest();

        result.addAuditRecord(new AuditRecord(sentCount, failedCount, backlogCount, lostCount));

        return result;
    }

    public static Builder getBuilder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private int sentCount;
        private int failedCount;
        private int backlogCount;
        private int lostCount;

        private Builder()
        {
        }

        public Builder sentCount(int value)
        {
            sentCount = value;
            return this;
        }

        public Builder failedCount(int value)
        {
            failedCount = value;
            return this;
        }

        public Builder backlogCount(int value)
        {
            backlogCount = value;
            return this;
        }

        public Builder lostCount(int value)
        {
            lostCount = value;
            return this;
        }

        public IReporterAudit build()
        {
            return new IReporterAudit(this);
        }
    }

    private static class AuditRequest
    {
        @SerializedName("Version")
        private final String version = "1.0";

        @SerializedName("txn")
        private List<AuditRecord> list = new ArrayList<AuditRecord>();

        private AuditRequest()
        {
        }

        private void addAuditRecord(AuditRecord record)
        {
            list.add(record);
        }
    }

    private static class AuditRecord
    {
        private int sent;
        private int failed;
        private int backlog;
        private int lost;

        private AuditRecord()
        {
        }

        private AuditRecord(int sent, int failed, int backlog, int lost)
        {
            this.sent = sent;
            this.failed = failed;
            this.backlog = backlog;
            this.lost = lost;
        }
    }
}