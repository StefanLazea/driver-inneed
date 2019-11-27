package eu.ase.damapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import eu.ase.damapp.fragment.HomeFragment;
import eu.ase.damapp.fragment.QuestionsFragment;
import eu.ase.damapp.util.Category;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    FloatingActionButton fabAskQuestion;
    DrawerLayout drawerLayout;
    Fragment currentFragment;
    ArrayList<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configNavigation();
        initComponents();
        initCategories();
        openDefaultFragment(savedInstanceState);
    }

    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    private void openDefaultFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            currentFragment = createHomeFragment();
            openFragment();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    private void initCategories(){
        categories.add(new Category("Mecanica1", (float)5));
        categories.add(new Category("Mecanica2", (float)3));
        categories.add(new Category("Contraventii", (float)2));

        if(currentFragment instanceof HomeFragment){
            ((HomeFragment)currentFragment).notifyInternal();
        }
    }

    private void initComponents() {
        fabAskQuestion = findViewById(R.id.main_fab_add_question);
        fabAskQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AskActivity.class);
                startActivity(intent);
            }
        });
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener());
    }

    private NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.nav_home) {
                    currentFragment = createHomeFragment();
                    openFragment();

                }
                else if(menuItem.getItemId() == R.id.nav_questions){
                    currentFragment = new QuestionsFragment();
                    openFragment();

                }
                else if(menuItem.getItemId() == R.id.nav_form){
                    Intent intent = new Intent(getApplicationContext(), FormActivity.class);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }

    private void openFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_container, currentFragment)
                .commit();
    }

    private Fragment createHomeFragment() {
        Fragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(HomeFragment.CATEGORY_KEY, categories);
        fragment.setArguments(bundle);
        return fragment;
    }
}