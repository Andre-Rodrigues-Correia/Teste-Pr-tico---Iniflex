package models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee extends Person {
    private BigDecimal salary;
    private String function;

    public Employee(String name, LocalDate birthday, BigDecimal salary, String function) {
        super(name, birthday);
        this.salary = salary;
        this.function = function;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getFunction() {
        return function;
    }

    @Override
    public String toString() {
        return super.toString() + ", Função: " + function + ", Salário: " + salary ;
    }
}

