package info.upump.questionnairematros.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import info.upump.questionnairematros.entity.Answer;

/**
 * Created by explo on 27.09.2017.
 */

public class AnswerDAO extends DBDAO {
    private static final String WHERE_ID_EQUALS = DataBaseHelper.TABLE_KEY_ID
            + " =?";

    public AnswerDAO(Context context) {
        super(context);
    }

    public long save(Answer answer) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.TABLE_KEY_BODY, answer.getBody().toLowerCase());
        cv.put(DataBaseHelper.TABLE_KEY_RIGHT, answer.getRight());
        cv.put(DataBaseHelper.TABLE_KEY_ID_QUESTION, answer.getQuestion().getId());
        return database.insert(DataBaseHelper.TABLE_ANSWER, null, cv);
    }

    public Cursor getAnswerByQuation(int id) {
        Cursor cursor = database.query(DataBaseHelper.TABLE_ANSWER,
                new String[]{
                        DataBaseHelper.TABLE_KEY_ID,
                        DataBaseHelper.TABLE_KEY_BODY,
                        DataBaseHelper.TABLE_KEY_RIGHT},
                        DataBaseHelper.TABLE_KEY_ID_QUESTION + "=? ", new String[]{String.valueOf(id)}, null, null, null, null);

        return cursor;
    }
    public Cursor getCursorAnswer() {
        Cursor cursor = database.query(DataBaseHelper.TABLE_ANSWER,
                new String[]{
                        DataBaseHelper.TABLE_KEY_ID,
                        DataBaseHelper.TABLE_KEY_BODY,
                        DataBaseHelper.TABLE_KEY_RIGHT,
                        DataBaseHelper.TABLE_KEY_ID_QUESTION},
                null, null, null, null, null, null
        );
        return cursor;
    }
}
