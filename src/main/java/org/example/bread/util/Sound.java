package org.example.bread.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URL;

public class Sound extends Object {

    private Media media;/* 相當於媒體播放器*/
    private MediaPlayer mediaPlayer;/* 相當於媒體控制器*/

    /*
     播放音樂
     */
    public void play() {
        mediaPlayer.play(); /* JavaFX提供的MediaPlayer有播放方法，直接調用即可 */
    }

    /*
      暫停播放
     */
    public void pause() {
        mediaPlayer.pause();/* JavaFX提供的MediaPlayer有暫停方法，直接調用即可*/
    }

    /*
      停止播放
     */
    public void stop() {
        mediaPlayer.stop();/* JavaFX提供的MediaPlayer有停止播放方法，直接調用即可*/
    }

    /*
      循環播放
     */
    public void loop() {
        setPlayCount(MediaPlayer.INDEFINITE);/* this的方法*/
        play();/* this的方法*/
    }

    /*
      獲取現在音樂播放到哪裏了

      @return 事件(單位秒)
     */
    public double getNewTime() {
        return mediaPlayer.getCurrentTime().toSeconds();
    }

    /*
      設置音樂的聲音大小0-1

      @param v
                 音量
     */
    public void setVolume(double v) {
        mediaPlayer.setVolume(v);
    }

    /*

      @param count
                 設置媒體文件循環播放的次數
     */
    public void setPlayCount(int count) {
        mediaPlayer.setCycleCount(count);
    }

    /*
      釋放媒體文件佔用的空間
     */
    public void close() {
        mediaPlayer.dispose(); /* 不是用Media類來釋放內存，要用MediaPlayer控制類來釋放內存*/
        System.gc(); /* 通知JVM內存回收,調用了dispose方法內存並不會馬上被回收，會被標記成垃圾，等待下一次垃圾回收的執行,這裏我們手動調用*/
    }

    /*

      @param URL
                 媒體文件目錄，本地文件也要用URL(String)路徑
     */
    public Sound(String URL, boolean isAutoPlay) {
        this.media = new Media(URL);
        this.mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setAutoPlay(isAutoPlay);
    }

    /*

      @param URL
                 雖然直接傳入的事URL，但是是不能直接使用，要轉成String類型的URL
     */
    public Sound(URL URL, boolean isAutoPlay) {
        this(URL.toString(), isAutoPlay);
    }

    /*

      @param file
                 最終轉換成URL(String)路徑
     */
    public Sound(File file, boolean isAutoPlay) {
        this(file.toURI().toString(), isAutoPlay);
    }

}