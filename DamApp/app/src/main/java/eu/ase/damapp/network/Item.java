package eu.ase.damapp.network;

public class Item {
    private String question;
    private String img;
    private Answer answer;

    public Item(String question, String img, Answer answer) {
        this.question = question;
        this.img = img;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Item{" +
                "question='" + question + '\'' +
                ", img='" + img + '\'' +
                ", answer=" + answer +
                '}';
    }
}

