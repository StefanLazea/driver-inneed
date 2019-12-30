package eu.ase.damapp.network;

public class Answer {
    private String firstAnswer;
    private String secondAnswer;
    private String thirdAnswer;
    private String fourthAnswer;
    private String correct;

    public Answer(String firstAnswer, String secondAnswer, String thirdAnswer, String fourthAnswer, String correct) {
        this.firstAnswer = firstAnswer;
        this.secondAnswer = secondAnswer;
        this.thirdAnswer = thirdAnswer;
        this.fourthAnswer = fourthAnswer;
        this.correct = correct;
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(String firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public void setSecondAnswer(String secondAnswer) {
        this.secondAnswer = secondAnswer;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    public void setThirdAnswer(String thirdAnswer) {
        this.thirdAnswer = thirdAnswer;
    }

    public String getFourthAnswer() {
        return fourthAnswer;
    }

    public void setFourthAnswer(String fourthAnswer) {
        this.fourthAnswer = fourthAnswer;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "Answer{" +
                " firstAnswer='" + firstAnswer + '\'' +
                ", secondAnswer='" + secondAnswer + '\'' +
                ", thirdAnswer='" + thirdAnswer + '\'' +
                ", fourthAnswer='" + fourthAnswer + '\'' +
                ", correct='" + correct + '\'' +
                '}';
    }
}
