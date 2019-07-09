package org.chobit.jspy;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jspy")
public class JSpyProperties {


    private String appCode;

    private String serverHost;

    private int serverPort;

    private boolean useSSL = false;

    private int memoryCollectIntervalSeconds = 6;

    private int threadCollectIntervalSeconds = 6;

    private int gcCollectIntervalSeconds = 60 * 5;

    private int classLoadingCollectIntervalSeconds = 6;

    private int methodHistogramPeriodSeconds = 60 * 5;


    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public boolean isUseSSL() {
        return useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public int getMemoryCollectIntervalSeconds() {
        return memoryCollectIntervalSeconds;
    }

    public void setMemoryCollectIntervalSeconds(int memoryCollectIntervalSeconds) {
        this.memoryCollectIntervalSeconds = memoryCollectIntervalSeconds;
    }

    public int getThreadCollectIntervalSeconds() {
        return threadCollectIntervalSeconds;
    }

    public void setThreadCollectIntervalSeconds(int threadCollectIntervalSeconds) {
        this.threadCollectIntervalSeconds = threadCollectIntervalSeconds;
    }

    public int getGcCollectIntervalSeconds() {
        return gcCollectIntervalSeconds;
    }

    public void setGcCollectIntervalSeconds(int gcCollectIntervalSeconds) {
        this.gcCollectIntervalSeconds = gcCollectIntervalSeconds;
    }

    public int getClassLoadingCollectIntervalSeconds() {
        return classLoadingCollectIntervalSeconds;
    }

    public void setClassLoadingCollectIntervalSeconds(int classLoadingCollectIntervalSeconds) {
        this.classLoadingCollectIntervalSeconds = classLoadingCollectIntervalSeconds;
    }

    public int getMethodHistogramPeriodSeconds() {
        return methodHistogramPeriodSeconds;
    }

    public void setMethodHistogramPeriodSeconds(int methodHistogramPeriodSeconds) {
        this.methodHistogramPeriodSeconds = methodHistogramPeriodSeconds;
    }
}
