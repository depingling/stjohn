package com.cleanwise.service.apps.quartz;

import com.cleanwise.service.apps.GetFile;
import com.cleanwise.service.api.value.InboundData;
import com.cleanwise.service.api.session.Interchange;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.Utility;
import org.quartz.JobDetail;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionException;
import org.apache.log4j.Logger;


import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;


public abstract class JanPakFtpJob extends EventJobImpl implements JanPakEventJob, FtpJobProcess {

    private static final Logger log = Logger.getLogger(JanPakFtpJob.class);

    public static final String P_TABLE          = "table";
    public static final String P_PATTERN        = "pattern";
    public static final String P_USER           = "user";
    public static final String P_STORE          = "store";
    public static final String P_REPORT         = "report";
    public static final String P_HOSTNAME       = "fromhost";
    public static final String P_PORT           = "port";
    public static final String P_FROM_DIR       = "fromdir";
    public static final String P_PARTNER_KEY    = "partnerkey";
    public static final String P_PGP_CREDENTIAL = "pgpcredential";
    public static final String P_PASSWORD       = "password";
    public static final String P_SAVE_TO_DB     = "saveToDb";
    public static final String P_REMOVE_FILE    = "removefile";

    public static final String INBOUND_ID = "INBOUND_ID";

    public void execute(JobDetail jobDetail) throws JobExecutionException {
        try {

            ArrayList<String> errors = checkData(jobDetail);
            if (errors.size() > 0) {
                throw new Exception(createErrorMessage(errors));
            }

            InboundFile iFile = accept(jobDetail);
            if (isReadyForProcess(iFile)) {
                process(iFile, jobDetail);
            } else {
                log.info("execute => No data for process.");
            }

            if (isAccepted(iFile) && isRemoveFile(jobDetail)) {
                removeIFile(iFile, jobDetail);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new JobExecutionException(e);
        }
    }

    protected void saveInboundData(InboundFile file, JobDetail jobDetail) throws Exception {

        InboundData inbound = InboundData.createValue();

        inbound.setFileName(file.getFileName());
        inbound.setUrl(getURL(jobDetail));
        inbound.setPartnerKey(getPartnerKey(jobDetail));
        inbound.setDecryptBinaryData(file.getDecContent());
        inbound.setEncryptBinaryData(file.getEncContent());

        Interchange interchangeEjb = APIAccess.getAPIAccess().getInterchangeAPI();

        int inboundId = interchangeEjb.saveInboundData(inbound, "JanPakFtpJob");
        file.setInboundId(inboundId);
    }

    public boolean isNoFilesError(Exception e) {
        String s = e.getMessage();
        return s != null && (s.indexOf("No files found") >= 0 || s.indexOf("No such file") >= 0);
    }

    private String getPGPCredential(JobDetail jobDetail) {
        return (String) jobDetail.getJobDataMap().get(P_PGP_CREDENTIAL);
    }

    private String getURL(JobDetail jobDetail) {

        String url     = getHost(jobDetail);
        String port    = getPort(jobDetail);
        String fromDir = getFromDir(jobDetail);

        if (port != null) {
            url += ":" + port;
        }

        if (fromDir != null) {
            url += "/" + fromDir;
        }

        url += "/";

        return url;
    }

    public String getPartnerKey(JobDetail jobDetail) {
        return (String) jobDetail.getJobDataMap().get(P_PARTNER_KEY);
    }

    public String getRemoveFile(JobDetail jobDetail) {
        return (String) jobDetail.getJobDataMap().get(P_REMOVE_FILE);
    }

    public String getFromDir(JobDetail jobDetail) {
        return (String) jobDetail.getJobDataMap().get(P_FROM_DIR);
    }

    public String getHost(JobDetail jobDetail) {
        return (String) jobDetail.getJobDataMap().get(P_HOSTNAME);
    }

    public String getPort(JobDetail jobDetail) {
        return (String) jobDetail.getJobDataMap().get(P_PORT);
    }

    public String getPattern(JobDetail jobDetail) {
        return (String) jobDetail.getJobDataMap().get(P_PATTERN);
    }

    public String getTable(JobDetail jobDetail) {
        return (String) jobDetail.getJobDataMap().get(P_TABLE);
    }

    public String getUser(JobDetail jobDetail) {
        return (String) jobDetail.getJobDataMap().get(P_USER);
    }

    private String getSaveToDbFlag(JobDetail jobDetail) {
        return (String) jobDetail.getJobDataMap().get(P_SAVE_TO_DB);
    }

    public int getStore(JobDetail jobDetail) {
        try {
            return new Integer(jobDetail.getJobDataMap().getString(P_STORE));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String getPassword(JobDetail jobDetail) {
        return jobDetail.getJobDataMap().getString(P_PASSWORD);
    }

    protected void closeSession(GetFile getClient) throws Exception {
        if (getClient != null) {
            try {
                getClient.closeSession();
            } catch (IOException e) {
                e.printStackTrace();
                throw new Exception(e);
            }
        }
    }

    private void closeStream(ByteArrayOutputStream pStream) {
        if (pStream != null) {
            try {
                pStream.flush();
                pStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String,String> getSafePropertyMap(JobDetail jobDetail) {
        Map<String, String> properties = getPropertyMap(jobDetail.getJobDataMap());
        if (properties.containsKey(P_PASSWORD)) {
            properties.put(P_PASSWORD, "******");
        } 
       return properties;
    }

    public ArrayList<String> checkData(JobDetail jobDetail) {

        ArrayList<String> errors = new ArrayList<String>();

        if (jobDetail == null) {
            errors.add("Job Details is null.");
            return errors;
        }

        if (jobDetail.getJobDataMap() == null) {
            errors.add("Job Data Map is null.");
        }

        if (!Utility.isSet(getHost(jobDetail))) {
            errors.add(P_HOSTNAME + " a required field.");
        }

        if (!Utility.isSet(getUser(jobDetail))) {
            errors.add(P_USER + " a required field.");
        }

        if (!Utility.isSet(getPassword(jobDetail))) {
            errors.add(P_PASSWORD + " a required field.");
        }

        if (!Utility.isSet(getTable(jobDetail))) {
            errors.add(P_TABLE + " a required field.");
        }

        if (getStore(jobDetail) <= 0) {
            errors.add(P_STORE + " a required field.");
        }

        return errors;

    }

    public Map<String, String> getPropertyMap(JobDataMap dataMap) {
        Map<String, String> propertyMap = new HashMap<String, String>();
        if (dataMap != null && dataMap.size() != 0) {
            String[] keys = dataMap.getKeys();
            for (String key : keys) {
                propertyMap.put(key, dataMap.getString(key));
            }
        }
        return propertyMap;
    }

    public String createErrorMessage(ArrayList<String> errors) {
        String errMessage = "";
        for (String error : errors) {
            errMessage += error + " ";
        }
        return errMessage;
    }

    public InboundFile getIFile(JobDetail jobDetail) throws Exception {

        log.info("getIFile()=> BEGIN");

        GetFile getClient = null;
        InboundFile iFile = null;
        try {

            JobDataMap dataMap = jobDetail.getJobDataMap();
            Map<String, String> propertyMap = getPropertyMap(dataMap);

            getClient = new GetFile();
            getClient.setProperties(propertyMap);

            getClient.connect();

            String pPattern = getPattern(jobDetail);
            String fs[] = getClient.getFileNames();
            for (String fileName : fs) {
                if (Utility.isSet(pPattern) && fileName.indexOf(pPattern) >= 0) {
                    Date modDate = getClient.lastModified(fileName);
                    if (iFile != null) {
                        if (iFile.getLastMod().compareTo(modDate) < 0) {
                            iFile.setFileName(fileName);
                            iFile.setLastMod(modDate);
                        }
                    } else {
                        iFile = new InboundFile();
                        iFile.setFileName(fileName);
                        iFile.setLastMod(modDate);
                    }
                }
            }

            if (iFile != null) {
                byte[] content = readIFile(getClient, iFile.getFileName());
                if (isPGPCredential(jobDetail)) {
                    iFile.setEncContent(content);
                    iFile.setDecContent(decrypt(content, getPGPCredential(jobDetail)));
                } else {
                    iFile.setEncContent(null);
                    iFile.setDecContent(content);
                }
            }

        } catch (Exception e) {
            String errm = "Error getting file.";
            throw new Exception(errm, e);
        } finally {
            closeSession(getClient);
        }

        log.info("getIFile()=> END.");

        return iFile;
    }

    private byte[] decrypt(byte[] pContent, String pPGPCredential) {

        if (pContent == null) {
            return null;
        }

        PGPDecrypt decryptFactory = new PGPDecrypt();

        return decryptFactory.decrypt(pContent, pPGPCredential);

    }

    private byte[] readIFile(GetFile pFtpClient, String pFileName) throws Exception {

        log.info("readIFile()=> BEGIN.");

        byte[] data = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        try {
            log.info("readIFile()=> pFileName: " + pFileName);
            if (pFtpClient.readIFile(pFileName, stream)) {
                data = stream.toByteArray();
                log.info("readIFile()=> bytes read: " + data.length);
            } else {
                log.info("readIFile()=> data has not been read!");
            }
        } catch (Exception e) {
            if (!isNoFilesError(e)) {
                throw e;
            }
        } finally {
            closeStream(stream);
        }

        log.info("readIFile()=> END.");

        return data;

    }

    public boolean removeIFile(InboundFile pIFile, JobDetail pJobDetail) {

        GetFile getClient = null;

        if (pIFile != null) {

            try {

                JobDataMap dataMap = pJobDetail.getJobDataMap();
                Map<String, String> propertyMap = getPropertyMap(dataMap);

                getClient = new GetFile();
                getClient.setProperties(propertyMap);

                getClient.connect();

                return getClient.remove(pIFile.getFileName());

            } catch (Exception e) {
                log.error("removeIFile()=> error:" + e.getMessage());
            } finally {
                try {
                    closeSession(getClient);
                } catch (Exception e) {
                    log.error("removeIFile()=> error:" + e.getMessage());
                }
            }
        }

        return false;
    }

    public void showStartupInfo(JobDetail jobDetail) {

        String show = "";
        show += "\n****************************************************************************\n";
        show += jobDetail.getFullName() + "(" + jobDetail.getFullName() + ") exec at " + new java.util.Date() + "\n";
        show += "properties:" + getSafePropertyMap(jobDetail) + "\n";
        show += "****************************************************************************\n";

        log.info(show);
    }

    public boolean isPGPCredential(JobDetail jobDetail) {
        return Utility.isSet(getPGPCredential(jobDetail));
    }

    public boolean isRemoveFile(JobDetail jobDetail) {
        return Utility.isTrue(getRemoveFile(jobDetail));
    }

    public boolean saveToDb(JobDetail jobDetail) {
        return Utility.isTrue(getSaveToDbFlag(jobDetail),true);
    }

    public boolean isReadyForProcess(InboundFile pIFile) {
        return pIFile != null &&
                pIFile.getDecContent() != null &&
                pIFile.getDecContent().length > 0;
    }

    public boolean isAccepted(InboundFile pIFile) {
        return pIFile != null &&
                Utility.isSet(pIFile.getFileName()) &&
                pIFile.getDecContent() != null;

    }
}
