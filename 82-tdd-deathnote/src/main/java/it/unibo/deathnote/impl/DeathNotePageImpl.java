package it.unibo.deathnote.impl;

import java.util.Objects;

import it.unibo.deathnote.api.DeathNotePage;

/**
 * An implementation of the interface {@link DeathNotePage}
 */
public class DeathNotePageImpl implements DeathNotePage {
    private final String name;
    private final long startTime;
    private String deathCause;
    private String deathDetails;

    /**
     * Creates a new page for a {@link it.unibo.deathnote.api.DeathNote}.
     * 
     * @param name The name of the person to write in the page
     */
    public DeathNotePageImpl(final String name) {
        Objects.requireNonNull(name);
        this.name = name;
        this.startTime = System.currentTimeMillis();
        this.deathCause = DeathNoteImplementation.DEFAULT_DEATH_CAUSE;
        this.deathDetails = DeathNoteImplementation.DEFAULT_DEATH_DETAILS;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getStartTime() {
        return startTime;
    }

    @Override
    public String getDeathCause() {
        return deathCause;
    }

    @Override
    public boolean setDeathCause(String deathCause) {
        if (System.currentTimeMillis() - this.startTime <= DeathNoteImplementation.MAX_CAUSE_WAITING_TIME) {
            this.deathCause = deathCause;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getDeathDetails() {
        return deathDetails;
    }

    @Override
    public boolean setDeathDetails(String deathDetails) {
        if (System.currentTimeMillis() - this.startTime <= DeathNoteImplementation.MAX_DETAILS_WAITING_TIME) {
            this.deathDetails = deathDetails;
            return true;
        } else {
            return false;
        }
    }
}
