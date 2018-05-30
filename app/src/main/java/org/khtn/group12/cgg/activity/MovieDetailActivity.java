package org.khtn.group12.cgg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.khtn.group12.cgg.R;
import org.khtn.group12.cgg.model.Movie;
import org.khtn.group12.cgg.utils.Constants;
import org.khtn.group12.cgg.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetailActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbarMovieDetail;
    @BindView(R.id.btn_get_ticket)
    Button btnGetTicket;
    @BindView(R.id.tv_movie_name)
    TextView tvMovieName;
    @BindView(R.id.tv_movie_info)
    TextView tvMovieInfo;
    @BindView(R.id.tv_movie_premiere)
    TextView tvMoviePremiere;
    @BindView(R.id.tv_movie_kind)
    TextView tvMovieKind;
    @BindView(R.id.tv_movie_directors)
    TextView tvMovieDirectors;
    @BindView(R.id.tv_movie_cast)
    TextView tvMovieCast;
    @BindView(R.id.tv_movie_time)
    TextView tvMovieTime;
    @BindView(R.id.tv_movie_language)
    TextView tvMovieLanguage;

    private YouTubePlayerSupportFragment youTubeMovieTrailer;
    private String idVideo = "";
    private Movie movie;

    @OnClick(R.id.btn_get_ticket)
    void onClickGetTicket() {
        if (Utils.checkUserLogin()) {
            Intent intent = new Intent(this, BookTicketActivity.class);
            intent.putExtra(Constants.INTENT_PUT_EXTRA_MOVIE_ID, movie.getId());
            this.startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginRegisterActivity.class);
            intent.putExtra(Constants.FLAG_LOGIN, Constants.LOGIN);
            this.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbarMovieDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getMovie();
        initYoutubePlayer();
    }

    private void getMovie() {
        Intent intent = getIntent();
        if (intent != null) {
            movie = (Movie) intent.getSerializableExtra(Constants.INTENT_PUT_EXTRA_MOVIE);
            tvMovieName.setText(movie.getName());
            tvMovieInfo.setText(movie.getInfo());
            tvMoviePremiere.setText(movie.getPremiere());
            tvMovieKind.setText(movie.getKind());
            tvMovieDirectors.setText(movie.getDirectors());
            tvMovieCast.setText(movie.getCast());
            tvMovieTime.setText(movie.getTime());
            tvMovieLanguage.setText(movie.getLanguage());
            idVideo = movie.getLink_trailer();
        }
    }

    private void initYoutubePlayer() {
        youTubeMovieTrailer = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_view_movie_trailer);
        youTubeMovieTrailer.initialize(getString(R.string.youtube_api_key), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(idVideo);
        youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {

            @Override
            public void onLoading() {
                youTubePlayer.pause();
            }

            @Override
            public void onLoaded(String s) {

            }

            @Override
            public void onAdStarted() {

            }

            @Override
            public void onVideoStarted() {

            }

            @Override
            public void onVideoEnded() {

            }

            @Override
            public void onError(YouTubePlayer.ErrorReason errorReason) {

            }
        });
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
