package info.upump.questionnairematros.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.List;

import info.upump.questionnairematros.entity.Question;

/**
 * Created by explo on 26.09.2017.
 */

public class QuestionDAO extends DBDAO {
    private  Cursor cursor;
    private static final String WHERE_ID_EQUALS = DataBaseHelper.TABLE_KEY_ID
            + " =?";

    public QuestionDAO(Context context) {
        super(context);
    }

    public long save(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.TABLE_KEY_BODY, question.getBody().toLowerCase());
        if(question.getImg()!=null){
        cv.put(DataBaseHelper.TABLE_KEY_IMG, question.getImg().toLowerCase());
        }else {
            cv.put(DataBaseHelper.TABLE_KEY_IMG, question.getImg());
        }

        cv.put(DataBaseHelper.TABLE_KEY_CATEGORY, question.getCategory().toLowerCase());
        if(question.getComment()!=null){
            cv.put(DataBaseHelper.TABLE_KEY_COMMENT, question.getComment().toLowerCase());
        }else {
            cv.put(DataBaseHelper.TABLE_KEY_COMMENT, question.getComment());
        }

        return database.insert(DataBaseHelper.TABLE_QUESTION, null, cv);
    }

    public long update(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.TABLE_KEY_BODY, question.getBody().toLowerCase());
        cv.put(DataBaseHelper.TABLE_KEY_IMG, question.getImg().toLowerCase());
        cv.put(DataBaseHelper.TABLE_KEY_CATEGORY, question.getCategory().toLowerCase());
        cv.put(DataBaseHelper.TABLE_KEY_COMMENT, question.getComment().toLowerCase());
        return database.update(DataBaseHelper.TABLE_QUESTION, cv,
                WHERE_ID_EQUALS, new String[]{String.valueOf(question.getId())});

    }

    public int delete(Question question) {
        return database.delete(DataBaseHelper.TABLE_QUESTION, WHERE_ID_EQUALS, new String[]{question.getId() + ""});
    }

    public List<Question> getQuestions() {


        return null;
    }

    public Cursor getCursorQuestion() {
        cursor = database.query(DataBaseHelper.TABLE_QUESTION,
                new String[]{
                        DataBaseHelper.TABLE_KEY_ID,
                        DataBaseHelper.TABLE_KEY_BODY,
                        DataBaseHelper.TABLE_KEY_CATEGORY,
                        DataBaseHelper.TABLE_KEY_IMG,
                        DataBaseHelper.TABLE_KEY_COMMENT},
                null, null, null, null, null, null
        );
        return cursor;
    }
    public Cursor searchByString(String search){
         cursor = database.query(DataBaseHelper.TABLE_QUESTION,
                new String[]{
                        DataBaseHelper.TABLE_KEY_ID,
                        DataBaseHelper.TABLE_KEY_BODY,
                        DataBaseHelper.TABLE_KEY_CATEGORY,
                        DataBaseHelper.TABLE_KEY_IMG,
                        DataBaseHelper.TABLE_KEY_COMMENT},
               DataBaseHelper.TABLE_KEY_BODY + " LIKE ? ", new String[]{String.valueOf("%"+search.toLowerCase()+"%")}, null, null, null, null
        );
        return cursor;
    }


    public Cursor getSearchInCategory(String category) {
        cursor = database.query(DataBaseHelper.TABLE_QUESTION,
                new String[]{
                        DataBaseHelper.TABLE_KEY_ID,
                        DataBaseHelper.TABLE_KEY_BODY,
                        DataBaseHelper.TABLE_KEY_CATEGORY,
                        DataBaseHelper.TABLE_KEY_IMG,
                        DataBaseHelper.TABLE_KEY_COMMENT},
                DataBaseHelper.TABLE_KEY_CATEGORY+ " LIKE ? ", new String[]{String.valueOf("%"+category.toLowerCase()+"%")}, null, null, null, null
        );
        return cursor;

    }
}
