package no.westerdals.student.vegeiv13.innlevering3.utils;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.IntegerObjectType;

import java.time.LocalDateTime;
import java.time.Year;

public class YearTranslator extends IntegerObjectType {

    private static final YearTranslator singleton = new YearTranslator();

    private YearTranslator() {
        super(SqlType.INTEGER, new Class<?>[]{LocalDateTime.class});
    }

    public static YearTranslator getSingleton() {
        return singleton;
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        Year year = (Year) javaObject;
        if (year == null) {
            return null;
        } else {
            return year.getValue();
        }
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return Year.of((int) sqlArg);
    }
}
