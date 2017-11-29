package info.upump.questionnairematros.task;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import info.upump.questionnairematros.db.AnswerDAO;
import info.upump.questionnairematros.entity.Answer;
import info.upump.questionnairematros.entity.Question;
import info.upump.questionnairematros.model.QuestionViewHolder;


public class TaskGetAnswer extends AsyncTask<Integer, Void, List<Answer>> {
    private Activity activity;
    private AnswerDAO answerDAO;
    private QuestionViewHolder holder;
    private List<Question> questionList;

    public TaskGetAnswer(Activity activity, QuestionViewHolder holder, List<Question> questionList) {
        this.activity = activity;
        this.holder = holder;
        this.questionList = questionList;
        answerDAO = new AnswerDAO(activity);

    }

    @Override
    protected List<Answer> doInBackground(Integer... params) {
        List<Answer> list = new ArrayList<>();
        Cursor answerByQuation = answerDAO.getAnswerByQuation(params[0]);
        if (answerByQuation.moveToFirst()) {
            do {
                Answer answer = new Answer();
                answer.setId(answerByQuation.getInt(0));
                answer.setBody((answerByQuation.getString(1)));
                answer.setRight(answerByQuation.getInt(2));
                list.add(answer);

            } while (answerByQuation.moveToNext());
        }
        answerByQuation.close();
        for (Question question : questionList) {
            if(question.getId()==params[0]){
                question.getAnswers().addAll(list);
            }

        }
        return list;
    }


    @Override
    protected void onPostExecute(List<Answer> answers) {
        for (Answer answer :
                answers) {
            CheckedTextView text = new CheckedTextView(activity.getApplicationContext());
            switch (answer.getRight()) {
                case 1:
                    text.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    text.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
                    text.setTypeface(null, Typeface.BOLD_ITALIC);
                    text.setChecked(true);
                    break;
                case 0:
                    text.setTypeface(null, Typeface.ITALIC);
                    break;
                case -1:
                    break;
            }
            text.setTextColor(Color.parseColor("#FF424242"));

            text.setText(" - " + answer.getBody());
            holder.linearLayoutAnswer.addView(text);
        }
    }

}
