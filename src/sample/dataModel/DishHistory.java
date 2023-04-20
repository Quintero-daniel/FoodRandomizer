package sample.dataModel;

import javafx.beans.property.SimpleStringProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DishHistory implements Comparable<DishHistory> {
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty date = new SimpleStringProperty("");

    public DishHistory(String name, String date) {
        this.name.set(name);

        try {
            this.date.set(formatter.format(formatter.parse(date)));
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    public DishHistory(String name) {
        final Calendar cal = Calendar.getInstance();
        final Date date = cal.getTime();

        this.name.set(name);
        this.date.set(formatter.format(date));
    }

    public String getName() {
        return name.get();
    }

    public String getDate() {
        return date.get();
    }

    @Override
    public String toString() {
        return this.getName() + " - " + this.getDate();
    }

    @Override
    public int compareTo(DishHistory o) {
        Date date1;
        Date date2;
        try {
            date1 = formatter.parse(this.getDate());
            date2 = formatter.parse(o.getDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return (date1.before(date2)) ? -1 : ((date1.after(date2)) ? 1 : 0);
    }
}
