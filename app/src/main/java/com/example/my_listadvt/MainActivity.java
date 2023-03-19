package com.example.my_listadvt;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private VideoView videoView;
    private ProgressBar progressBar;
    private ArrayList<String> videoList = new ArrayList<>();
    private int currentVideo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        progressBar = findViewById(R.id.progressBar);

        videoList.add("https://drive.google.com/uc?id=1R0KvXarAoqzrE9-qop4J1pmHPqthc1hi&export=download");
        videoList.add("https://drive.google.com/uc?id=1Q27Efw0C3uQuccdyOZoJ1krQty_mDLax&export=download");
        videoList.add("https://drive.google.com/uc?id=1QOFecl7OG0oHSxpEixss8NUhyvLgpuxg&export=download");
        videoList.add("https://drive.google.com/uc?id=1QDRpCt7vPqTLl6Kw1mBkjO0YaA5Q4rDr&export=download");
        videoList.add("https://drive.google.com/uc?id=1Qj8__Cxp8DYpeqR-wJ2hIfIBG1Jz0f-J&export=download");
        videoList.add("https://drive.google.com/uc?id=1QMGIDprE2bCZxE90LN5ykuHrDIhSxR3O&export=download");
        videoList.add("https://drive.google.com/uc?id=1QRguw6TFHcNk--ySc1T8PYKl-IT1YbTL&export=download");
        videoList.add("https://drive.google.com/uc?id=1QA-LxR7i3ZgMddnFtq6CvJNB0987gn5u&export=download");
        videoList.add("https://drive.google.com/uc?id=1QPVs5a_W7qWLq-SHVsANJ-i6rOHUBP8O&export=download");
        loadVideo();
    }

    private void loadVideo() {
        progressBar.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.INVISIBLE);

        Uri uri = Uri.parse(videoList.get(currentVideo));
        new DownloadVideoTask(this, uri).execute();
    }

    private void playVideo(Uri uri) {
        progressBar.setVisibility(View.INVISIBLE);
        videoView.setVisibility(View.VISIBLE);

        videoView.setVideoURI(uri);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                currentVideo++;
                if (currentVideo >= videoList.size()) {
                    currentVideo = 0;
                }
                loadVideo();
            }
        });

        videoView.start();
    }

    private static class DownloadVideoTask extends AsyncTask<Void, Void, Uri> {
        private Context context;
        private Uri uri;

        DownloadVideoTask(Context context, Uri uri) {
            this.context = context;
            this.uri = uri;
        }

        @Override
        protected Uri doInBackground(Void... voids) {
            try {
                URL url = new URL(uri.toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                String filename = "video" + System.currentTimeMillis() + ".mp4";
                return FileHelper.saveFile(inputStream, filename);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Uri result) {
            super.onPostExecute(result);
            if (result != null) {
                ((MainActivity) context).playVideo(result);
            }
        }
    }
}
