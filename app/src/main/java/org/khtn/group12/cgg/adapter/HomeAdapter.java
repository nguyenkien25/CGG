package org.khtn.group12.cgg.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.khtn.group12.cgg.R;
import org.khtn.group12.cgg.activity.BookTicketActivity;
import org.khtn.group12.cgg.activity.HomeActivity;
import org.khtn.group12.cgg.activity.LoginRegisterActivity;
import org.khtn.group12.cgg.activity.MovieDetailActivity;
import org.khtn.group12.cgg.model.Movie;
import org.khtn.group12.cgg.utils.AppController;
import org.khtn.group12.cgg.utils.Constants;
import org.khtn.group12.cgg.utils.Utils;
import org.khtn.group12.cgg.widget.CustomButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> mListMovie;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name_movie)
        TextView tvName;
        @BindView(R.id.tv_movie_kind)
        TextView tvMovieKind;
        @BindView(R.id.tv_movie_time)
        TextView tvMovieTime;
        @BindView(R.id.tv_movie_premiere)
        TextView tvMoviePremiere;
        @BindView(R.id.imv_thumbnail)
        ImageView imvThumbnail;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public HomeAdapter(Context mContext, List<Movie> albumList) {
        this.mContext = mContext;
        this.mListMovie = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Movie movie = mListMovie.get(position);
        holder.tvName.setText(movie.getName());
        holder.tvMovieKind.setText(movie.getKind());
        holder.tvMovieTime.setText(movie.getTime());
        holder.tvMoviePremiere.setText(movie.getPremiere());

        // loading album cover using Glide library
        Glide.with(mContext)
                .load(movie.getImage())
                .apply(new RequestOptions()
                        .centerCrop()
                        .fitCenter()
                        .placeholder(android.R.drawable.ic_menu_report_image))
                .into(holder.imvThumbnail);

        holder.imvThumbnail.setOnClickListener(v -> showMovieDetail(movie));
    }

    private void showMovieDetail(Movie movie) {
        Intent intent = new Intent(mContext, MovieDetailActivity.class);
        intent.putExtra(Constants.INTENT_PUT_EXTRA_MOVIE, movie);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mListMovie.size();
    }
}
