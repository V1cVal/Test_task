package com.company;

import java.io.IOException;
import java.time.LocalDate;

import java.util.*;
import java.util.List;


public class Main {

    public static void main(String[] args) throws IOException{

        DialogFileReader dialogFileReader = new DialogFileReader();
        HashMap<LocalDate, List<TableValues>> sortedMap = dialogFileReader.CSVFileToSortedMap();

        CreatingHTML creatingHTML = new CreatingHTML();
        creatingHTML.HTMLFileCreation(creatingHTML.TableCreation(sortedMap));
    }
}