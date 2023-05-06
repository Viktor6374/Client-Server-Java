package ru.subbotin;


import ru.subbotin.Services.Service;
import ru.subbotin.dao.DaoImpl;
import ru.subbotin.models.Owner;

public class Main {

  public static void main(String[] args) {
    Service service = new Service(new DaoImpl());
    service.saveOwner(new Owner());
  }

}