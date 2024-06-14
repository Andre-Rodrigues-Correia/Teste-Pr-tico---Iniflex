import models.Employee;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static utils.Utils.readEmployeeFile;

public class Main {

    private static final BigDecimal MINIMUM_SALARY = new BigDecimal("1212.00");

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();

        try {
            employees = readEmployeeFile("src/data/funcionários.csv");
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }

        removeEmployeeByName(employees, "João");

        getFormattedEmployees(employees);

        printLine();

        increaseEmployeeSalary(employees, BigDecimal.valueOf(0.10));

        printLine();

        Map<String, List<Employee>> employeesByFunction = groupEmployeesByFunction(employees);

        printLine();

        getEmployeesGroupedByFunction(employeesByFunction);

        printLine();

        getBirthdays(employees, 10, 12);

        printLine();

        getOlderEmployees(employees);

        printLine();

        getEmployeesByAlphabeticalOrder(employees);

        printLine();

        getTotalSalaries(employees);

        printLine();

        getMinimunSalaries(employees);
    }

    public static void printLine() {
        System.out.println("--------------------");
    }

    public static void removeEmployeeByName(List<Employee> employees, String name) {
        employees.removeIf(e -> e.getName().equalsIgnoreCase(name));
    }

    public static void increaseEmployeeSalary(List<Employee> employees, BigDecimal percentage) {
        employees.forEach(f -> f.setSalary(f.getSalary().multiply(percentage.add(BigDecimal.ONE))));
    }

    public static Map<String, List<Employee>> groupEmployeesByFunction(List<Employee> funcionarios) {
        return funcionarios.stream().collect(Collectors.groupingBy(Employee::getFunction));
    }


    private static void getFormattedEmployees(List<Employee> employees) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat numberFormatter = new DecimalFormat("#,##0.00");

        employees.stream().forEach(e -> System.out.println(
                "Nome: " + e.getName() + "\n" +
                        "Data de Nascimento: " + e.getBirthday().format(dateFormatter) + "\n" +
                        "Salário: R$ " + numberFormatter.format(e.getSalary()) + "\n" +
                        "Função: " + e.getFunction() + "\n"
        ));
    }

    private static void getEmployeesGroupedByFunction(Map<String, List<Employee>> map) {
        map.forEach((key, value) -> {
            System.out.println("Função: " + key);
            value.forEach(f -> System.out.println(f.getName()));
            System.out.println();
        });
    }

    private static void getBirthdays(List<Employee> employees, int... months) {
        System.out.println("Funcionários agrupados por mês " + Arrays.toString(months) + ":");
        employees.stream()
                .filter(e -> Arrays.stream(months).anyMatch(m -> m == e.getBirthday().getMonthValue()))
                .forEach(e -> System.out.println(e.getName() + " - " + e.getBirthday().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        System.out.println();
    }

    private static void getOlderEmployees(List<Employee> employees) {
        Employee older = employees.stream().max(Comparator.comparing(Employee::getBirthday)).get();
        int age = LocalDate.now().getYear() - older.getBirthday().getYear();
        System.out.println("Funcionário mais velho: " + older.getName() + " - Idade: " + age + " anos");
        System.out.println();
    }

    private static void getEmployeesByAlphabeticalOrder(List<Employee> employees) {
        System.out.println("Funcionários em ordem alfabética:");
        employees.stream().sorted(Comparator.comparing(Employee::getName)).forEach(System.out::println);
        System.out.println();
    }

    private static void getTotalSalaries(List<Employee> employees) {
        BigDecimal totalSalaries = employees.stream().map(Employee::getSalary).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total dos salários: R$ " + totalSalaries);
        System.out.println();
    }

    private static void getMinimunSalaries(List<Employee> employees) {
        DecimalFormat numberFormatter = new DecimalFormat("#,##0.00");
        employees.stream().forEach(f -> System.out.println(f.getName() + " ganha " + numberFormatter.format(f.getSalary().divide(MINIMUM_SALARY, 2, BigDecimal.ROUND_HALF_UP)) + " salários mínimos"));
        System.out.println();
    }
}
