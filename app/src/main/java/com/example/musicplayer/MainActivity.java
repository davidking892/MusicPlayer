package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> songsDirectory;
    private SongListController songListController;
    private ArrayList<File> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();

        songListController=new SongListController(MainActivity.this,songList,songsDirectory);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(songListController);

    }


    private void initComponent(){
        songsDirectory=new ArrayList<>();
        recyclerView=findViewById(R.id.mainRecyclerView);
        songList=getList(new File("/sdcard"));

    }

    private ArrayList<File> getList(File rootDirectory){

        ArrayList<File> arrayList=new ArrayList<>();
        File[] files=rootDirectory.listFiles();

        for(File file:files){

            if(file.isDirectory()&&!file.isHidden()){
                arrayList.addAll(getList(file));
            }else{
                if(file.getName().endsWith(".mp3")){
                    String dir=file.getParent()+"/"+file.getName();
                    songsDirectory.add(dir);
                    arrayList.add(file);

                }

            }
        }
        return arrayList;
    }

}
