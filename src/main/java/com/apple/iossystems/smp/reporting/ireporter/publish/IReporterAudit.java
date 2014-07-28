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

    private IReporterAudit(Builder builder)
    {
        sentCount = builder.sentCount;
        failedCount = builder.failedCount;
        backlogCount = builder.backlogCount;
    }

    public String toJson()
    {
        return new GsonBuilder().create().toJson(createAuditRequest());
    }

    private AuditRequest createAuditRequest()
    {
        AuditRequest result = new AuditRequest();

        result.addAuditRecord(new AuditRecord(sentCount, failedCount, backlogCount));

        return result;
    }

    public static class Builder
    {
        private int sentCount;
        private int failedCount;
        private int backlogCount;

        private Builder()
        {
        }

        public static Builder getInstance()
        {
            return new Builder();
        }

        public Builder sentCount(int val)
        {
            sentCount = val;
            return this;
        }

        public Builder failedCount(int val)
        {
            failedCount = val;
            return this;
        }

        public Builder backlogCount(int val)
        {
            backlogCount = val;
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

        private AuditRecord()
        {
        }

        private AuditRecord(int sent, int failed, int backlog)
        {
            this.sent = sent;
            this.failed = failed;
            this.backlog = backlog;
        }
    }
}