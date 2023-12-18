package assignment2;
import java.sql.Time;
public class InterviewClass {
    private java.sql.Date date;
    private String month;
    private String team;
    private String panelName;
    private String round;
    private String skill;
    private Time time;
    public java.sql.Date getDate() {
        return date;
    }
    public void setDate(java.sql.Date date) {
        this.date = date;
    }
    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    public String getTeam() {
        return team;
    }
    public void setTeam(String team) {
        this.team = team;
    }
    public String getPanelName() {
        return panelName;
    }
    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }
    public String getRound() {
        return round;
    }
    public void setRound(String round) {
        this.round = round;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Time getTime() {
        return time;
    }
    public void setTime(Time time) {
        this.time = time;
    }

    public InterviewClass() {
    }

    public InterviewClass(java.sql.Date date, String month, String team,
                          String panelName, String round, String skill,
                          Time time) {
        this.date = date;
        this.month = month;
        this.team = team;
        this.panelName = panelName;
        this.round = round;
        this.skill = skill;
        this.time = time;
    }
    public String toString() {
        return "InterviewSchedule{" +
                "date=" + date +
                ", month=" + month +
                ", team='" + team + '\'' +
                ", panelName='" + panelName + '\'' +
                ", round='" + round + '\'' +
                ", skill='" + skill + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

}
