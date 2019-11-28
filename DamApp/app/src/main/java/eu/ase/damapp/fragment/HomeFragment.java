package eu.ase.damapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import eu.ase.damapp.R;
import eu.ase.damapp.util.Category;
import eu.ase.damapp.util.CategoryAdapter;


public class HomeFragment extends Fragment {
    private ListView lvCategories;
    private List<Category> categories = new ArrayList<>();
    public static final String CATEGORY_KEY = "categoriesKey";


    public HomeFragment() {
    }

    public void notifyInternal(){
        CategoryAdapter categoryAdapter = (CategoryAdapter) lvCategories.getAdapter();
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        lvCategories = view.findViewById(R.id.home_lv_categories);

        if(getArguments()!=null){
            categories = getArguments().getParcelableArrayList(CATEGORY_KEY);
            Toast.makeText(getContext(), categories.toString(), Toast.LENGTH_LONG).show();
        }

        if(getContext() != null){
            CategoryAdapter adapter = new CategoryAdapter(getContext(),
                    R.layout.lv_row_view,
                    categories,
                    getLayoutInflater());
            lvCategories.setAdapter(adapter);
        }
    }
}