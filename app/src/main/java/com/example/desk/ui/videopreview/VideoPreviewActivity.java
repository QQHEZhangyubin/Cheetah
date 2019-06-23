package com.example.desk.ui.videopreview;


import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.desk.R;
import com.example.desk.mvp.MVPBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class VideoPreviewActivity extends MVPBaseActivity<VideoPreviewContract.View, VideoPreviewPresenter> implements VideoPreviewContract.View, View.OnClickListener, TextureView.SurfaceTextureListener, MediaPlayer.OnCompletionListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.preview_video)
    TextureView surfaceView;
    @BindView(R.id.previre_play)
    ImageView imagePlay;
    @BindView(R.id.preview_video_parent)
    RelativeLayout previewVideoParent;
    public static final String VIDEO_PATH = "path";
    private MediaPlayer mediaPlayer;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ffmpeg_preview);
        ButterKnife.bind(this);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        surfaceView = (TextureView) findViewById(R.id.preview_video);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        RelativeLayout preview_video_parent = (RelativeLayout) findViewById(R.id.preview_video_parent);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) preview_video_parent
                .getLayoutParams();
        layoutParams.width = displaymetrics.widthPixels;
        layoutParams.height = displaymetrics.widthPixels;
        preview_video_parent.setLayoutParams(layoutParams);

        surfaceView.setSurfaceTextureListener(this);
        surfaceView.setOnClickListener(this);
        path = getIntent().getStringExtra(VIDEO_PATH);
        imagePlay = (ImageView) findViewById(R.id.previre_play);
        imagePlay.setOnClickListener(this);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        this.setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("查看视频");
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
        }
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.previre_play) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
            imagePlay.setVisibility(View.GONE);
        } else if (id == R.id.preview_video) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                imagePlay.setVisibility(View.VISIBLE);
            }
        }
    }
    private void prepare(Surface surface) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 设置需要播放的视频
            mediaPlayer.setDataSource(path);
            // 把视频画面输出到Surface
            mediaPlayer.setSurface(surface);
            mediaPlayer.setLooping(false);
            mediaPlayer.prepare();
            mediaPlayer.seekTo(0);
        } catch (Exception e) {
        }
    }
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture arg0, int width, int height) {
        prepare(new Surface(arg0));
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
    private void stop() {
        mediaPlayer.stop();
    }
    @Override
    public void onBackPressed() {
        stop();
        super.onBackPressed();
    }
    @Override
    public void onCompletion(MediaPlayer mp) {
        imagePlay.setVisibility(View.VISIBLE);
    }
}
