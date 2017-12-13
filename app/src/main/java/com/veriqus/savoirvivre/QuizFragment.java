package com.veriqus.savoirvivre;


import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

public class QuizFragment extends Fragment {

    View rootView;
    String subCategoryName;
    int category;
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
    List<String> quizAnswers;
    TextView answer1text;
    TextView answer2text;
    TextView answer3text;
    TextView answer4text;
    TextView questionTextView;
    TextView nextQuestion;
    ProgressBar progressBar;
    ImageView okIcon1;
    ImageView okIcon2;
    ImageView okIcon3;
    ImageView okIcon4;
    TextView progressTextView;
    Snackbar snackbar;
    int quizListLenght;
    View quizView;
    SharedPreferences.Editor edit;
    Random random = new Random();
    OnQuizPass mCallback;

    public QuizFragment() {}

    public interface OnQuizPass {
        void onQuizPassed(String quizSubCategory, int category);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_quiz, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        Bundle bundle = getArguments();
        category = bundle.getInt("CATEGORY");
        Log.i("category", category+"");
        subCategoryName = bundle.getString("SUBCATEGORY_ID");

        bindViews();
        loadQuizQuestion();
        updateProgressViews();

        return rootView;
    }

    private void bindViews() {

        quotesQuestions = ((MainActivity) getContext()).getQuizQuotes(subCategoryName, "question");
        quotesGoodAnswers = ((MainActivity) getContext()).getQuizQuotes(subCategoryName, "answer_good");
        quotesAnswers1 = ((MainActivity) getContext()).getQuizQuotes(subCategoryName, "answer_1");
        quotesAnswers2 = ((MainActivity) getContext()).getQuizQuotes(subCategoryName, "answer_2");
        quotesAnswers3 = ((MainActivity) getActivity()).getQuizQuotes(subCategoryName, "answer_3");

        quizListLenght = quotesQuestions.size();

        questionTextView = (TextView) rootView.findViewById(R.id.questionTitle);

        answer1text = (TextView) rootView.findViewById(R.id.answer1);
        answer2text = (TextView) rootView.findViewById(R.id.answer2);
        answer3text = (TextView) rootView.findViewById(R.id.answer3);
        answer4text = (TextView) rootView.findViewById(R.id.answer4);

        okIcon4 = (ImageView) rootView.findViewById(R.id.okIcon4);
        okIcon3 = (ImageView) rootView.findViewById(R.id.okIcon3);
        okIcon2 = (ImageView) rootView.findViewById(R.id.okIcon2);
        okIcon1 = (ImageView) rootView.findViewById(R.id.okIcon1);

        quizView = (View) rootView.findViewById(R.id.quizLay);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar_quiz);
        progressTextView = (TextView) rootView.findViewById(R.id.progress_textView_quiz);

        nextQuestion = (TextView) rootView.findViewById(R.id.next_question_button);
    }

    private void loadQuizQuestion() {
        clearQuizView();

        question = quotesQuestions.get(position);
        questionTextView.setText(question);
        goodAnswer = quotesGoodAnswers.get(position);

        prepareSingleQuizAnswers();
        setImageData();
        setAnswerTextViewsQuantity();
        setAnswersToVisibleTextViews();
    }

    private void setAnswersToVisibleTextViews() {

        if (answer2.isEmpty() && answer3.isEmpty()) {
            Log.i("SET:", "2");

            int rand = random.nextInt(quizAnswers.size());
            answer1text.setText(quizAnswers.get(rand));
            quizAnswers.remove(rand);
            rand = random.nextInt(quizAnswers.size());
            answer2text.setText(quizAnswers.get(0));
            quizAnswers.remove(0);
        }

        else if (answer2.isEmpty()){
            Log.i("SET:", "3 - no 3");
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
            answer4text.setText(quizAnswers.get(rand));
            quizAnswers.remove(0);
            Log.i("size:", quizAnswers.size() +"");


        } else if (answer3.isEmpty()){
            Log.i("SET:", "3 - no 4");
            int rand = random.nextInt(quizAnswers.size());
            answer1text.setText(quizAnswers.get(rand));
            quizAnswers.remove(rand);
            rand = random.nextInt(quizAnswers.size());
            answer2text.setText(quizAnswers.get(rand));
            quizAnswers.remove(rand);
            rand = random.nextInt(quizAnswers.size());
            answer3text.setText(quizAnswers.get(0));
            quizAnswers.remove(0);
        } else {
            Log.i("SET:", "ALL");
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
            quizAnswers.remove(0);
        }
    }

    private void setAnswerTextViewsQuantity() {
        answer1text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goodAnswer.equals(answer1text.getText())) {
                    answer1text.setBackgroundResource(R.drawable.sub_learning_bg_good_outline);
                    answer1text.setTextColor(getResources().getColor(R.color.colorGood));
                    okIcon1.setImageResource(R.drawable.success_green);
                    nextQuestionButton();
                    disableAnswerButtons();
                    showSnackbar("GOOD!",true);

                } else {
                    answer1text.setBackgroundResource(R.drawable.sub_learning_bg_bad);
                    answer1text.setTextColor(getResources().getColor(R.color.colorBad));
                    okIcon1.setImageResource(R.drawable.bad_answer_icon);
                    showSnackbar("Try again", false);
                }
                okIcon1.setVisibility(View.VISIBLE);
            }
        });
        answer2text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goodAnswer.equals(answer2text.getText())){
                    answer2text.setBackgroundResource(R.drawable.sub_learning_bg_good_outline);
                    answer2text.setTextColor(getResources().getColor(R.color.colorGood));
                    okIcon2.setImageResource(R.drawable.success_green);
                    nextQuestionButton();
                    disableAnswerButtons();
                    showSnackbar("GOOD!",true);
                }
                else {
                    answer2text.setBackgroundResource(R.drawable.sub_learning_bg_bad);
                    answer2text.setTextColor(getResources().getColor(R.color.colorBad));
                    okIcon2.setImageResource(R.drawable.bad_answer_icon);
                    showSnackbar("Try again", false);
                }
                okIcon2.setVisibility(View.VISIBLE);
            }
        });
        if(answer2.isEmpty()){
            Log.i("Check:", quotesAnswers2.get(position)+ "HERE");
            answer3text.setVisibility(View.GONE);
        } else {
            answer3text.setVisibility(View.VISIBLE);
            answer3text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(goodAnswer.equals(answer3text.getText())){
                        answer3text.setBackgroundResource(R.drawable.sub_learning_bg_good_outline);
                        answer3text.setTextColor(getResources().getColor(R.color.colorGood));
                        okIcon3.setImageResource(R.drawable.success_green);
                        nextQuestionButton();
                        disableAnswerButtons();
                        showSnackbar("GOOD!",true);
                    }
                    else {
                        answer3text.setBackgroundResource(R.drawable.sub_learning_bg_bad);
                        answer3text.setTextColor(getResources().getColor(R.color.colorBad));
                        okIcon3.setImageResource(R.drawable.bad_answer_icon);
                        showSnackbar("Try again", false);
                    }
                    okIcon3.setVisibility(View.VISIBLE);

                }
            });
        }
        if(answer3.isEmpty()){
            Log.i("Check:", quotesAnswers3.get(position) + "HERE");
            answer4text.setVisibility(View.GONE);
        } else {
            answer4text.setVisibility(View.VISIBLE);
            answer4text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(goodAnswer.equals(answer4text.getText())){
                        answer4text.setBackgroundResource(R.drawable.sub_learning_bg_good_outline);
                        answer4text.setTextColor(getResources().getColor(R.color.colorGood));
                        okIcon4.setImageResource(R.drawable.success_green);
                        nextQuestionButton();
                        disableAnswerButtons();
                        showSnackbar("GOOD!",true);
                    }
                    else {
                        answer4text.setBackgroundResource(R.drawable.sub_learning_bg_bad);
                        answer4text.setTextColor(getResources().getColor(R.color.colorBad));
                        okIcon4.setImageResource(R.drawable.bad_answer_icon);
                        showSnackbar("Try again", false);
                    }
                    okIcon4.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void showSnackbar(String msg, boolean good_or_bad) {
        snackbar = Snackbar.make(quizView, msg, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        if(good_or_bad) {
            sbView.setBackgroundColor(getResources().getColor(R.color.colorGood));
        } else {
            sbView.setBackgroundColor(getResources().getColor(R.color.colorBad));
        }
        snackbar.show();
    }

    private void setImageData() {
        ImageView imgPlace = (ImageView) rootView.findViewById(R.id.imgPlace);
        byte[] imgByteData = ((MainActivity) getActivity()).getQuizImageByte(quotesQuestions.get(position));
        int lenData = 0;
        if (imgByteData != null) {
            lenData = imgByteData.length;
        }

        if ((imgByteData != null) && (lenData > 1)) {
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

    private void prepareSingleQuizAnswers() {
        quizAnswers = new ArrayList<>();
        goodAnswer = quotesGoodAnswers.get(position);
        answer1 = quotesAnswers1.get(position);
        answer2 = quotesAnswers2.get(position);
        answer3 = quotesAnswers3.get(position);

        quizAnswers.add(goodAnswer);
        quizAnswers.add(answer1);
        if (!answer2.isEmpty()) {
            quizAnswers.add(answer2);
        }
        if (!answer3.isEmpty()) {
            quizAnswers.add(answer3);
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
                    snackbar.dismiss();
                }
            });
        } else {
            nextQuestion.setText("Congrats! Click to learn another category");
            nextQuestion.setVisibility(View.VISIBLE);
            nextQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                    mCallback.onQuizPassed(subCategoryName, category);

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

    private void disableAnswerButtons(){
        answer1text.setEnabled(false);
        answer2text.setEnabled(false);
        answer3text.setEnabled(false);
        answer4text.setEnabled(false);
    }

    private void clearQuizView() {
        nextQuestion.setVisibility(View.GONE);
        answer1text.setBackgroundResource(R.drawable.sub_learning_bg);
        answer2text.setBackgroundResource(R.drawable.sub_learning_bg);
        answer3text.setBackgroundResource(R.drawable.sub_learning_bg);
        answer4text.setBackgroundResource(R.drawable.sub_learning_bg);

        okIcon1.setVisibility(View.GONE);
        okIcon2.setVisibility(View.GONE);
        okIcon3.setVisibility(View.GONE);
        okIcon4.setVisibility(View.GONE);

        answer1text.setTextColor(Color.parseColor("#AAAAAA"));
        answer2text.setTextColor(Color.parseColor("#AAAAAA"));
        answer3text.setTextColor(Color.parseColor("#AAAAAA"));
        answer4text.setTextColor(Color.parseColor("#AAAAAA"));

        answer1text.setEnabled(true);
        answer2text.setEnabled(true);
        answer3text.setEnabled(true);
        answer4text.setEnabled(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (QuizFragment.OnQuizPass) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLearningSubSelected");
        }
    }

}
