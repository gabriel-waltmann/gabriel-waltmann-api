import java.time.*;

interface IDate {
    LocalDateTime getCurrentDateTime();
    LocalDateTime getDateTime();
    LocalDate getDate();
    LocalTime getTime();
    void setDate(int year, int month, int day);
    void setTime(int hour, int minute, int second);
}

public class Date implements IDate {
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    private int second;

    public void Date(int day, int month, int year, int hour, int minute, int second) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(this.year, this.month, this.day, this.hour, this.minute, this.second);
    }

    public LocalDate getDate() {
        return LocalDate.of(this.year, this.month, this.day);
    }

    public LocalTime getTime() {
        return LocalTime.of(this.hour, this.minute, this.second);
    }

    public void setDate(int year, int month, int day) {
        this.year = year;
        this.day = day;
        this.month = month;
    }

    public void setTime(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public LocalDate getIncrementedDate(int year, int month, int day) {
        LocalDate date = this.getDate();

        return date
                .plusYears(year)
                .plusDays(day)
                .plusMonths(month);
    }

    public ZonedDateTime getBrazilianDate() {
        ZoneId brazilZone = ZoneId.of("America/Sao_Paulo");

        LocalDateTime date = this.getDateTime();

        return ZonedDateTime.of(date, brazilZone);
    }

    public ZonedDateTime getUTCDate() {
        ZoneId utcZone = ZoneId.of("UTC");

        LocalDateTime date = this.getDateTime();

        return ZonedDateTime.of(date, utcZone);
    }
}
