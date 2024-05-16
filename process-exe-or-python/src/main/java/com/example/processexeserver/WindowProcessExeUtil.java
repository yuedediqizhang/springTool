package com.example.processexeserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.io.InputStreamReader;

public class WindowProcessExeUtil {

    private static final Logger logger = LoggerFactory.getLogger(WindowProcessExeUtil.class);

    private final static int BUFFER_SIZE = 1024;



    private static class ProcessWorker extends Thread {
        private final Process process;
        private volatile int exitCode = -99;
        private volatile boolean completed = false;
        private volatile String output = "";
        private final String charset;
        private ProcessWorker(Process process, String charset) {
            this.process = process;
            this.charset = charset;
        }

        @Override
        public void run() {
            try (final InputStreamReader reader = new InputStreamReader(
                    process.getInputStream(), charset)) {

                final StringBuilder log = new StringBuilder();
                char[] buffer = new char[BUFFER_SIZE];
                int length;
                while ((length = reader.read(buffer)) != -1) {
                    log.append(buffer, 0, length);
                }
                output = log.toString();
                exitCode = process.waitFor();
                completed = true;
            } catch (InterruptedException | IOException e) {
                Thread.currentThread().interrupt();
            }
        }

        public int getExitCode() {
            return exitCode;
        }

        public String getOutput() {
            return output;
        }

        public boolean isCompleted() {
            return completed;
        }
    }


    public static String execCmd(final String[] command, final int timeoutSecond, @NonNull final String charset) {
        final StringBuilder result = new StringBuilder();
        final ProcessBuilder processBuilder = new ProcessBuilder(command);
        // 合并错误输出流
        processBuilder.redirectErrorStream(true);
        int exitCode = -99;
        ProcessWorker processWorker = null;
        Process process = null;
        try {
            process = processBuilder.start();
            processWorker = new ProcessWorker(process, charset);
            exitCode = processWorker.getExitCode();
            processWorker.start();
            processWorker.join(timeoutSecond * 1000);
            if (processWorker.isCompleted()) {
                result.append(processWorker.getOutput());
                exitCode = processWorker.getExitCode();
            }
        } catch (InterruptedException | IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (process != null) {
                process.destroy();
            }
            if (processWorker != null) {
                processWorker.interrupt();
            }
        }
        if (exitCode != 0) {
            logger.error("执行命令超时");
        }
        return result.toString();
    }
}
