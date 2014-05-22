package com.cleanwise.service.apps.quartz;

import org.quartz.JobDetail;

import java.util.Date;


public interface FtpJobProcess {

    public InboundFile accept(JobDetail jobDetail) throws Exception;
    public void process(InboundFile file, JobDetail jobDetail) throws Exception;

    public class InboundFile {

        private String fileName;
        private Date lastMod;
        private byte[] encContent;
        private byte[] decContent;
        private int inboundId;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public Date getLastMod() {
            return lastMod;
        }

        public void setLastMod(Date lastMod) {
            this.lastMod = lastMod;
        }

        public byte[] getEncContent() {
            return encContent;
        }

        public void setEncContent(byte[] encContent) {
            this.encContent = encContent;
        }

        public byte[] getDecContent() {
            return decContent;
        }

        public void setDecContent(byte[] decContent) {
            this.decContent = decContent;
        }

        public int getInboundId() {
            return inboundId;
        }

        public void setInboundId(int inboundId) {
            this.inboundId = inboundId;
        }
    }

}
