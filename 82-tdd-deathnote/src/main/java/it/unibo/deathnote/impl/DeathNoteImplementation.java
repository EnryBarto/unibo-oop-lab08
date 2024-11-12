package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Objects;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.api.DeathNotePage;

/**
 * An implementation of the interface {@link DeathNote}
 */
public class DeathNoteImplementation implements DeathNote {
    /**
     * If you don't specify a death cause in the given time (see {@link MAX_CAUSE_WAITING_TIME}),
     * it will be set as {@value #DEFAULT_DEATH_CAUSE}
     */
    public static final String DEFAULT_DEATH_CAUSE = "Heart attack";
    /**
     * If you don't specify details of the death in the given time (see {@link MAX_DETAILS_WAITING_TIME}),
     * it will be set as {@value #DEFAULT_DEATH_DETAILS}
     */
    public static final String DEFAULT_DEATH_DETAILS = "";
    /**
     * Maximum time to specify the cause of the death after writing a name in a new page.
     * It is set to {@value #MAX_CAUSE_WAITING_TIME} milliseconds.
     */
    public static final long MAX_CAUSE_WAITING_TIME = 40;
    /**
     * Maximum time to specify the details of the death after writing a name in a new page.
     * It is set to {@value #MAX_DETAILS_WAITING_TIME} milliseconds.
     */
    public static final long MAX_DETAILS_WAITING_TIME = 6040;

    private final HashMap<String, DeathNotePage> deathNote;
    private DeathNotePage lastWrittenPage;

    /**
     * Initialize a new DeathNote.
     */
    public DeathNoteImplementation() {
        this.deathNote = new HashMap<String, DeathNotePage>();
    }

    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber <= 0) {
            throw new IllegalArgumentException("The rule number must be greater than 0");
        }
        return DeathNote.RULES.get(ruleNumber + 1);
    }

    @Override
    public void writeName(String name) {
        Objects.requireNonNull(name);
        lastWrittenPage = new DeathNotePageImpl(name);
        deathNote.put(name, lastWrittenPage);
    }

    @Override
    public boolean writeDeathCause(String cause) {
        Objects.requireNonNull(cause);
        if (cause.isBlank()) {
            throw new IllegalArgumentException("The cause of death is blank");
        }
        if (lastWrittenPage == null) {
            throw new IllegalStateException("You first have to write a name!");
        }
        return lastWrittenPage.setDeathCause(cause);
    }

    @Override
    public boolean writeDetails(String details) {
        Objects.requireNonNull(details);
        if (lastWrittenPage == null) {
            throw new IllegalStateException("You first have to write a name!");
        }
        return lastWrittenPage.setDeathDetails(details);
    }

    @Override
    public String getDeathCause(String name) {
        Objects.requireNonNull(name);
        return this.deathNote.get(name).getDeathCause();
    }

    @Override
    public String getDeathDetails(String name) {
        Objects.requireNonNull(name);
        return this.deathNote.get(name).getDeathDetails();
    }

    @Override
    public boolean isNameWritten(String name) {
        Objects.requireNonNull(name);
        return this.deathNote.containsKey(name);
    }

}