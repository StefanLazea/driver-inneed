package eu.ase.damapp.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eu.ase.damapp.R;
import eu.ase.damapp.network.Answer;
import eu.ase.damapp.network.HttpManager;
import eu.ase.damapp.network.HttpResponse;
import eu.ase.damapp.network.Item;
import eu.ase.damapp.network.JsonParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionsFragment extends Fragment {
    private static final String URL = "http://api.myjson.com/bins/uqzey";
    private HttpResponse httpResponse;

    private ListView listViewQuestions;
    private List<Item> selectedResponse = new ArrayList<>();

    public QuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_questions, container, false);

//        initComponets(view);

        new HttpManager() {
            @Override
            protected void onPostExecute(String s) {
                httpResponse = JsonParser.parseJson(s);
                if (httpResponse != null) {
                    Toast.makeText(getContext(), httpResponse.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }.execute(URL);

        if (httpResponse != null && httpResponse.getSigns() != null) {
            selectResponse(httpResponse.getSigns());
        }

        listViewQuestions = view.findViewById(R.id.lv_questions);
        ArrayAdapter<Item> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, selectedResponse);

        listViewQuestions.setAdapter(adapter);


        return view;

    }

    private void initComponets(View view) {
        listViewQuestions = view.findViewById(R.id.lv_questions);
        ArrayAdapter<Item> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, selectedResponse);

        listViewQuestions.setAdapter(adapter);

        if (httpResponse != null && httpResponse.getMechanics() != null) {
            selectResponse(httpResponse.getMechanics());
        }

    }

    private void selectResponse(List<Item> list) {
        selectedResponse.clear();

        selectedResponse.addAll(list);
        Toast.makeText(getContext(), list.toString(), Toast.LENGTH_LONG).show();

        ArrayAdapter<Item> adapter = (ArrayAdapter<Item>) listViewQuestions.getAdapter();
        adapter.notifyDataSetChanged();
    }

}
