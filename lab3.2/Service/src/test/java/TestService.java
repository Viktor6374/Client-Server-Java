import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.aspectj.util.Reflection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import ru.subbotin.Color;
import ru.subbotin.DTO.OwnerDTO;
import ru.subbotin.dao.DaoRepositoryCats;
import ru.subbotin.dao.DaoRepositoryOwners;
import ru.subbotin.entity.Cat;
import ru.subbotin.entity.Owner;
import ru.subbotin.service.CatsServiceImpl;

public class TestService {
    private final DaoRepositoryCats daoCat = mock(DaoRepositoryCats.class);
    private final DaoRepositoryOwners daoOwner = mock(DaoRepositoryOwners.class);
    @Test
    public void addOwners(){
        CatsServiceImpl service = new CatsServiceImpl();
        ReflectionTestUtils.setField(service, "daoCat", daoCat);
        ReflectionTestUtils.setField(service, "daoOwner", daoOwner);

        Owner bob = new Owner(1, "Bob");
        Owner bill = new Owner(2, "Bill");
        OwnerDTO bobDTO = new OwnerDTO(bob);
        OwnerDTO billDTO = new OwnerDTO(bill);

        ArrayList<Owner> owners = new ArrayList<>();

        doAnswer(invocation -> { owners.add(invocation.getArgument(0)); return null; }).when(daoOwner).save(any(Owner.class));

        when(daoOwner.findById(any(int.class))).thenReturn(Optional.empty());

        service.saveOwner(bobDTO);
        service.saveOwner(billDTO);

        when(daoOwner.findAll()).thenReturn(owners);

        Assertions.assertSame("Bob", service.getAllOwners().get(0).getName());
        Assertions.assertSame("Bill", service.getAllOwners().get(1).getName());

        Cat jack = new Cat(1, "Jack", "British", Color.Black, bob);
        Cat john = new Cat(2, "John", "British", Color.Red, bill);

        ArrayList<Cat> cats = new ArrayList<>();

        doAnswer(invocation -> { return null; }).when(daoCat).save(any(Cat.class));

        doAnswer(invocation -> {
            if (jack.getId() == invocation.getArgument(0))
                return Optional.of(jack);
            if (john.getId() == invocation.getArgument(0))
                return Optional.of(john);
            return Optional.empty();
        }).when(daoCat).findById(any(int.class));

        service.addFriendship(john.getId(), jack.getId());

        Assertions.assertSame("Jack", bob.getCats().get(0).getName());
        Assertions.assertSame("John", bill.getCats().get(0).getName());
        Assertions.assertSame("John", jack.getFriendsOfCat().get(0).getName());
        Assertions.assertSame("Jack", john.getFriendsOfCat().get(0).getName());
    }
}

