package info.upump.questionnairematros;

import android.app.Activity;
import android.content.ContentValues;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import info.upump.questionnairematros.db.AnswerDAO;
import info.upump.questionnairematros.db.QuestionDAO;
import info.upump.questionnairematros.entity.Answer;
import info.upump.questionnairematros.entity.Question;

/**
 * Created by explo on 23.09.2017.
 */

public class Reader {
    private Activity activity;
    private List<Question> list;
    private static String alphaRu = new String("абвгдеёжзиыйклмнопрстуфхцчшщьэюя");
    private static String[] alphaEn = {"a", "b", "v", "g", "d", "e", "yo", "g", "z", "i", "y", "i",
            "k", "l", "m", "n", "o", "p", "r", "s", "t", "u",
            "f", "h", "tz", "ch", "sh", "sh", "'", "e", "yu", "ya"};

    public Reader(Activity activity) {
        this.activity = activity;
    }

    public void startReader() throws IOException, JSONException {
        this.list = new ArrayList<>();

        // String htmlString = readHtml();
        // String[] arrayQuestions = getArrayQuestions(htmlString);
/*
        for (int i = 0; i < arrayQuestions.length - 1; i++) {
            ContentValues contentValues = new ContentValues();
            Question questionBody = new Question();
            Document parse = Jsoup.parse(arrayQuestions[i], "windows-1251");
            int startQuestion = parse.body().html().indexOf("Вопрос:") + 8;
            int startKategorii = parse.body().html().indexOf("Категории:");
            int startType = parse.body().html().indexOf("Тип вопроса:");
            int startAnswer = parse.body().html().indexOf("Ответы:");
            int startImg = parse.body().html().indexOf("<center>");
            int startKomment = parse.body().html().indexOf("Комментарий:");
            int end = parse.body().html().length();

            String question = Jsoup.parse(parse.body().html().substring(startQuestion, startKategorii)).text();

            questionBody.setBody(question);
            //высчитвываем конец для ответов если рисунка нет

            if (startImg < 0) {
                if (startKomment > 0) {
                    startImg = startKomment - 3;

                } else startImg = end;
            }

            //если рисунок есть

            if (startImg > 0) {
                String img = parse.select("center").html();
                String attrImg = Jsoup.parse(img).select("img").attr("src");
                String s = renameImg(attrImg);
                questionBody.setImg(s);
            }

            //получаем спсиок ответов

            String answer = parse.body().html().substring(startAnswer, startImg);

            String[] brs = answer.split("<br>");
            for (int b = 1; b < brs.length; b++) {// b=1 тк убираем слово ответ

                if (!brs[b].trim().equals("")) {
                    Answer answerBody = new Answer();
                    answerBody.setBody(Jsoup.parse(brs[b]).text());
                    if (brs[b].contains("<i>")) {
                        //           System.out.println(Jsoup.parse(b + " " + brs[b]).text() + "- почти правильный");
                        answerBody.setRight(0);
                    } else if (brs[b].contains("<u>")) {
                        //            System.out.println(Jsoup.parse(b + " " + brs[b]).text() + "- правильный");
                        answerBody.setRight(1);
                    } else {
                        //             System.out.println(b + " " + brs[b]);
                        answerBody.setRight(-1);
                    }
                    questionBody.getAnswers().add(answerBody);
                }

            }

            //коментарий
            if (startKomment > 0) {
                String substring = parse.body().html().substring(startKomment + 12, end);
                String com = Jsoup.parse(substring).text();
                questionBody.setComment(com);
            }

            //категории
            if (startKategorii > 0) {
                String substring;
                if (startType < 0) {
                    substring = parse.body().html().substring(startKategorii, startAnswer);
                } else {
                    substring = parse.body().html().substring(startKategorii, startType);
                }
                questionBody.setCategory(Jsoup.parse(substring).text());
            }
            list.add(questionBody);

        }*/
      /*  AnswerDAO answeDAO = new AnswerDAO(activity.getApplicationContext());
        Cursor answerByQuation = answeDAO.getAnswerByQuation(3901);
       answerByQuation.moveToFirst();
        do {
           System.out.println(answerByQuation.getString(1));
       }
        while (answerByQuation.moveToNext());

*/


        JSONArray jsonArray = new JSONArray(readJsonFile());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            Question question = new Question();
            question.setBody(jsonObject.get("question").toString());
            String img = (String) jsonObject.get("img");
           // if (!img.equals("имя картинки")) question.setImg(jsonObject.get("img").toString());
            question.setImg(jsonObject.get("img").toString());
            question.setCategory("капитан");
            question.setComment(null);

            JSONArray answers = jsonObject.getJSONArray("answers");
            for (int k = 0; k < answers.length(); k++) {
                Answer answer = new Answer();
                JSONObject jsonObjectAnswer = (JSONObject) answers.get(k);
                answer.setBody(jsonObjectAnswer.get("answer").toString());
                if (jsonObjectAnswer.get("good").toString().equals("true")) {
                    answer.setRight(1);
                } else answer.setRight(-1);
                answer.setQuestion(question);
                question.getAnswers().add(answer);
            }
            list.add(question);
        }
        System.out.println("list size: " + list.size());



    }

    public void writeInDb() {
        QuestionDAO questionDAO = new QuestionDAO(activity.getApplicationContext());
        AnswerDAO answeDAO = new AnswerDAO(activity.getApplicationContext());
        for (Question question : list) {
            int id = (int) questionDAO.save(question);
            question.setId(id);
            for (Answer answer : question.getAnswers()) {
                answer.setQuestion(question);
                answeDAO.save(answer);
            }
        }
    }


    /*
    private String readHtml() {
        String s;
        InputStreamReader scanner = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            scanner = new InputStreamReader(am.open("question.html"), "windows-1251");
            bufferedReader = new BufferedReader(scanner);

            while ((s = bufferedReader.readLine()) != null) {
                stringBuilder.append(s);

            }
            scanner.close();
            bufferedReader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();

    }
*/
    private String readJsonFile() {
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(activity.getAssets().open("data.json"), "windows-1251"));
            String line;


            while ((line = reader.readLine()) != null) {
                text.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString();
    }

}
