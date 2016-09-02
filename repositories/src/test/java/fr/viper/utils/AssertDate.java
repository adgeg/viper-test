package fr.viper.utils;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertDate {
    private AssertDate() {

    }

    public static void assertSameHour(Date date, int year, int month, int dayOfMonth, int hourOfDay) {
        final GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, 0);
        assertThat(date).isInSameHourAs(calendar.getTime());
    }
}
