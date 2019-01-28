package br.com.kleberjunio.acampamentoadventista.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import br.com.kleberjunio.acampamentoadventista.R;
import br.com.kleberjunio.acampamentoadventista.helper.YouTubeConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayerActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView playerVideo;
    private String idVideo;
    private TextView title;
    private TextView descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        //Incializa componentes
        playerVideo = findViewById(R.id.playerVideo);
        title = findViewById(R.id.video_title);
        descricao = findViewById(R.id.video_descricao);

        //Recupera valores
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            idVideo = bundle.getString("idVideo");
            title.setText(bundle.getString("title"));
            descricao.setText(bundle.getString("descricao"));

            playerVideo.initialize(YouTubeConfig.CHAVE_YOUTUBE_API, this);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
            youTubePlayer.loadVideo(idVideo);
        }
    }
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(PlayerActivity.this, youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
    }
}
