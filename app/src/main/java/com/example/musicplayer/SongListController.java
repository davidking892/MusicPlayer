package com.example.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class SongListController extends RecyclerView.Adapter<SongListController.myHolder> {

   private ArrayList<String> directory;
   private ArrayList<File> songList;
   private Context context;
   private MediaMetadataRetriever mediaMetadataRetriever;
   private byte[] arts;
    private String[] song;

    public SongListController(Context context, ArrayList<File> songList, ArrayList<String> directory){
        this.context=context;
        this.songList=songList;
        this.directory=directory;

        removeExtension();
    }

    @NonNull
    @Override
    public SongListController.myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View view= LayoutInflater.from(context).inflate(R.layout.song_container,parent,false);
          return new myHolder(view);
    }

    private void removeExtension(){
        song=new String[songList.size()];
        for(int i=0;i<song.length;i++){

            song[i]=songList.get(i).getName().replace(".mp3","");
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final SongListController.myHolder holder, final int position) {

        mediaMetadataRetriever=new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(directory.get(position));
        holder.song_container_name.setText(song[position]);
        try{
            arts=mediaMetadataRetriever.getEmbeddedPicture();
            Bitmap imageCode= BitmapFactory.decodeByteArray(arts,0,arts.length);
            holder.song_container_image.setImageBitmap(imageCode);
        }catch(Exception e) {
             holder.song_container_image.setImageResource(R.drawable.music_icon);
        }

         holder.songContainer.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                Intent intent=new Intent(context,Player.class);
                intent.putExtra("position",position);
                 intent.putExtra("songsName",songList);
                 intent.putExtra("directory",directory);
                 context.startActivity(intent);
             }
         });

    }

    @Override
    public int getItemCount() {
        return song.length;
    }

    public static class myHolder extends RecyclerView.ViewHolder{

        private ImageView song_container_image;
        private TextView song_container_name;
        private LinearLayout songContainer;


        public myHolder(@NonNull View itemView) {
            super(itemView);
            song_container_image=itemView.findViewById(R.id.song_container_musicAlbum);
            song_container_name=itemView.findViewById(R.id.song_container_name);
            songContainer=itemView.findViewById(R.id.songContainer);
        }



    }

}

