import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.subbotin.banks.Bank;
import ru.subbotin.banks.CentralBank;
import ru.subbotin.banks.Client;
import ru.subbotin.banks.Debit;
import ru.subbotin.banks.Doubtful;
import ru.subbotin.banks.InterestForDeposit;

public class BankTest {
  @Test
  public void addDebit(){
    var centralBank = CentralBank.getCentralBank();
    Bank sber = centralBank.addBank("Sber");
    sber.setInterestForDebit(3);
    sber.setLimitTransactionForDoubtful(10000);
    sber.setLimitWithdrawForDoubtful(10000);
    Client adolf = sber.createClient("Adolf", "Hitler", null, null);
    Client iosif = sber.createClient("Iosif", "Stalin", "USSR", 1111111111111111L);

    sber.addDebit(adolf);
    sber.addDebit(iosif);

    Assertions.assertTrue(iosif.getAccounts().get(0) instanceof Debit && !(iosif.getAccounts().get(0) instanceof Doubtful));
    Assertions.assertTrue(adolf.getAccounts().get(0) instanceof Doubtful);

    iosif.getAccounts().get(0).replenish(1000);
    adolf.getAccounts().get(0).replenish(1000);

    Assertions.assertEquals(1000, iosif.getAccounts().get(0).getAmount());
    Assertions.assertEquals(1000, adolf.getAccounts().get(0).getAmount());

    centralBank.transaction(iosif.getAccounts().get(0), adolf.getAccounts().get(0), 500);

    Assertions.assertEquals(500, iosif.getAccounts().get(0).getAmount());
    Assertions.assertEquals(1500, adolf.getAccounts().get(0).getAmount());

    centralBank.cancellationTransaction(iosif.getAccounts().get(0).getTransactions().get(0));

    Assertions.assertEquals(1000, iosif.getAccounts().get(0).getAmount());
    Assertions.assertEquals(1000, adolf.getAccounts().get(0).getAmount());

    for (int i = 0; i < 364; i++){
      centralBank.notifyObservers(false);
    }

    centralBank.notifyObservers(true);

    Assertions.assertEquals(1030, Math.round(iosif.getAccounts().get(0).getAmount()));
    Assertions.assertEquals(1030, Math.round(adolf.getAccounts().get(0).getAmount()));

    Assertions.assertThrows(IllegalArgumentException.class, () -> iosif.getAccounts().get(0).withdraw(1500));
  }

  @Test
  public void AddDeposit(){
    var centralBank = CentralBank.getCentralBank();
    Bank sber = centralBank.addBank("Sber");

    InterestForDeposit.BuilderInterest builder = InterestForDeposit.getBuilderInterest();
    builder.addLevel(500, 3);
    builder.addLevel(2000, 4);
    builder.addLevel(3000, 5);
    sber.setInterestForDeposit(builder.build());

    Client adolf = sber.createClient("Adolf", "Hitler", null, null);
    Client iosif = sber.createClient("Iosif", "Stalin", "USSR", 1111111111111111L);

    LocalDate dateNow = LocalDate.now().minusDays(1);
    sber.addDeposit(adolf, dateNow.minusDays(-366));
    sber.addDeposit(iosif, dateNow.minusDays(-366));

    iosif.getAccounts().get(0).replenish(1000);
    adolf.getAccounts().get(0).replenish(1000);

    Assertions.assertEquals(1000, iosif.getAccounts().get(0).getAmount());
    Assertions.assertEquals(1000, adolf.getAccounts().get(0).getAmount());

    Assertions.assertThrows(Exception.class, () -> iosif.getAccounts().get(0).withdraw(500));

    for (int i = 0; i < 364; i++){
      centralBank.notifyObservers(false);
    }

    centralBank.notifyObservers(true);
    centralBank.notifyObservers(false);

    Assertions.assertEquals(1040, Math.round(iosif.getAccounts().get(0).getAmount()));
    Assertions.assertEquals(1040, Math.round(adolf.getAccounts().get(0).getAmount()));

    centralBank.transaction(iosif.getAccounts().get(0), adolf.getAccounts().get(0), 500);

    Assertions.assertEquals(540, Math.round(iosif.getAccounts().get(0).getAmount()));
    Assertions.assertEquals(1540, Math.round(adolf.getAccounts().get(0).getAmount()));

    centralBank.cancellationTransaction(iosif.getAccounts().get(0).getTransactions().get(0));

    Assertions.assertEquals(1040, Math.round(iosif.getAccounts().get(0).getAmount()));
    Assertions.assertEquals(1040, Math.round(adolf.getAccounts().get(0).getAmount()));

    Assertions.assertThrows(IllegalArgumentException.class, () -> iosif.getAccounts().get(0).withdraw(1500));
  }

  @Test
  public void AddCredit(){
    var centralBank = CentralBank.getCentralBank();
    Bank sber = centralBank.addBank("Sber");

    sber.setLimitForCredit(10000);
    sber.setCommissionForCredit(10);
    sber.setLimitTransactionForDoubtful(20000);
    sber.setLimitWithdrawForDoubtful(20000);

    Client adolf = sber.createClient("Adolf", "Hitler", null, null);
    Client iosif = sber.createClient("Iosif", "Stalin", "USSR", 1111111111111111L);

    sber.addCredit(adolf);
    sber.addCredit(iosif);

    iosif.getAccounts().get(0).replenish(1000);
    adolf.getAccounts().get(0).replenish(1000);

    Assertions.assertEquals(1000, iosif.getAccounts().get(0).getAmount());
    Assertions.assertEquals(1000, adolf.getAccounts().get(0).getAmount());

    centralBank.transaction(iosif.getAccounts().get(0), adolf.getAccounts().get(0), 1500);

    Assertions.assertEquals(-510, iosif.getAccounts().get(0).getAmount());
    Assertions.assertEquals(2500, adolf.getAccounts().get(0).getAmount());

    centralBank.cancellationTransaction(iosif.getAccounts().get(0).getTransactions().get(0));

    Assertions.assertEquals(1000, iosif.getAccounts().get(0).getAmount());
    Assertions.assertEquals(1000, adolf.getAccounts().get(0).getAmount());

    Assertions.assertThrows(IllegalArgumentException.class, () -> iosif.getAccounts().get(0).withdraw(15000));
  }
}
