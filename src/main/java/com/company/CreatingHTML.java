package com.company;

import javax.swing.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class CreatingHTML {

    public final StringBuffer TableCreation(HashMap<LocalDate, List<TableValues>> tableMap) {
        StringBuffer table = new StringBuffer();
        table.append("<table border=\"1\" width=\"600\">" + System.lineSeparator());
        table.append("<tbody>" + System.lineSeparator());
        table.append("<tr>" + System.lineSeparator());
        table.append("<th>Дата</th>" + System.lineSeparator());
        table.append("<th>День недели</th>" + System.lineSeparator());
        table.append("<th>Занятие</th>" + System.lineSeparator());
        table.append("<th>Время</th>" + System.lineSeparator());
        table.append("<th>Руководитель</th>" + System.lineSeparator());
        table.append("<th>Кабинет</th>" + System.lineSeparator());
        table.append("</tr>" + System.lineSeparator());

        tableMap.forEach((k, v) -> {
            table.append("<tr>" + System.lineSeparator());
            table.append("<td rowspan=\"" + v.size() + "\">" + v.get(0).getDate() + "</td>" + System.lineSeparator());
            table.append("<td rowspan=\"" + v.size() + "\">" + v.get(0).getDay() + "</td>" + System.lineSeparator());
            for(int i = 0; i < v.size(); i++) {
                TableValues values = v.get(i);
                if (i != 0) {
                    table.append("<tr>" + System.lineSeparator());
                }
                table.append("<td>" + values.getSubject() + "</td>" + System.lineSeparator());
                table.append("<td>" + values.getTime() + "</td>" + System.lineSeparator());
                table.append("<td>" + values.getTeacher() + "</td>" + System.lineSeparator());
                table.append("<td>" + values.getCabinet() + "</td>" + System.lineSeparator());
                table.append("</tr>" + System.lineSeparator());
            }
        } );
        table.append("</tbody>" + System.lineSeparator());
        table.append("</table>" + System.lineSeparator());
        return table;
    }

    public void HTMLFileCreation (StringBuffer table) throws FileNotFoundException {
        String encoding = "UTF-8";

        JFileChooser fileChoose = new JFileChooser();
        fileChoose.setFileHidingEnabled(false);
        String file_Path = null;
        if (fileChoose.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChoose.getSelectedFile();
            file_Path = file.getPath();
        }

        FileOutputStream fos = new FileOutputStream(file_Path + ".html");
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(fos, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            writer.write("<!DOCTYPE html>" + System.lineSeparator());
            writer.write("<html>" + System.lineSeparator());
            writer.write("<head>" + System.lineSeparator());
            writer.write("<META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" + System.lineSeparator());
            writer.write("</head>" + System.lineSeparator());
            writer.write("<body>" + System.lineSeparator());
            writer.write(String.valueOf(table));
            writer.write("</body>" + System.lineSeparator());
            writer.write("</html>" + System.lineSeparator());
            writer.write("</body>" + System.lineSeparator());
            writer.write("</html>" + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
