package com.company;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

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
        FileOutputStream fos = new FileOutputStream("Schedule.html");
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(fos, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StreamResult streamResult = new StreamResult(writer);

        SAXTransformerFactory saxFactory =
                (SAXTransformerFactory) TransformerFactory.newInstance();
        TransformerHandler tHandler = null;
        try {
            tHandler = saxFactory.newTransformerHandler();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        tHandler.setResult(streamResult);

        Transformer transformer = tHandler.getTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "html");
        transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

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
