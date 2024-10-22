package com.allcear.alclectureapi.fileupload.event;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

//2024-10-14 1차 코드 정리 완료
@Slf4j
public class FileEventSource implements Runnable{
    public boolean keepRunning = true;
    private int updateInterval;
    //무슨파일을 모니터링할거냐
    private File file;

    //파일을어디까지 읽었냐
    private long filePointer = 0;

    // 파일의 이전 내용을 저장하는 리스트
    private List<String> previousLines = new ArrayList<>();
    private EventHandler eventHandler;



    public FileEventSource(int updateInterval, File file, EventHandler eventHandler) {
        this.updateInterval = updateInterval;
        this.file = file;
        this.eventHandler = eventHandler;
    }

    //스레드 시작 시 백그라운드에서 실행 -> FileEventSource가 Runnable인터페이스 구현 클래스이기 때문
    @Override
    public void run() {
        try {
            while (this.keepRunning) {
                Thread.sleep(this.updateInterval); // 업데이트 주기 설정
                processFileChanges();
            }
        } catch (InterruptedException e) {
            log.error("Thread was interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // Interrupted 상태 복원
        } catch (Exception e) {
            log.error("An error occurred: " + e.getMessage());
        }
    }

    private void processFileChanges() throws IOException, ExecutionException, InterruptedException {
        long len = file.length();

        if (len < filePointer) {
            log.info("Log file was reset. Resetting file pointer.");
            filePointer = 0; // 파일이 초기화된 경우 포인터를 0으로
        } else if (len != filePointer) {
            readAppendAndSend(); // 파일이 수정된 경우 내용 읽기 및 전송
        }
    }
    private void readAppendAndSend() throws IOException, ExecutionException, InterruptedException {
        List<String> currentLines = readFileLines(); // 현재 파일의 모든 라인을 읽음

        // 변경된 부분만 처리
        processChangedLines(currentLines);

        previousLines = currentLines; // 이전 라인 리스트 업데이트
    }
    private List<String> readFileLines() throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines; // 파일의 모든 라인 반환
    }

    private void processChangedLines(List<String> currentLines) throws ExecutionException, InterruptedException {
        // 변경된 라인 비교 및 메시지 전송
        int minLength = Math.min(previousLines.size(), currentLines.size());

        for (int i = 0; i < minLength; i++) {
            if (!Objects.equals(previousLines.get(i), currentLines.get(i))) {
                sendMessage(currentLines.get(i)); // 변경된 라인 전송
            }
        }

        // 새롭게 추가된 라인 처리
        for (int i = previousLines.size(); i < currentLines.size(); i++) {
            sendMessage(currentLines.get(i)); // 새로운 라인 전송
        }
    }

    private void sendMessage(String line) throws ExecutionException, InterruptedException {
        // Split the line by commas
        String[] tokens = line.split(",");

        //키 추출
        String key = tokens[0].trim();

        String value = String.join(",", Arrays.stream(tokens, 1, tokens.length).map(String::trim).toArray(String[]::new)); // 값 추출

        //MessageEvent 객체 생성
        MessageEvent messageEvent = new MessageEvent(key, value);

        this.eventHandler.onMessage(messageEvent);
    }

}
