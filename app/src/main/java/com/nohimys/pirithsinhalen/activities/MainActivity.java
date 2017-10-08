package com.nohimys.pirithsinhalen.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nohimys.pirithsinhalen.R;
import com.nohimys.pirithsinhalen.fragments.PlayerFragment;
import com.nohimys.pirithsinhalen.fragments.TrackListFragment;
import com.nohimys.pirithsinhalen.models.TrackListItem;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TrackListFragment.OnTrackSelectedListerner {

    private TrackListFragment trackListFragment;
    private PlayerFragment playerFragment;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ButterKnife Registration has to be after the content view is being set
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        //actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_chevron_left_24dp);

        trackListFragment = new TrackListFragment();
        playerFragment = new PlayerFragment();

        //Change to Track List Fragment
        this.changeFragment(trackListFragment);

        actionBar.setDisplayHomeAsUpEnabled(false);
        //actionBar.setHomeButtonEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == android.R.id.home) {
            changeFragment(trackListFragment);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        return super.onOptionsItemSelected(item);
    }

    private void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        if(fragment.getClass().getSimpleName().equals(PlayerFragment.class.getSimpleName())){
            //TODO:
            //Check the reason for the red line by the injellisence and follow the best practice way
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        else if(fragment.getClass().getSimpleName().equals(TrackListFragment.class.getSimpleName())){
            //TODO:
            //Check the reason for the red line by the injellisence and follow the best practice way
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

        fragmentTransaction.replace(R.id.fragment_main_content_area, fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onSelected(TrackListItem trackListItem) {
        //Put arguments
        Bundle bundle = new Bundle();
        bundle.putString("trackName",trackListItem.getTrackName());
        bundle.putString("trackLink", trackListItem.getTrackLink());
        playerFragment.setArguments(bundle);

        this.changeFragment(playerFragment);

        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeButtonEnabled(true);
    }
}
