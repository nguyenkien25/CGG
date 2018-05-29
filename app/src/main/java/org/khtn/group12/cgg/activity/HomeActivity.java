package org.khtn.group12.cgg.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.khtn.group12.cgg.adapter.HomeAdapter;
import org.khtn.group12.cgg.R;
import org.khtn.group12.cgg.model.Movie;
import org.khtn.group12.cgg.utils.Constants;
import org.khtn.group12.cgg.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerViewListMovie;
    @BindView(R.id.toolbar)
    Toolbar mToolbarHome;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarHome;
    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    private HomeAdapter mAdapterListMovie;
    private List<Movie> mListMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbarHome);

        initCollapsingToolbar();

        mListMovie = new ArrayList<>();
        mAdapterListMovie = new HomeAdapter(this, mListMovie);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerViewListMovie.setLayoutManager(mLayoutManager);
        mRecyclerViewListMovie.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerViewListMovie.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewListMovie.setAdapter(mAdapterListMovie);

        prepareAlbums();

        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializing collapsing mToolbarHome
     * Will show and hide the mToolbarHome title on scroll
     */
    private void initCollapsingToolbar() {
        mCollapsingToolbarHome.setTitle(" ");
        mAppBarLayout.setExpanded(true);

        // hiding & showing the title when mToolbarHome expanded & collapsed
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mCollapsingToolbarHome.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    mCollapsingToolbarHome.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11};

        Movie a = new Movie("True Romance", 13, covers[0]);
        mListMovie.add(a);

        a = new Movie("Xscpae", 8, covers[1]);
        mListMovie.add(a);

        a = new Movie("Maroon 5", 11, covers[2]);
        mListMovie.add(a);

        a = new Movie("Born to Die", 12, covers[3]);
        mListMovie.add(a);

        a = new Movie("Honeymoon", 14, covers[4]);
        mListMovie.add(a);

        a = new Movie("I Need a Doctor", 1, covers[5]);
        mListMovie.add(a);

        a = new Movie("Loud", 11, covers[6]);
        mListMovie.add(a);

        a = new Movie("Legend", 14, covers[7]);
        mListMovie.add(a);

        a = new Movie("Hello", 11, covers[8]);
        mListMovie.add(a);

        a = new Movie("Greatest Hits", 17, covers[9]);
        mListMovie.add(a);

        mAdapterListMovie.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
//        MenuItem mMenuLogin = menu.findItem(R.id.login_logout);
//        mMenuLogin.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                Intent intent = new Intent(this, LoginRegisterActivity.class);
                intent.putExtra(Constants.FLAG_LOGIN, Constants.LOGIN);
                startActivity(intent);
                break;
            case R.id.logout:
                item.setVisible(false);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
