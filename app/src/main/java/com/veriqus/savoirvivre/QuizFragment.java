package com.veriqus.savoirvivre;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {


    View rootView;
    String question;
    String goodAnswer;
    String answer1;
    String answer2;
    String answer3;
    int position = 0;
    List<String> quotesQuestions;
    List<String> quotesGoodAnswers;
    List<String> quotesAnswers1;
    List<String> quotesAnswers2;
    List<String> quotesAnswers3;
    TextView answerGoodtext;
    TextView answer1text;
    TextView answer2text;
    TextView answer3text;
    TextView questionTextView;
    ViewGroup swipeCard;
    View swipeLay;
    int dip16toPx;
    ProgressBar progressBar;
    TextView progressTextView;
    int quizListLenght;
    SharedPreferences.Editor edit;
    public static String ca1;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_quiz, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        ca1 = "subCat1_1_phone";

        Bundle bundle = getArguments();
        String categoryName = bundle.getString("CATEGORY_ID");

        quotesQuestions = ((MainActivity) getActivity()).getQuizQuotes(ca1, "question");
        quotesGoodAnswers = ((MainActivity) getActivity()).getQuizQuotes(ca1, "answer_good");
        quotesAnswers1 = ((MainActivity) getActivity()).getQuizQuotes(ca1, "answer_1");
        quotesAnswers2 = ((MainActivity) getActivity()).getQuizQuotes(ca1, "answer_2");
        quotesAnswers3 = ((MainActivity) getActivity()).getQuizQuotes(ca1, "answer_3");

        quizListLenght = quotesQuestions.size();


        questionTextView = (TextView) rootView.findViewById(R.id.questionTitle);
        //questionTextView.setText(categoryName);
        answerGoodtext = (TextView) rootView.findViewById(R.id.answer4);
        answer1text = (TextView) rootView.findViewById(R.id.answer1);
        answer2text = (TextView) rootView.findViewById(R.id.answer2);
        answer3text = (TextView) rootView.findViewById(R.id.answer3);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        progressTextView = (TextView) rootView.findViewById(R.id.progress_textView);


        loadArticle();
        updateProgressViews();

        return rootView;
    }

    private void loadArticle() {
        question = quotesQuestions.get(position);
        goodAnswer = quotesGoodAnswers.get(position);
        answer1 = quotesAnswers1.get(position);
        answer2 = quotesAnswers2.get(position);
        answer3 = quotesAnswers3.get(position);





        questionTextView.setText(question);
        //articleContentTextView.setText(articleContent);

        ImageView imgPlace = (ImageView) rootView.findViewById(R.id.imgPlace);
        byte[] imgByteData = ((MainActivity) getActivity()).getQuizImageByte(quotesQuestions.get(position));
        int lenData = 0;
        if (!imgByteData.equals(null)) {
            lenData = imgByteData.length;
        }

        if (!imgByteData.equals(null) && lenData > 1) {
//            imgPlace.setPadding(dip16toPx, dip16toPx, dip16toPx, dip16toPx);
            Log.i("Image:", "shown");
            Glide.with(getActivity())
                    .load(imgByteData).asBitmap()
                    .into(imgPlace);
            imgPlace.setVisibility(View.VISIBLE);
        } else {
            imgPlace.setVisibility(View.GONE);
        }
    }

    private void updateProgressViews() {
        int progressposition = position + 1;
        progressBar.setMax(quizListLenght);
        progressBar.setProgress(progressposition);
        progressTextView.setText(progressposition + " / " + questionTextView);
        if(progressBar.getProgress() == progressBar.getMax()) {
            //CONGRATS
        }
    }

}
