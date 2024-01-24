package dev.fklein.demoservice.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testToString() {
        User a = new User("dnukem", "Duke", "Nukem", "duke@nukem.com", "boss", "NUKE1");
        assertEquals("""
                User{id=null, userName='dnukem', firstName='Duke', lastName='Nukem', email='duke@nukem.com', role='boss', ssn='NUKE1'}\
                """,
                a.toString());
    }

    @Test
    void testEquals_equal() {
        User a = new User("dnukem", "Duke", "Nukem", "duke@nukem.com", "boss", "NUKE1");
        User b = new User("dnukem", "Duke", "Nukem", "duke@nukem.com", "boss", "NUKE1");
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testEquals_same() {
        User a = new User("dnukem", "Duke", "Nukem", "duke@nukem.com", "boss", "NUKE1");
        assertTrue(a.equals(a));
    }

    @Test
    void testEquals_differ() {
        User a = new User("dnukem", "Duke", "Nukem", "duke@nukem.com", "boss", "NUKE1");
        User b = new User("dnukem", "Duke", "Nukem", "duke@nukem.com", "boss", "NUKE1");
        b.setId(123L);
        assertFalse(a.equals(b));
        assertFalse(b.equals(a));
        assertNotEquals(a.hashCode(), b.hashCode());
    }

}