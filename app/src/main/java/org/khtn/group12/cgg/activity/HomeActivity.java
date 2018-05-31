package org.khtn.group12.cgg.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rd.PageIndicatorView;

import org.khtn.group12.cgg.adapter.CoverImageAdapter;
import org.khtn.group12.cgg.adapter.HomeAdapter;
import org.khtn.group12.cgg.R;
import org.khtn.group12.cgg.model.Movie;
import org.khtn.group12.cgg.utils.AppController;
import org.khtn.group12.cgg.utils.Constants;
import org.khtn.group12.cgg.utils.GridSpacingItemDecoration;
import org.khtn.group12.cgg.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerViewListMovie;
    @BindView(R.id.toolbar)
    Toolbar mToolbarHome;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarHome;
    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.view_pager_covers)
    ViewPager mViewPagerCovers;
    @BindView(R.id.page_dots_covers)
    PageIndicatorView mPageIndicatorViewCovers;
    private MenuItem mMenuLogin;
    private MenuItem mMenuLogout;

    private HomeAdapter mAdapterListMovie;
    private List<Movie> mListMovie;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbarHome);
        initRecycleView();
        initCollapsingToolbar();
        initFireBase();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mAdapterListMovie.notifyDataSetChanged();
        if (mMenuLogin != null) {
            checkUserLogin();
        }
    }

    private void initViewPager() {
        List<String> listImageCover = new ArrayList<>();
        for (int i = 0; i < Constants.NUMBER_IMAGE_COVER; i++) {
            listImageCover.add(mListMovie.get(i).getImage());
        }
        CoverImageAdapter adapter = new CoverImageAdapter(HomeActivity.this, listImageCover);
        mViewPagerCovers.setAdapter(adapter);
        mPageIndicatorViewCovers.setViewPager(mViewPagerCovers);
    }

    private void initRecycleView() {
        mListMovie = new ArrayList<>();
        mAdapterListMovie = new HomeAdapter(this, mListMovie);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerViewListMovie.setLayoutManager(mLayoutManager);
        mRecyclerViewListMovie.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerViewListMovie.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewListMovie.setAdapter(mAdapterListMovie);
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
    private void initFireBase() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(Constants.FIREBASE_MOVIE);

        // data change listener
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "Count: " + dataSnapshot.getChildrenCount());
                mListMovie.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    Movie movie = postSnapshot.getValue(Movie.class);
                    movie.setId(key);
                    mListMovie.add(movie);
                }
                Collections.reverse(mListMovie);
                initViewPager();
                mAdapterListMovie.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        mMenuLogin = menu.findItem(R.id.login);
        mMenuLogout = menu.findItem(R.id.logout);

        checkUserLogin();
        return true;
    }

    private void checkUserLogin() {
        if (Utils.checkUserLogin()) {
            showMenuLogin(true);
        } else {
            showMenuLogin(false);
        }
    }

    private void showMenuLogin(boolean isLogged) {
        if (isLogged) {
            mMenuLogin.setVisible(false);
            mMenuLogout.setVisible(true);
        } else {
            mMenuLogin.setVisible(true);
            mMenuLogout.setVisible(false);
        }
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
                AppController.getInstance().getPrefManager().clear();
                showMenuLogin(false);
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
