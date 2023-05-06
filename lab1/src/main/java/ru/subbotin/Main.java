package ru.subbotin;

import ru.subbotin.banks.CentralBank;
import ru.subbotin.banksConsole.CentralBankConsole;

public class Main {


  public static void main(String[] args) {
    CentralBankConsole.centralBank(CentralBank.getCentralBank());
  }
}