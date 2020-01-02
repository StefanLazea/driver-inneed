package eu.ase.damapp.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import eu.ase.damapp.R;
import eu.ase.damapp.network.Item;

public class QuestionAdapter extends ArrayAdapter<Item> {
    private Context context;
    private int resource;
    private List<Item> items;
    private LayoutInflater layoutInflater;

    public QuestionAdapter(
            @NonNull Context context,
            int resource,
            List<Item> items,
            LayoutInflater layoutInflater
    ) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent
    ) {
        View view = layoutInflater.inflate(resource, parent, false);
        Item item = items.get(position);
        if (item != null) {
            addQuestion(view, item.getQuestion());
        }
        return view;
    }

    private void addQuestion(View view, String question) {
        TextView textView = view.findViewById(R.id.lv_row_question);
        if (question != null && !question.trim().isEmpty()) {
            textView.setText(question);
        } else {
            textView.setText("Something went wrong");
        }
    }
}

