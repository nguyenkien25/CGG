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

import org.khtn.group12.cgg.R;
import org.khtn.group12.cgg.activity.MovieDetailActivity;
import org.khtn.group12.cgg.model.Movie;
import org.khtn.group12.cgg.model.NumberBook;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookTicketAdapter extends RecyclerView.Adapter<BookTicketAdapter.MyViewHolder> {

    private Context mContext;
    private List<NumberBook> mListBookTicket;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imv_book)
        ImageView imvBook;
        @BindView(R.id.tv_number)
        TextView tvNumber;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public List<NumberBook> getmListBookTicket() {
        return mListBookTicket;
    }

    public BookTicketAdapter(Context mContext, List<NumberBook> bookTicketList) {
        this.mContext = mContext;
        this.mListBookTicket = bookTicketList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_book_ticket_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        NumberBook numberBook = mListBookTicket.get(position);
        if (numberBook.isBook()) {
            holder.imvBook.setBackgroundResource(R.drawable.ic_person_book);
        } else {
            if (numberBook.isSelect()) {
                holder.imvBook.setBackgroundResource(R.drawable.ic_person_select);
            } else {
                holder.imvBook.setBackgroundResource(R.drawable.ic_person_non_book);
            }
        }
        holder.tvNumber.setText(numberBook.getNameNumber());

        holder.imvBook.setOnClickListener(v -> {
            if (!numberBook.isBook()) {
                mListBookTicket.get(position).setSelect(mListBookTicket.get(position).isSelect() ? false : true);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListBookTicket.size();
    }
}
