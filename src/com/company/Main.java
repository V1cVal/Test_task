package com.company;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException, CsvValidationException {

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d.MM.yyyy");

        List<TableValues> values = new ArrayList<>();
        String[] recorder;
        HashMap<LocalDate, List<TableValues>> tableMap = new HashMap<>();

        FileReader filereader = new FileReader("Schedule.csv");
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader reader = new CSVReaderBuilder(filereader).withCSVParser(parser).build();

        try {
            reader.readNext();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while((recorder = reader.readNext()) != null){
            TableValues tableValues = new TableValues();
            tableValues.setDay(recorder[0]);
            tableValues.setDate(LocalDate.parse(recorder[1], dateFormatter));
            tableValues.setSubject(recorder[2]);
            tableValues.setTime(LocalTime.parse(recorder[3], timeFormatter));
            tableValues.setTeacher(recorder[4]);
            tableValues.setCabinet((recorder[5]));
            values.add(tableValues);

            List<TableValues> recordsList = tableMap.getOrDefault(tableValues.getDate(), new ArrayList<>());
            recordsList.add(tableValues);
            tableMap.put(tableValues.getDate(), recordsList);
        }

        HashMap<LocalDate, List<TableValues>> sortedMap = tableMap.entrySet()
                .stream()
                .sorted(HashMap.Entry.comparingByKey())
                .collect(Collectors
                        .toMap(HashMap.Entry::getKey,
                                HashMap.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new));
        sortedMap.forEach((k, v) -> {
                v.sort(Comparator.comparing(TableValues::getTime));
        });

        CreatingHTML creatingHTML = new CreatingHTML();
        creatingHTML.HTMLFileCreation(creatingHTML.TableCreation(sortedMap));
    }
}