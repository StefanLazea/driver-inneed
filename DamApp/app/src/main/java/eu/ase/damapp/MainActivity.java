package eu.ase.damapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import eu.ase.damapp.database.model.Category;
import eu.ase.damapp.database.model.User;
import eu.ase.damapp.database.service.CategoryService;
import eu.ase.damapp.database.service.UserService;
import eu.ase.damapp.fragment.HomeFragment;
import eu.ase.damapp.fragment.QuestionsFragment;
import eu.ase.damapp.util.CustomSharedPreferences;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private NavigationView navigationView;
    private FloatingActionButton fabAskQuestion;
    private DrawerLayout drawerLayout;
    private Fragment currentFragment;
    private User currentUser;
    private ImageView image_view;
    private final ArrayList<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configNavigation();
        initComponents();
        getAllCategoriesFromDb();
        getTotalEntriesFromDb();
        openDefaultFragment(savedInstanceState);
        getCurrentUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                if (data != null) {
                    Uri imageUri = data.getData();
                    InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    image_view.setImageBitmap(selectedImage.createScaledBitmap
                            (selectedImage, 120, 120, false));
                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.main_img_somethin_went_wrong,
                            Toast.LENGTH_LONG).show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getApplicationContext(),
                    R.string.main_img_not_selected,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void getCurrentUser() {
        if (getIntent().getExtras() != null) {
            currentUser = getIntent().getExtras().getParcelable(LoginActivity.CURRENT_USER);
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
        insertCategoryIntoDB(new Category(R.drawable.ic_home_black_24dp,
                "Mecanica", (float) 5));
        insertCategoryIntoDB(new Category(R.drawable.ic_person_black_24dp,
                "Semne de circulatie", (float) 3));
        insertCategoryIntoDB(new Category(R.drawable.ic_help_outline_black_24dp,
                "Contraventii", (float) 2));
    }

    private void notifyCustomAdapter() {
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
                } else if (menuItem.getItemId() == R.id.nav_faq) {
                    Intent intent = new Intent(getApplicationContext(), FaqActivity.class);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.nav_delete_account) {
                    CustomSharedPreferences.setIdToPreferences(getApplicationContext(),
                            RegisterActivity.SHARED_PREF_NAME, -1);
                    deleteUser();
                } else if (menuItem.getItemId() == R.id.nav_maps) {
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
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
            image_view = headerView.findViewById(R.id.imageView);
            image_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pickerIntent = new Intent();
                    pickerIntent.setType("image/*");
                    pickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(pickerIntent,
                            getString(R.string.main_select_img_message)), PICK_IMAGE);
                }
            });
            navName.setText(user.getUsername());
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void insertCategoryIntoDB(Category category) {
        new CategoryService.Insert(getApplicationContext()) {
            @Override
            protected void onPostExecute(Category result) {
                if (result != null) {
                    categories.add(result);
                    notifyCustomAdapter();
                }
            }
        }.execute(category);
    }

    @SuppressLint("StaticFieldLeak")
    private void getAllCategoriesFromDb() {
        new CategoryService.GetAll(getApplicationContext()) {
            @Override
            protected void onPostExecute(List<Category> results) {
                if (results != null) {
                    categories.clear();
                    categories.addAll(results);
                    notifyCustomAdapter();
                }
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void deleteUser() {
        new UserService.Delete(getApplicationContext()) {
            @Override
            protected void onPostExecute(Integer result) {
                if (result == 0) {
                    CustomSharedPreferences.setIdToPreferences(getApplicationContext(), RegisterActivity.SHARED_PREF_NAME, -1);
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(intent);
                }
            }
        }.execute(currentUser);
    }

    @SuppressLint("StaticFieldLeak")
    private void getTotalEntriesFromDb() {
        new CategoryService.GetEntries(getApplicationContext()) {
            @Override
            protected void onPostExecute(Integer integer) {
                if (integer == 0) {
                    initCategories();
                }
            }
        }.execute();
    }


}