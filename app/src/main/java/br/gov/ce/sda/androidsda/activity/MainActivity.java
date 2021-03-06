package br.gov.ce.sda.androidsda.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import br.gov.ce.sda.androidsda.R;
import br.gov.ce.sda.androidsda.fragment.FavoritosFragment;
import br.gov.ce.sda.androidsda.fragment.FilmesFragment;
import br.gov.ce.sda.androidsda.fragment.InicioFragment;

public class MainActivity extends AppCompatActivity implements
                NavigationView.OnNavigationItemSelectedListener,
                FilmesFragment.OnFragmentInteractionListener,
                InicioFragment.OnFragmentInteractionListener,
                FavoritosFragment.OnFragmentInteractionListener{

    private static final String TAG = "MainActivity";
    private TextView mTextMessage;
    String title = "";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    title = "Início";
                    switchToInicioFragment();
                    return true;
                case R.id.navigation_dashboard:
                    title = "Filmes";
                    switchToFilmesFragment();
                    return true;
                case R.id.navigation_notifications:
                    title = "Favoritos";
                    switchToFavoritosFragment();
                    return true;
            }

            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchToInicioFragment();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onNavigationItemSelected: " + item);
        return false;
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

    public void switchToFilmesFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, new FilmesFragment()).commit();
        getSupportActionBar().setTitle(title);
    }

    public void switchToInicioFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, new InicioFragment()).commit();
        getSupportActionBar().setTitle(title);
    }

    public void switchToFavoritosFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, new FavoritosFragment()).commit();
        getSupportActionBar().setTitle(title);
    }
}
