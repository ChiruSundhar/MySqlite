package com.example.mysqlite;

public class User  {
 private    int id;
    private String firstname, lastname;
    private int salary;

    public User(int id, String firstname, String lastname, int salary) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getfirstname() {
        return firstname;
    }

    public void setfirstname(String firstname) {
       this.firstname=firstname;
    }

    public String getlastname() {
        return lastname;
    }
    public void  setlastname(String lastname) {
        this.lastname=lastname;
    }

    public int getsalary() {
        return salary;
    }
    public void setsalary(int salary) {
        this.salary= salary;
    }

}
