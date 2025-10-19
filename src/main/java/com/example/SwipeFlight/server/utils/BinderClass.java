package com.example.SwipeFlight.server.utils;

import java.beans.PropertyEditorSupport;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class BinderClass {

    @InitBinder
    public void initBinder(WebDataBinder binder) throws IllegalArgumentException {

        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text == null || text.isEmpty()) {
                    setValue(null);
                } else {
                    try {
                        setValue(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(text).getTime()));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException("Invalid date format. Please use dd.MM.yyyy");

                    }
                }
            }
        });

        binder.registerCustomEditor(Time.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text == null || text.isEmpty())
                    setValue(null);
                else {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        sdf.setLenient(false);
                        setValue(new Time(sdf.parse(text).getTime()));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException("Invalid time format. Please use HH:mm:ss");
                    }
                }
            }
        });

        binder.registerCustomEditor(Duration.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text == null || text.isEmpty()) {
                    setValue(null);
                    return;
                } else if (!isValidDurationFormat(text)) {
                    setValue(Duration.ZERO);
                    return;
                }

                String[] parts = text.split(":");
                long hours = Long.parseLong(parts[0]);
                long minutes = Long.parseLong(parts[1]);
                Duration duration = Duration.ofHours(hours).plusMinutes(minutes);
                setValue(duration);
            }

            private boolean isValidDurationFormat(String text) {
                Pattern pattern = Pattern.compile("^([0-9]|[0-9][0-9]):([0-5][0-9])$");
                Matcher matcher = pattern.matcher(text);
                return matcher.matches();
            }

            @Override
            public String getAsText() {
                Duration duration = (Duration) getValue();
                if (duration == null) {
                    return "";
                }
                long hours = duration.toHours();
                long minutes = duration.toMinutes() % 60;

                return String.format("%02d:%02d", hours, minutes);
            }
        });

        binder.registerCustomEditor(double.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null || text.isEmpty()) {
                    setValue(0.0);
                } else {
                    try {
                        setValue(Double.parseDouble(text));
                    } catch (NumberFormatException e) {
                        setValue(0.0);
                    }

                }
            }
        });
    }
}
