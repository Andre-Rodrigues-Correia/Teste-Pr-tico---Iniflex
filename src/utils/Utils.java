package utils;

import models.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static List<Employee> readEmployeeFile(String fileName) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            return br.lines()
                    .skip(1) // Pula a linha do cabeçalho
                    .map(line -> line.split(","))
                    .map(fields -> new Employee(fields[0], LocalDate.parse(fields[1], formatter), new BigDecimal(fields[2]), fields[3]))
                    .collect(Collectors.toList());
        }
    }

}
