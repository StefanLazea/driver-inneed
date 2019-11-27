package eu.ase.damapp.util;

public class Form {
    private String schoolName;
    private String licenceCategory;
    private String dateTheoreticalExam;
    private String datePracticalExam;
    private String sex;
    private boolean schoolStarted;

    public Form(String schoolName, String licenceCategory, String dateTheoreticalExam, String datePracticalExam, String sex, boolean schoolStarted) {
        this.schoolName = schoolName;
        this.licenceCategory = licenceCategory;
        this.dateTheoreticalExam = dateTheoreticalExam;
        this.datePracticalExam = datePracticalExam;
        this.sex = sex;
        this.schoolStarted = schoolStarted;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getLicenceCategory() {
        return licenceCategory;
    }

    public void setLicenceCategory(String licenceCategory) {
        this.licenceCategory = licenceCategory;
    }

    public String getDateTheoreticalExam() {
        return dateTheoreticalExam;
    }

    public void setDateTheoreticalExam(String dateTheoreticalExam) {
        this.dateTheoreticalExam = dateTheoreticalExam;
    }

    public String getDatePracticalExam() {
        return datePracticalExam;
    }

    public void setDatePracticalExam(String datePracticalExam) {
        this.datePracticalExam = datePracticalExam;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isSchoolStarted() {
        return schoolStarted;
    }

    public void setSchoolStarted(boolean schoolStarted) {
        this.schoolStarted = schoolStarted;
    }

    @Override
    public String toString() {
        return "Form{" +
                "schoolName='" + schoolName + '\'' +
                ", licenceCategory='" + licenceCategory + '\'' +
                ", dateTheoreticalExam='" + dateTheoreticalExam + '\'' +
                ", datePracticalExam='" + datePracticalExam + '\'' +
                ", sex='" + sex + '\'' +
                ", schoolStarted=" + schoolStarted +
                '}';
    }
}
