package it.smallcode.smallpets.core.conditions;
/*

Class created by SmallCode
03.08.2021 13:08

*/

import com.google.gson.JsonObject;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateCondition implements Condition{

    public static final String id = "date_condition";

    @Setter
    private Date date;

    @Setter
    private Operation operation;

    @Setter
    private boolean returnValue = true;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isTrue() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTime(date);
        int dateDay = calendar.get(Calendar.DAY_OF_MONTH);
        int dateMonth = calendar.get(Calendar.MONTH);

        Calendar calendarNow = Calendar.getInstance(TimeZone.getDefault());
        calendarNow.setTime(new Date(System.currentTimeMillis()));
        int day = calendarNow.get(Calendar.DAY_OF_MONTH);
        int month = calendarNow.get(Calendar.MONTH);

        if(operation == Operation.EQUALS){
            if(dateDay == day && dateMonth == month){
                return returnValue;
            }
        }else if(operation == Operation.GREATER){
            if(month > dateMonth){
                return returnValue;
            }else if(month == dateMonth && day > dateDay){
                return returnValue;
            }
        }else if(operation == Operation.GREATER_EQUALS){
            if(month > dateMonth){
                return returnValue;
            }else if(month == dateMonth && day >= dateDay){
                return returnValue;
            }
        }else if(operation == Operation.LESSER){
            if(month < dateMonth){
                return returnValue;
            }else if(month == dateMonth && day < dateDay){
                return returnValue;
            }
        }if(operation == Operation.LESSER_EQUALS){
            if(month < dateMonth){
                return returnValue;
            }else if(month == dateMonth && day <= dateDay){
                return returnValue;
            }
        }
        return !returnValue;
    }

    @Override
    public void load(JsonObject object) {
        if(object.has("returnValue")){
            setReturnValue(object.get("returnValue").getAsBoolean());
        }
        if(object.has("date")){
            String dateString = object.get("date").getAsString();
            try {
                Date date = new SimpleDateFormat("dd/MM").parse(dateString);
                setDate(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        if(object.has("operation")){
            String operationString = object.get("operation").getAsString();
            operationString = operationString.toUpperCase(Locale.ROOT);
            Operation operation = Operation.valueOf(operationString);
            setOperation(operation);
        }
    }

}
