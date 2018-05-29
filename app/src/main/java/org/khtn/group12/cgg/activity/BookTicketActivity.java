package org.khtn.group12.cgg.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.khtn.group12.cgg.R;
import org.khtn.group12.cgg.adapter.BookTicketAdapter;
import org.khtn.group12.cgg.model.NumberBook;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookTicketActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_a)
    RecyclerView mRecyclerViewA;
    @BindView(R.id.recycler_view_b)
    RecyclerView mRecyclerViewB;
    @BindView(R.id.recycler_view_c)
    RecyclerView mRecyclerViewC;
    @BindView(R.id.recycler_view_d)
    RecyclerView mRecyclerViewD;
    @BindView(R.id.recycler_view_e)
    RecyclerView mRecyclerViewE;
    @BindView(R.id.recycler_view_f)
    RecyclerView mRecyclerViewF;
    @BindView(R.id.toolbar)
    Toolbar mToolbarBookTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbarBookTicket);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initRecycleViews();
    }

    private void initRecycleViews() {
        List<NumberBook> numberBooks = new ArrayList<>();
        numberBooks.add(new NumberBook(true, "1", false));
        numberBooks.add(new NumberBook(false, "2", true));
        numberBooks.add(new NumberBook(false, "3", true));
        numberBooks.add(new NumberBook(true, "4", false));
        numberBooks.add(new NumberBook(true, "5", false));
        numberBooks.add(new NumberBook(true, "6", false));
        numberBooks.add(new NumberBook(false, "7", false));
        numberBooks.add(new NumberBook(false, "8", false));
        numberBooks.add(new NumberBook(true, "9", false));
        numberBooks.add(new NumberBook(false, "10", true));
        numberBooks.add(new NumberBook(true, "11", false));
        numberBooks.add(new NumberBook(false, "12", false));
        numberBooks.add(new NumberBook(false, "13", false));
        numberBooks.add(new NumberBook(false, "14", true));
        numberBooks.add(new NumberBook(false, "15", true));
        initRecycleViews(mRecyclerViewA, numberBooks);
        initRecycleViews(mRecyclerViewB, numberBooks);
        initRecycleViews(mRecyclerViewC, numberBooks);
        initRecycleViews(mRecyclerViewD, numberBooks);
        initRecycleViews(mRecyclerViewE, numberBooks);
        initRecycleViews(mRecyclerViewF, numberBooks);
    }

    private void initRecycleViews(RecyclerView recyclerView, List<NumberBook> numberBooks) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager myLayoutManagerPlayerSelect = new LinearLayoutManager(this);
        myLayoutManagerPlayerSelect.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(myLayoutManagerPlayerSelect);
        recyclerView.setAdapter(new BookTicketAdapter(this, numberBooks));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
