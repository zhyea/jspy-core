package org.chobit.jspy;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jspy")
public class JSpyProperties {

    private boolean enable = true;

    private String appCode;

    private String serverHost = "127.0.0.1";

    private int serverPort = 8190;

    private boolean useSsl = false;

    private int startDelayedSeconds = 60;

    private int memoryCollectIntervalSeconds = 6;

    private int threadCollectIntervalSeconds = 6;

    private int gcCollectIntervalSeconds = 60 * 5;

    private int classLoadingCollectIntervalSeconds = 6;

    private int cpuUsageCollectIntervalSeconds = 6;

    private int messageSendIntervalSeconds = 60;

    private WatcherConfig watcher;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

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

    public boolean isUseSsl() {
        return useSsl;
    }

    public void setUseSsl(boolean useSsl) {
        this.useSsl = useSsl;
    }

    public int getStartDelayedSeconds() {
        return startDelayedSeconds;
    }

    public void setStartDelayedSeconds(int startDelayedSeconds) {
        this.startDelayedSeconds = startDelayedSeconds;
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

    public int getCpuUsageCollectIntervalSeconds() {
        return cpuUsageCollectIntervalSeconds;
    }

    public void setCpuUsageCollectIntervalSeconds(int cpuUsageCollectIntervalSeconds) {
        this.cpuUsageCollectIntervalSeconds = cpuUsageCollectIntervalSeconds;
    }

    public int getMessageSendIntervalSeconds() {
        return messageSendIntervalSeconds;
    }

    public void setMessageSendIntervalSeconds(int messageSendIntervalSeconds) {
        this.messageSendIntervalSeconds = messageSendIntervalSeconds;
    }

    public WatcherConfig getWatcher() {
        return watcher;
    }

    public void setWatcher(WatcherConfig watcher) {
        this.watcher = watcher;
    }
}
