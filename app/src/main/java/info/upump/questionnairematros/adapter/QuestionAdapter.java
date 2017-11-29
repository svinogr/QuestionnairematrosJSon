package info.upump.questionnairematros.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import java.util.List;
import java.util.concurrent.ExecutionException;

import info.upump.questionnairematros.R;
import info.upump.questionnairematros.entity.Answer;
import info.upump.questionnairematros.entity.Question;
import info.upump.questionnairematros.filter.CategoryFilter;
import info.upump.questionnairematros.model.QuestionViewHolder;
import info.upump.questionnairematros.task.TaskGetAnswer;

/**
 * Created by explo on 23.09.2017.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionViewHolder> {
    private Activity activity;
    protected List<Question> list;
    private CategoryFilter filter;
    protected QuestionViewHolder holder;
    private List<Answer> answers;

    public QuestionAdapter(Activity activity, List<Question> list) {
        this.activity = activity;
        this.list = list;
        this.filter = new CategoryFilter(list, this);
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_card_item, parent, false);
        return new QuestionViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final QuestionViewHolder holder, final int position) {
        this.holder = holder;

        holder.linearLayoutAnswer.removeAllViews();

        holder.number.setText("Вопрос номер: " + String.valueOf(position + 1));

       // holder.questionBody.setText("Вопрос: " + list.get(position).getBody());
        holder.questionBody.setText(list.get(position).getBody());

        setComment(position);

        String s = list.get(position).getImg();
        System.out.println(s);
        if (s != null) {
            holder.img.setImageResource(activity.getResources().getIdentifier("drawable/" + s, null, activity.getApplication().getApplicationContext().getPackageName()));
            holder.img.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
        } else {
            holder.img.setImageDrawable(null);
            holder.img.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        }
        if(list.get(position).getAnswers().size()<1) {

            TaskGetAnswer taskGetAnswer = new TaskGetAnswer(activity, holder, list);
            taskGetAnswer.execute(list.get(position).getId());
            try {
                answers = taskGetAnswer.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }else {
            for (Answer answer :
                    list.get(position).getAnswers()) {
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

    public void setList(List<Question> list) {
        this.list = list;
    }

    public void filter(String text) {
        filter.filter(text);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected void setComment(int position) {
        if (list.get(position).getComment() != null) {
            //holder.comment.setText("Коментарий: " + list.get(position).getComment());
            holder.comment.setText("Коментарий: "+list.get(position).getComment());
        }

    }
}
