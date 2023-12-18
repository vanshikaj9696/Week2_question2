package assignment2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DataStore {
    public static void insertDataIntoDatabase(List<InterviewClass> scheduleEntries) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO interview_entries " +
                    "(`date_col`, `month_col`, `team`, `panel`, `round`, `skill`, `time_col`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);

            scheduleEntries.parallelStream().forEach(entry -> {
                try {
                    java.sql.Date sqlDate = entry.getDate();
                    statement.setDate(1, sqlDate);
                    statement.setString(2, entry.getMonth());
                    statement.setString(3, entry.getTeam());
                    statement.setString(4, entry.getPanelName());
                    statement.setString(5, entry.getRound());
                    statement.setString(6, entry.getSkill());
                    statement.setTime(7, entry.getTime());

                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

