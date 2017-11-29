package info.upump.questionnairematros.entity;

/**
 * Created by explo on 23.09.2017.
 */

public class Answer {
    private int id;
    private String body;
    private int right;
    private Question question;


    public Answer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (id != answer.id) return false;
        if (right != answer.right) return false;
        return body != null ? body.equals(answer.body) : answer.body == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + right;
        return result;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +"\n"+
                ", body='" + body + '\'' +"\n"+
                ", right=" + right +"\n"+
                '}';
    }
}
