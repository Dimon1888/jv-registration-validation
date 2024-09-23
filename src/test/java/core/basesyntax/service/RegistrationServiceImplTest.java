package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationServiceImpl;
    private User user;

    @BeforeEach
    void setUp() {
        registrationServiceImpl = new RegistrationServiceImpl();
        user = new User();
        Storage.people.clear();
    }

    @Test
    void userIsValid_ok() {
        User existingUser = new User();
        existingUser.setLogin("differentLogin");
        Storage.people.add(existingUser);
        user.setAge(18);
        user.setLogin("validLogin");
        user.setPassword("validP");
        User registeredUser = registrationServiceImpl.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void registre_ageLessThan18_notOk() {
        user.setAge(17);
        user.setLogin("validLogin");
        user.setPassword("validPass");
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void registr_negativeAge_notOk() {
        user.setAge(-5);
        user.setLogin("validLogin");
        user.setPassword("validePasssword");
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void registr_ageEdgeCase_notOk() {
        user.setAge(17);
        user.setLogin("validLogin");
        user.setPassword("validePasssword");
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void registre_ageEquals18_ok() {
        user.setAge(18);
        user.setLogin("valLog");
        user.setPassword("valPas");
        User registeredUser = registrationServiceImpl.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void registre_LogineExist_notOk() {
        User existingUser = new User();
        existingUser.setLogin("validLogin");
        Storage.people.add(existingUser);
        user.setAge(20);
        user.setLogin("validLogin");
        user.setPassword("validPass");
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void registre_LoginLength_notOk() {
        user.setAge(20);
        user.setLogin("log");
        user.setPassword("validPass");
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void registre_LogineLengtthEgeCaseEquals15_notOk() {
        user.setAge(20);
        user.setLogin("valid");
        user.setPassword("validPass");
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_LoginLengEquals_ok() {
        user.setAge(18);
        user.setLogin("validL");
        user.setPassword("validP");
        User registeredUser = registrationServiceImpl.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_PasswordLength_notOk() {
        user.setAge(20);
        user.setLogin("validLogin");
        user.setPassword("pass");
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_PasswordLength_Ok() {
        user.setAge(18);
        user.setLogin("validLogin");
        user.setPassword("validP");
        User registeredUser = registrationServiceImpl.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void registr_PasswordLengtchEdgeCaseEqals_notOk() {
        user.setAge(20);
        user.setLogin("validLogin");
        user.setPassword("valid");
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void registr_PasswordLengthEqalse_Ok() {
        user.setAge(20);
        user.setLogin("validLogin");
        user.setPassword("validP");
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void registre_LoginNotNull_notOk() {
        user.setAge(20);
        user.setLogin(null);
        user.setPassword("validPass");
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void registre_PasswordNotNull_notOk() {
        user.setAge(20);
        user.setLogin("validLogin");
        user.setPassword(null);
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }
}
