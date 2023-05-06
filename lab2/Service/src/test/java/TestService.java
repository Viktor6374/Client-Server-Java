import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.subbotin.Color;
import ru.subbotin.Services.Service;
import ru.subbotin.dao.Dao;
import ru.subbotin.dao.DaoImpl;
import ru.subbotin.models.Cat;
import ru.subbotin.models.Owner;

public class TestService {
  @Test
  public void addOwners(){
    DaoImpl dao = mock(DaoImpl.class);
    Service service = new Service(dao);

    Owner bob = new Owner("Bob", LocalDate.of(2000, 12, 12));
    Owner bill = new Owner("Bill", LocalDate.of(1999, 11, 11));
    ArrayList<Owner> owners = new ArrayList<>();

    doAnswer(invocation -> owners.add(invocation.getArgument(0))).when(dao).saveOwner(any(Owner.class));
    service.saveOwner(bob);
    service.saveOwner(bill);

    when(dao.getAllOwners()).thenReturn(owners);

    Assertions.assertSame("Bob", service.getAllOwners().get(0).getName());
    Assertions.assertSame("Bill", service.getAllOwners().get(1).getName());

    Cat jack = new Cat("Jack", "British", Color.Black, LocalDate.of(2020, 11, 12), bob);
    Cat john = new Cat("John", "British", Color.Red, LocalDate.of(2017, 5, 1), bill);

    doAnswer(invocation -> {
      Cat cat1 = invocation.getArgument(0);
      Cat cat2 = invocation.getArgument(1);
      cat1.addFriend(cat2);
      cat2.addFriend(cat1);
      return null;
    }).when(dao).addFriendship(any(Cat.class), any(Cat.class));

    service.addFriendship(john, jack);

    Assertions.assertSame("Jack", bob.getCats().get(0).getName());
    Assertions.assertSame("John", bill.getCats().get(0).getName());
    Assertions.assertSame("John", jack.getFriendsOfCat().get(0).getName());
    Assertions.assertSame("Jack", john.getFriendsOfCat().get(0).getName());
  }
}
