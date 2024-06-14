import static org.junit.jupiter.api.Assertions.*;
import static utils.Utils.readEmployeeFile;


import models.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class MainTest {
    private List<Employee> employees;

    @BeforeEach
    public void setUp() {
        employees = new ArrayList<>();
        try {
            employees = readEmployeeFile("src/data/funcionários.csv");
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    @Test
    public void testRemoverFuncionarioExistente() {
        Main.removeEmployeeByName(employees, "João");
        assertFalse(employees.stream().anyMatch(f -> f.getName().equalsIgnoreCase("João")));
    }

    @Test
    public void testRemoverFuncionarioInexistente() {
        Main.removeEmployeeByName(employees, "Pedro");

        assertEquals(10, employees.size());
    }

    @Test
    public void testRemoverFuncionarioLower() {
        Main.removeEmployeeByName(employees, "João");
        assertFalse(employees.stream().anyMatch(f -> f.getName().equalsIgnoreCase("João")));
    }


    @Test
    public void testAumentarSalario() {
        BigDecimal percentualAumento = new BigDecimal("0.10");
        Main.increaseEmployeeSalary(employees, percentualAumento);
        assertEquals(new BigDecimal("2210.3840"), employees.get(0).getSalary()); // Maria,
    }
}