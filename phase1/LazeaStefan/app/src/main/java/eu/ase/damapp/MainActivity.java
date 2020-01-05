package eu.ase.damapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import eu.ase.damapp.util.User;

public class MainActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private FloatingActionButton fabAskQuestion;
    private DrawerLayout drawerLayout;
    private Fragment currentFragment;
    private final ArrayList<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configNavigation();
        initComponents();
        initCategories();
        openDefaultFragment(savedInstanceState);
        getCurrentUser();
    }

    private void getCurrentUser() {
        if (getIntent().getExtras() != null) {
            User currentUser = getIntent().getExtras().getParcelable(LoginActivity.CURRENT_USER);
            Toast.makeText(getApplicationContext(),
                    getString(R.string.main_welcome_user).concat(currentUser.getUsername()),
                    Toast.LENGTH_LONG).show();
            updateMenuDetails(currentUser);
        }
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

    private void initCategories() {
        categories.add(new Category(R.drawable.ic_home_black_24dp, "Mecanica", (float) 5));
        categories.add(new Category(R.drawable.ic_person_black_24dp, "Semne de circulatie", (float) 3));
        categories.add(new Category(R.drawable.ic_help_outline_black_24dp, "Contraventii", (float) 2));

        if (currentFragment instanceof HomeFragment) {
            ((HomeFragment) currentFragment).notifyInternal();
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

                } else if (menuItem.getItemId() == R.id.nav_questions) {
                    currentFragment = new QuestionsFragment();
                    openFragment();

                } else if (menuItem.getItemId() == R.id.nav_form) {
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


    private void updateMenuDetails(User user) {
        if (user != null) {
            View headerView = navigationView.getHeaderView(0);
            TextView navName = headerView.findViewById(R.id.nav_name);
            navName.setText(user.getUsername());
        }
    }
}