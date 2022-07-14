package com.company;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DialogFileReader {

    public final HashMap<LocalDate, List<TableValues>> CSVFileToSortedMap () throws FileNotFoundException {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d.MM.yyyy");

        List<TableValues> values = new ArrayList<>();
        String[] recorder = new String[0];
        HashMap<LocalDate, List<TableValues>> tableMap = new HashMap<>();

        JFileChooser fileChooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("CSV Files","csv");
        fileChooser.setFileFilter(filter);
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileHidingEnabled(false);
        String filePath = null;
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            filePath = file.getPath();
        }

        FileReader filereader = new FileReader(filePath);
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader reader = new CSVReaderBuilder(filereader).withCSVParser(parser).build();

        try {
            reader.readNext();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                if (!((recorder = reader.readNext()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvValidationException e) {
                e.printStackTrace();
            }
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

        return sortedMap;
    }
}

