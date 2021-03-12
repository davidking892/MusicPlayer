package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;

public class Player extends AppCompatActivity {

    private ImageView playerImage,isRunning,next,prev;
    private SeekBar seekBar;
    private Thread thread;
    private MediaPlayer mediaPlayer;
    private ArrayList<File> songs;
    private ArrayList<String> directory;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        initComponent();

        thread=new Thread(){
          public void run(){
             int currentPosition=0;
             while(currentPosition>mediaPlayer.getDuration()){
                  try{
                      sleep(500);
                      currentPosition=mediaPlayer.getCurrentPosition();
                      seekBar.setProgress(currentPosition);
                  }catch (InterruptedException e){
                      e.printStackTrace();
                  }
             }
          }
        };
      //  thread.start();

        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent intent=getIntent();
        directory=intent.getStringArrayListExtra("directory");
        songs=(ArrayList<File>) intent.getSerializableExtra("songsName");
        position=intent.getIntExtra("songsName",0);

        Uri uri=Uri.parse(songs.get(position).toString());
        mediaPlayer=MediaPlayer.create(Player.this,uri);
        mediaPlayer.start();

    }

    private void initComponent(){
        playerImage=findViewById(R.id.player_image);
        next=findViewById(R.id.next);
        prev=findViewById(R.id.prev);
        isRunning=findViewById(R.id.isRunning);
        directory=new ArrayList<>();
    }

}
