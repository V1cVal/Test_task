package com.company;

import java.time.LocalDate;
import java.time.LocalTime;

public final class TableValues {
    private String day;
    private LocalDate date;
    private String subject;
    private LocalTime time;
    private String teacher;
    private String cabinet;

    public String getDay(){
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCabinet() {
        return cabinet;
    }
    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }
}

