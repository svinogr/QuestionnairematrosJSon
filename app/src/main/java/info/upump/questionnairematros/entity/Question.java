package info.upump.questionnairematros.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by explo on 23.09.2017.
 */

public class Question {
    int id;
    int nunber;
    String body;
    String img;
    List<Answer> answers = new ArrayList();
    String comment;
    String category;

    public Question() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Answer> getAnswers() {
        return this.answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getNunber() {
        return this.nunber;
    }

    public void setNunber(int nunber) {
        this.nunber = nunber;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (id != question.id) return false;
        if (nunber != question.nunber) return false;
        if (body != null ? !body.equals(question.body) : question.body != null) return false;
        if (img != null ? !img.equals(question.img) : question.img != null) return false;
        if (answers != null ? !answers.equals(question.answers) : question.answers != null)
            return false;
        if (comment != null ? !comment.equals(question.comment) : question.comment != null)
            return false;
        return category != null ? category.equals(question.category) : question.category == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + nunber;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (img != null ? img.hashCode() : 0);
        result = 31 * result + (answers != null ? answers.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "Question{\nid=" + this.id + "\n, nunber=" + this.nunber + "\n, body='" + this.body + '\'' + "\n, img='" + this.img + '\'' + "\n, answers=" + this.answers + "\n, comment='" + this.comment + '\'' + ", category='" + this.category + '\'' + '}';
    }
}
