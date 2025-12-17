package de.seuhd.campuscoffee.domain.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.seuhd.campuscoffee.domain.exceptions.DuplicationException;
import de.seuhd.campuscoffee.domain.model.objects.User;
import de.seuhd.campuscoffee.domain.ports.data.UserDataService;
import de.seuhd.campuscoffee.domain.tests.TestFixtures;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Unit and integration tests for the operations related to the generic CrudServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
public class CrudServiceTest {
    @Mock
    private UserDataService userDataService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testClear(){
        //given
        CrudServiceImpl<User, Long> crudService = userService;

        //when
        crudService.clear();

        //then
        assertTrue(crudService.dataService().getAll().isEmpty());
    }

    @Test
    void testUpsertError(){
        //given
        CrudServiceImpl<User, Long> crudService = userService;
        User user = TestFixtures.getUserFixtures().getFirst();

        when(userDataService.upsert(user)).thenThrow(new DuplicationException(User.class, "", ""));

        //when, then
        assertThrows(DuplicationException.class, () -> crudService.upsert(user));
        verify(userDataService).getById(user.id());

    }
}
