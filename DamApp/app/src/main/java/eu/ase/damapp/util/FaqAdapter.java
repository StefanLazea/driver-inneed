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
import eu.ase.damapp.database.model.Faq;

public class FaqAdapter extends ArrayAdapter<Faq> {
    private Context context;
    private int resource;
    private List<Faq> faqs;
    private LayoutInflater layoutInflater;

    public FaqAdapter(@NonNull Context context,
                      int resource,
                      List<Faq> objects,
                      LayoutInflater layoutInflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.faqs = objects;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        Faq faq = faqs.get(position);
        if (faq != null) {
            addName(view, faq.getQuestion());
            addRating(view, faq.getApplicationRating());
        }
        return view;
    }

    private void addName(View view, String question) {
        TextView textView = view.findViewById(R.id.lv_row_ask_question);
        if (question != null && !question.trim().isEmpty()) {
            textView.setText(question);
        } else {
            textView.setText("-");
        }
    }

    private void addRating(View view, float rating){
        TextView textView = view.findViewById(R.id.lv_row_ask_rating);
        if(rating > 0){
            textView.setText(String.valueOf(rating));
        }else{
            textView.setText("-");
        }
    }
}
