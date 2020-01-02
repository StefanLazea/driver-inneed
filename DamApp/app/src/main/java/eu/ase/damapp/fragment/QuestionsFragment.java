package eu.ase.damapp.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import eu.ase.damapp.QuizActivity;
import eu.ase.damapp.R;
import eu.ase.damapp.network.HttpManager;
import eu.ase.damapp.network.HttpResponse;
import eu.ase.damapp.network.Item;
import eu.ase.damapp.network.JsonParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionsFragment extends Fragment {
    private static final String URL = "https://api.myjson.com/bins/uqzey";
    public static final String CURRENT_QUESTION = "currentQuestion";
    public static final String CURRENT_CATEGORY = "currentCategory";
    private HttpResponse httpResponse;

    private Button btnMechanics;
    private Button btnSigns;
    private Button btnTickets;
    private ListView listViewQuestions;
    private List<Item> selectedResponse = new ArrayList<>();
    private String categoryName;

    public QuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_questions, container, false);

        initComponents(view);

        new HttpManager() {
            @Override
            protected void onPostExecute(String s) {
                httpResponse = JsonParser.parseJson(s);
                if (httpResponse != null) {
                    selectedResponse.addAll(httpResponse.getMechanics());
                }
            }
        }.execute(URL);


        return view;

    }

    private void initComponents(View view) {
        btnMechanics = view.findViewById(R.id.questions_btn_mechanics);
        btnSigns = view.findViewById(R.id.questions_btn_signs);
        btnTickets = view.findViewById(R.id.questions_btn_tickets);

        listViewQuestions = view.findViewById(R.id.lv_questions);
        ArrayAdapter<Item> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                selectedResponse);
        listViewQuestions.setAdapter(adapter);

        unSelectedButtons();

        btnMechanics.setOnClickListener(new View
                .OnClickListener() {
            @Override
            public void onClick(View view) {
                if (httpResponse != null && httpResponse
                        .getMechanics() != null) {
                    unSelectedButtons();
                    selectButtonColor(btnMechanics);
                    selectResponse(httpResponse.getMechanics());
                    categoryName = "Mecanica";
                }
            }
        });

        //btn center
        btnSigns.setOnClickListener(new View
                .OnClickListener() {
            @Override
            public void onClick(View view) {
                if (httpResponse != null &&
                        httpResponse.getSigns() != null) {
                    unSelectedButtons();
                    selectButtonColor(btnSigns);
                    selectResponse(httpResponse.getSigns());
                    categoryName = "Semne de circulatie";
                }
            }
        });

        //btn inter
        btnTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (httpResponse != null && httpResponse
                        .getTickets() != null) {
                    unSelectedButtons();
                    selectButtonColor(btnTickets);
                    selectResponse(httpResponse.getTickets());
                    categoryName = "Contraventii";
                }
            }
        });

        listViewQuestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putExtra(CURRENT_QUESTION, selectedResponse.get(position));
                intent.putExtra(CURRENT_CATEGORY, categoryName);
                Toast.makeText(getContext(), "Intrebarea " + selectedResponse.get(position).getQuestion()
                        + "categoria " + categoryName
                        , Toast.LENGTH_LONG).show();

                startActivity(intent);
            }
        });
    }

    private void selectResponse(List<Item> list) {
        selectedResponse.clear();
        selectedResponse.addAll(list);
        ArrayAdapter<Item> adapter = (ArrayAdapter<Item>)
                listViewQuestions.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void unSelectedButtons() {
        btnMechanics.setBackgroundColor(Color.GRAY);
        btnTickets.setBackgroundColor(Color.GRAY);
        btnSigns.setBackgroundColor(Color.GRAY);
    }

    private void selectButtonColor(Button button) {
        button.setBackgroundColor(Color.BLUE);
    }


}
