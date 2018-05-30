package org.khtn.group12.cgg.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.khtn.group12.cgg.R;
import org.khtn.group12.cgg.adapter.BookTicketAdapter;
import org.khtn.group12.cgg.model.BookTicketSelected;
import org.khtn.group12.cgg.model.NumberBook;
import org.khtn.group12.cgg.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookTicketActivity extends AppCompatActivity {

    private static final String TAG = BookTicketActivity.class.getSimpleName();

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
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String mIdMovie;
    private String[] rows = {"A", "B", "C", "D", "E", "F"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbarBookTicket);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getMovieId();
        initFireBase();
    }

    private void getMovieId() {
        Intent intent = getIntent();
        if (intent != null) {
            mIdMovie = intent.getStringExtra(Constants.INTENT_PUT_EXTRA_MOVIE_ID);
        }
    }

    private void initFireBase() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(Constants.FIREBASE_BOOK_TICKET);
        for (int index = 0; index < rows.length; index++) {
            mFirebaseDatabase.child(mIdMovie).child(rows[index]).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<NumberBook> numberBooks = new ArrayList<>();
                    for (int i = 1; i <= 15; i++) {
                        numberBooks.add(new NumberBook(i + ""));
                    }
                    String value = dataSnapshot.getValue(String.class);
                    Log.d(TAG, "Value is: " + value);
                    if (value != null) {
                        String[] values = value.split(",");
                        if (values[0] != null && !values[0].equals("")) {
                            for (int j = 0; j < values.length; j++) {
                                numberBooks.get(Integer.parseInt(values[j]) - 1).setBook(true);
                            }
                        }
                    }
                    initRecycleViews(getRecyclerViewByIndex(dataSnapshot.getKey().charAt(0)), numberBooks);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.e(TAG, "Failed to read app title value.", error.toException());
                }
            });
        }
    }

    private RecyclerView getRecyclerViewByIndex(char index) {
        RecyclerView recyclerView = null;
        switch (index) {
            case 'A':
                recyclerView = mRecyclerViewA;
                break;
            case 'B':
                recyclerView = mRecyclerViewB;
                break;
            case 'C':
                recyclerView = mRecyclerViewC;
                break;
            case 'D':
                recyclerView = mRecyclerViewD;
                break;
            case 'E':
                recyclerView = mRecyclerViewE;
                break;
            case 'F':
                recyclerView = mRecyclerViewF;
                break;
        }
        return recyclerView;
    }

    private void initRecycleViews(RecyclerView recyclerView, List<NumberBook> numberBooks) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager myLayoutManagerPlayerSelect = new LinearLayoutManager(this);
        myLayoutManagerPlayerSelect.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(myLayoutManagerPlayerSelect);
        recyclerView.setAdapter(new BookTicketAdapter(this, numberBooks));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_book_ticket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.save:
                String title = "";
                List<BookTicketSelected> bookTicketSelectedList = getListBookSelected();
                for (int i = 0; i < bookTicketSelectedList.size(); i++) {
                    title = title + " " + bookTicketSelectedList.get(i).getRow() + "(" + bookTicketSelectedList.get(i).getNumbers() + ")";
                }
                if (title.equals("")) {
                    break;
                }
                new MaterialDialog.Builder(this)
                        .title(getString(R.string.title_book_ticket))
                        .content(getString(R.string.content_book_ticket) + " " + title)
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .onPositive((dialog, which) -> {
                            for (int row = 0; row < rows.length; row++) {
                                BookTicketAdapter adapter = (BookTicketAdapter) getRecyclerViewByIndex(rows[row].charAt(0)).getAdapter();
                                String value = "";
                                for (int index = 0; index < adapter.getItemCount(); index++) {
                                    if (adapter.getmListBookTicket().get(index).isBook() || adapter.getmListBookTicket().get(index).isSelect()) {
                                        value = value + adapter.getmListBookTicket().get(index).getNameNumber() + ",";
                                    }
                                }
                                if (!value.equals("")) {
                                    mFirebaseDatabase.child(mIdMovie).child(rows[row]).setValue(value.substring(0, value.length() - 1));
                                }
                            }
                        })
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<BookTicketSelected> getListBookSelected() {
        List<BookTicketSelected> bookTicketSelecteds = new ArrayList<>();
        for (int row = 0; row < rows.length; row++) {
            BookTicketAdapter adapter = (BookTicketAdapter) getRecyclerViewByIndex(rows[row].charAt(0)).getAdapter();
            String value = "";
            for (int index = 0; index < adapter.getItemCount(); index++) {
                if (adapter.getmListBookTicket().get(index).isSelect()) {
                    value = value + adapter.getmListBookTicket().get(index).getNameNumber() + ",";
                }
            }
            if (!value.equals("")) {
                bookTicketSelecteds.add(new BookTicketSelected(rows[row], value.substring(0, value.length() - 1)));
            }
        }
        return bookTicketSelecteds;
    }
}
