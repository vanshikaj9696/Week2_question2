package assignment2;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.sql.Date;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelReader {
    public static void main(String[] args) {
        String filePath = "excelsheet.xlsx";
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

            List<InterviewClass> scheduleEntries = new ArrayList<>();

           //first row-> header
            Row headerRow = sheet.getRow(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row currentRow = sheet.getRow(i);
                if (currentRow != null) {
                    InterviewClass entry = new InterviewClass();
                    Cell dateCell = currentRow.getCell(0);
                    if (dateCell != null) {
                        switch (dateCell.getCellType()) {
                            case STRING:
                                String dateValue = dateCell.getStringCellValue();
                                Date parsedDate = parseDateToSQLDate(dateValue); // Parse to java.sql.Date
                                entry.setDate(parsedDate);
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(dateCell)) {
                                    Date dateFromNumeric = new Date(dateCell.getDateCellValue().getTime());
                                    entry.setDate(dateFromNumeric);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    Cell monthCell = currentRow.getCell(1);
                    if (monthCell != null && monthCell.getCellType() == CellType.STRING) {
                        String monthValue = monthCell.getStringCellValue();
                        //Month in ("Oct-23")
                        String formattedMonthYear = formatMonth(monthValue);
                        entry.setMonth(formattedMonthYear);
                    }
//
                    Cell timeCell = currentRow.getCell(6);
                    if (timeCell != null && timeCell.getCellType() == CellType.STRING) {
                        String timeValue = timeCell.getStringCellValue();
                        Time parsedTime = parseStringToSQLTime(timeValue);
                        entry.setTime(parsedTime);
                    }
                    entry.setTeam(currentRow.getCell(2).getStringCellValue());
                    entry.setPanelName(currentRow.getCell(3).getStringCellValue());
                    entry.setRound(currentRow.getCell(4).getStringCellValue());
                    entry.setSkill(currentRow.getCell(5).getStringCellValue());

                    scheduleEntries.add(entry);
                }
            }

            for (InterviewClass entry : scheduleEntries) {
                System.out.println(entry); // For demonstration, printing the objects
            }
            DataStore.insertDataIntoDatabase(scheduleEntries);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
            }

private static Date parseDateToSQLDate(String dateStr) {
    try {
        SimpleDateFormat formatter = new SimpleDateFormat("d-MMM-yy", Locale.ENGLISH);
        java.util.Date utilDate = formatter.parse(dateStr);
        return new java.sql.Date(utilDate.getTime());
    } catch (ParseException e) {
        System.err.println("Error parsing date string to java.sql.Date: " + dateStr);
        e.printStackTrace();
        return null;
    }
}
private static Time parseStringToSQLTime(String timeStr) {
    try {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        java.util.Date utilDate = formatter.parse(timeStr);
        return new Time(utilDate.getTime());
    } catch (ParseException e) {
        System.err.println("Error parsing time string to java.sql.Time: " + timeStr);
        e.printStackTrace();
        return null;
    }
}

    private static String formatMonth(String monthValue) {
        String[] parts = monthValue.split("-");
        if (parts.length == 2) {
            String monthAbbreviation = parts[0];
            String year = parts[1].substring(2); // Extract the last two digits of the year
            return monthAbbreviation + "-" + year;
        } else {
            System.err.println("Invalid month format: " + monthValue);
            return "";
        }
    }
}
