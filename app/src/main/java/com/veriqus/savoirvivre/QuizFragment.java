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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
    TextView answer4text;
    TextView answer1text;
    TextView answer2text;
    TextView answer3text;
    TextView questionTextView;
    TextView nextQuestion;
    ViewGroup swipeCard;
    View swipeLay;
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
        nextQuestion = (TextView) rootView.findViewById(R.id.next_question_button);

        //questionTextView.setText(categoryName);
        answer1text = (TextView) rootView.findViewById(R.id.answer1);
        answer2text = (TextView) rootView.findViewById(R.id.answer2);
        answer3text = (TextView) rootView.findViewById(R.id.answer3);
        answer4text = (TextView) rootView.findViewById(R.id.answer4);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar_quiz);
        progressTextView = (TextView) rootView.findViewById(R.id.progress_textView_quiz);


        loadQuizQuestion();
        updateProgressViews();

        return rootView;
    }

    private void loadQuizQuestion() {
        nextQuestion.setVisibility(View.GONE);
        answer1text.setBackgroundResource(R.drawable.sub_learning_bg);
        answer2text.setBackgroundResource(R.drawable.sub_learning_bg);
        answer3text.setBackgroundResource(R.drawable.sub_learning_bg);
        answer4text.setBackgroundResource(R.drawable.sub_learning_bg);


        Random random = new Random();
        question = quotesQuestions.get(position);
        goodAnswer = quotesGoodAnswers.get(position);

        List<String> quizAnswers= new ArrayList<>();

        quizAnswers.add(quotesGoodAnswers.get(position));
        quizAnswers.add(quotesAnswers1.get(position));
        if(!(quotesAnswers2.get(position).isEmpty())) {
            quizAnswers.add(quotesAnswers2.get(position));
        }
        if(!(quotesAnswers3.get(position).isEmpty())) {
            quizAnswers.add(quotesAnswers3.get(position));
        }

        answer1text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goodAnswer.equals(answer1text.getText())){
                    answer1text.setBackgroundResource(R.drawable.sub_learning_bg_good);
                    nextQuestionButton();
                }
                else {
                    answer1text.setBackgroundResource(R.drawable.sub_learning_bg_bad);
                }

            }
        });
        answer2text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goodAnswer.equals(answer2text.getText())){
                    answer2text.setBackgroundResource(R.drawable.sub_learning_bg_good);
                    nextQuestionButton();
                }
                else {
                    answer2text.setBackgroundResource(R.drawable.sub_learning_bg_bad);
                }
            }
        });
        if(quotesAnswers2.get(position).isEmpty()){
            answer3text.setVisibility(View.GONE);
        } else {
            answer3text.setVisibility(View.VISIBLE);
            answer3text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(goodAnswer.equals(answer3text.getText())){
                        answer3text.setBackgroundResource(R.drawable.sub_learning_bg_good);
                        nextQuestionButton();
                    }
                    else {
                        answer3text.setBackgroundResource(R.drawable.sub_learning_bg_bad);
                    }
                }
            });
        }
        if(quotesAnswers3.get(position).isEmpty()){
            answer4text.setVisibility(View.GONE);
        } else {
            answer4text.setVisibility(View.VISIBLE);
            answer4text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(goodAnswer.equals(answer4text.getText())){
                        answer4text.setBackgroundResource(R.drawable.sub_learning_bg_good);
                        nextQuestionButton();
                    }
                    else {
                        answer4text.setBackgroundResource(R.drawable.sub_learning_bg_bad);
                    }
                }
            });
        }

        if (quizAnswers.size() == 2) {
            int rand = random.nextInt(quizAnswers.size());
            answer1text.setText(quizAnswers.get(rand));
            quizAnswers.remove(rand);
            rand = random.nextInt(quizAnswers.size());
            answer2text.setText(quizAnswers.get(0));
            quizAnswers.remove(rand);
        }
        else if (quotesAnswers2.get(position).isEmpty()){
            Log.i("size:", quizAnswers.size() +"");
            int rand = random.nextInt(quizAnswers.size());
            answer1text.setText(quizAnswers.get(rand));
            quizAnswers.remove(rand);
            Log.i("size:", quizAnswers.size() +"");
            rand = random.nextInt(quizAnswers.size());
            answer2text.setText(quizAnswers.get(rand));
            quizAnswers.remove(rand);
            Log.i("size:", quizAnswers.size() +"");
            rand = random.nextInt(quizAnswers.size());
            answer4text.setText(quizAnswers.get(0));
            quizAnswers.remove(rand);
        } else if (quotesAnswers3.get(position).isEmpty()){
            int rand = random.nextInt(quizAnswers.size());
            answer1text.setText(quizAnswers.get(rand));
            quizAnswers.remove(rand);
            rand = random.nextInt(quizAnswers.size());
            answer2text.setText(quizAnswers.get(rand));
            quizAnswers.remove(rand);
            rand = random.nextInt(quizAnswers.size());
            answer3text.setText(quizAnswers.get(0));
            quizAnswers.remove(rand);
        } else {
            int rand = random.nextInt(quizAnswers.size());
            answer1text.setText(quizAnswers.get(rand));
            quizAnswers.remove(rand);
            rand = random.nextInt(quizAnswers.size());
            answer2text.setText(quizAnswers.get(rand));
            quizAnswers.remove(rand);
            rand = random.nextInt(quizAnswers.size());
            answer3text.setText(quizAnswers.get(rand));
            quizAnswers.remove(rand);
            rand = random.nextInt(quizAnswers.size());
            answer4text.setText(quizAnswers.get(0));
            quizAnswers.remove(rand);
        }

        questionTextView.setText(question);


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

    private void nextQuestionButton() {
        if(position<quizListLenght-1) {
            nextQuestion.setVisibility(View.VISIBLE);
            nextQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position++;
                    loadQuizQuestion();
                    updateProgressViews();
                }
            });
        } else {
            nextQuestion.setText("Congrats! Click to learn another category");
            nextQuestion.setVisibility(View.VISIBLE);
            nextQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: GO TO CATEGORY PICKUP
                    //TODO: MARK CATEGORY AS COMPLETED
                }
            });

        }
    }

    private void updateProgressViews() {
        int progressposition = position + 1;
        progressBar.setMax(quizListLenght);
        progressBar.setProgress(progressposition);
        progressTextView.setText(progressposition + " / " + quizListLenght);
        if(progressBar.getProgress() == progressBar.getMax()) {
            //CONGRATS
        }
    }

}
