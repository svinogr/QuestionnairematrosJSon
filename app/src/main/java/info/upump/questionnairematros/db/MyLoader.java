package info.upump.questionnairematros.db;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;


import java.util.ArrayList;
import java.util.List;

import info.upump.questionnairematros.entity.Question;

/**
 * Created by explo on 09.10.2017.
 */

public class MyLoader extends AsyncTaskLoader<List<Question>> {
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;
    private String category;
    private Cursor cursor;

    public MyLoader(Context context) {
        super(context);
        questionDAO = new QuestionDAO(context);
        answerDAO = new AnswerDAO(context);
    }


    public MyLoader(Context context, String category) {
        this(context);
        this.category = category;

    }

    @Override
    public List<Question> loadInBackground() {
        List<Question> list = new ArrayList<>();
        Cursor answerByQuation;


        if (category != null) {
            cursor = questionDAO.getSearchInCategory(category);
        } else cursor = questionDAO.getCursorQuestion();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Question question = new Question();
                    question.setId(cursor.getInt(0));
                    question.setBody(stringToUpperCase(cursor.getString(1)));
                    question.setCategory(stringToUpperCase(cursor.getString(2)));
                    question.setImg(cursor.getString(3));
                    question.setComment(stringToUpperCase(cursor.getString(4)));

                  /*  answerByQuation = answerDAO.getAnswerByQuation(question.getId());
                    if (answerByQuation.moveToFirst()) {
                        do {
                            Answer answer = new Answer();
                            answer.setId(answerByQuation.getInt(0));
                            answer.setBody(stringToUpperCase(answerByQuation.getString(1)));
                            answer.setRight(answerByQuation.getInt(2));
                            answer.setQuestion(question);
                            question.getAnswers().add(answer);

                        } while (answerByQuation.moveToNext());
                    }
                    list.add(question);
                    answerByQuation.close();*/

                    list.add(question);
                }

                while (cursor.moveToNext());


            }
        }
        /*if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Question question = new Question();
                    question.setId(cursor.getInt(0));
                    question.setBody(stringToUpperCase(cursor.getString(1)));
                    question.setCategory(stringToUpperCase(cursor.getString(2)));
                    question.setImg(cursor.getString(3));
                    question.setComment(stringToUpperCase(cursor.getString(4)));
                    list.add(question);

                } while (cursor.moveToNext());

                for (Question question : list) {
                    answerByQuation = answerDAO.getAnswerByQuation(question.getId());
                    if (answerByQuation.moveToFirst()) {
                        do {
                            Answer answer = new Answer();
                            answer.setId(answerByQuation.getInt(0));
                            answer.setBody(stringToUpperCase(answerByQuation.getString(1)));
                            answer.setRight(answerByQuation.getInt(2));
                            answer.setQuestion(question);
                            question.getAnswers().add(answer);

                        } while (answerByQuation.moveToNext());
                    }

                    answerByQuation.close();
                }


            }
        }
*/
        return list;
    }

    private String stringToUpperCase(String s) {
        return s != null && s.length() != 0 ? s.substring(0, 1).toUpperCase() + s.substring(1) : null;
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();

    }

    @Override
    public void deliverResult(List<Question> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }
}
