package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImplementation;

class TestDeathNote {
    private static final String FIRST_TO_KILL = "Mario Rossi";
    private static final String SECOND_TO_KILL = "Antonio Marroni";
    private static final String NOT_TO_KILL = "Luigi Verdi";
    private static final String EMPTY_STRING = "";
    private static final String DEFAULT_DEATH_CAUSE = "Heart attack";
    private static final String ANOTHER_DEATH_CAUSE = "Karting accident";
    private static final String DEATH_DETAILS = "Ran for too long";
    private static final long FIRST_WAITING_TIME = 100;
    private static final long SECOND_WAITING_TIME = 6100;

    private DeathNote deathNote;

    @BeforeEach
    void setUp() {
        this.deathNote = new DeathNoteImplementation();
    }

    @Test
    void testRuleNumber() {
        deathNote.getRule(1);
        try {
            deathNote.getRule(0);
            fail("Getting rule 0 should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertStringNotNullNorBlank(e.getMessage());
        }
        try {
            deathNote.getRule(-1);
            fail("Getting negative rule should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertStringNotNullNorBlank(e.getMessage());
        }
    }

    @Test
    public void testRulesValidity() {
        for (String rule: DeathNote.RULES) {
            assertStringNotNullNorBlank(rule);
        }
    }

    private static void assertStringNotNullNorBlank(final String string) {
        assertNotNull(string);
        assertFalse(string.isBlank());
    }

    @Test
    public void testWriteHumanName() {
        assertFalse(deathNote.isNameWritten(FIRST_TO_KILL));
        deathNote.writeName(FIRST_TO_KILL);
        assertTrue(deathNote.isNameWritten(FIRST_TO_KILL));
        assertFalse(deathNote.isNameWritten(NOT_TO_KILL));
        assertFalse(deathNote.isNameWritten(EMPTY_STRING));
    }

    @Test
    public void testWriteDeathCause () {
        try {
            deathNote.writeDeathCause(ANOTHER_DEATH_CAUSE);
            fail("Writing the death cause before the name should throw an IllegalStateException");
        } catch (IllegalStateException e) {
        }
        // Killing one person with default cause
        deathNote.writeName(FIRST_TO_KILL);
        assertEquals(DEFAULT_DEATH_CAUSE, deathNote.getDeathCause(FIRST_TO_KILL));
        // Killing another person with a specific cause
        deathNote.writeName(SECOND_TO_KILL);
        assertTrue(deathNote.writeDeathCause(ANOTHER_DEATH_CAUSE));
        assertEquals(ANOTHER_DEATH_CAUSE, deathNote.getDeathCause(SECOND_TO_KILL));
        try {
            Thread.sleep(FIRST_WAITING_TIME);
        } catch (InterruptedException e) {
            fail(e.toString());
        }
        assertFalse(deathNote.writeDeathCause(DEFAULT_DEATH_CAUSE));
        assertEquals(ANOTHER_DEATH_CAUSE, deathNote.getDeathCause(SECOND_TO_KILL));
    }

    @Test
    public void testWriteDeathDetails () {
        try {
            deathNote.writeDeathCause(DEATH_DETAILS);
            fail("Writing the death details before the name should throw an IllegalStateException");
        } catch (IllegalStateException e) {
        }
        // Kill a person setting the death's details
        deathNote.writeName(FIRST_TO_KILL);
        assertEquals(EMPTY_STRING, deathNote.getDeathDetails(FIRST_TO_KILL));
        assertTrue(deathNote.writeDetails(DEATH_DETAILS));
        assertEquals(DEATH_DETAILS, deathNote.getDeathDetails(FIRST_TO_KILL));
        // Kill another person waiting too much to set the details
        deathNote.writeName(SECOND_TO_KILL);
        try {
            Thread.sleep(SECOND_WAITING_TIME);
        } catch (InterruptedException e) {
            fail(e.toString());
        }
        assertFalse(deathNote.writeDetails(DEATH_DETAILS));
        assertEquals(EMPTY_STRING, deathNote.getDeathDetails(SECOND_TO_KILL));
    }
}