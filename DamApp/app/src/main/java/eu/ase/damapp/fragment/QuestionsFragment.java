package eu.ase.damapp.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import eu.ase.damapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionsFragment extends Fragment {
    private static final String URL = "https://api.myjson.com/bins/uqzey";

    private ListView listViewCategory;

    public QuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toast.makeText(getContext(), "Questions", Toast.LENGTH_LONG).show();
        return inflater.inflate(R.layout.fragment_questions, container, false);
    }

}
