package eu.ase.damapp.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import eu.ase.damapp.R;
import eu.ase.damapp.database.model.Category;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private final Context context;
    private final int resource;
    private final List<Category> categories;
    private final LayoutInflater layoutInflater;

    public CategoryAdapter(@NonNull Context context,
                           int resource,
                           List<Category> categories,
                           LayoutInflater layoutInflater) {
        super(context, resource, categories);
        this.context = context;
        this.resource = resource;
        this.categories = categories;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent
    ) {
        View view = layoutInflater.inflate(resource, parent, false);
        ImageView img = view.findViewById(R.id.lv_row_img);
        Category category = categories.get(position);
        if (category != null) {
            addName(view, category.getName());
            addRating(view, category.getRating());
        }

        img.setImageResource(category.getImgDrawable());
        return view;
    }

    private void addRating(View view, float rating) {
        TextView textView = view.findViewById(R.id.lv_row_rating);
        if (rating > 0) {
            textView.setText(String.valueOf(rating));
        } else {
            textView.setText(R.string.lv_empty_resource);
        }
    }

    private void addName(View view, String name) {
        TextView textView = view.findViewById(R.id.lv_row_name);
        if (name != null && !name.trim().isEmpty()) {
            textView.setText(name);
        } else {
            textView.setText(R.string.lv_empty_resource);
        }
    }
}
