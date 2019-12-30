package eu.ase.damapp.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.NumberPicker;
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
    private static final int MIN_RATING_PICKER = 0;
    private static final int MAX_RATING_PICKER = 10;

    public HomeFragment() {
    }

    public void notifyInternal() {
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

        if (getArguments() != null) {
            categories = getArguments().getParcelableArrayList(CATEGORY_KEY);
        }

        if (getContext() != null) {
            CategoryAdapter adapter = new CategoryAdapter(getContext(),
                    R.layout.lv_row_view,
                    categories,
                    getLayoutInflater());
            lvCategories.setAdapter(adapter);
        }

        lvCategories.setOnItemClickListener(lvCategoriesSelectedItem());
    }

    private AdapterView.OnItemClickListener lvCategoriesSelectedItem() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final NumberPicker picker = new NumberPicker(getActivity());
                picker.setMinValue(MIN_RATING_PICKER);
                picker.setMaxValue(MAX_RATING_PICKER);

                ratingPicker(position, picker);
            }
        };
    }

    private void ratingPicker(final int position, final NumberPicker picker) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle(getString(R.string.home_ratingPicker_title))
                .setMessage(getString(R.string.home_ratingPicker_message))
                .setPositiveButton(getString(R.string.home_button_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateCategory(picker.getValue(), position);
                    }
                })
                .setNegativeButton(getString(R.string.home_cancel_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.setView(picker);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //todo implement the reviews section
    private void updateCategory(int value, int position) {
        if (value == 0) {
            Toast.makeText(getContext(), getString(R.string.home_rating_below), Toast.LENGTH_LONG).show();
            return;
        }

        categories.get(position).setRating(value);
        CategoryAdapter adapter = (CategoryAdapter) lvCategories.getAdapter();
        adapter.notifyDataSetChanged();
    }

}