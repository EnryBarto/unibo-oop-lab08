package it.unibo.deathnote.api;

/**
 * A DeathNotePage is a page of the book {@link DeathNote}.
 * In every page you can write the name of the person you want to kill, the death's cause and details.
 */
public interface DeathNotePage {

    /**
     * Returns the name written in this page.
     * 
     * @return The name written in this page
     */
    String getName();

    /**
     * Returns the epoch time in which the page was created.
     * 
     * @return The epoch time in which the page was created
     */
    long getStartTime();

    /**
     * Returns the cause of the death relating to the person written in this page.
     * 
     * @return The cause of the death written in this page
     */
    String getDeathCause();

    /**
     * Writes the cause of the death in the page, only if it's possible.
     * 
     * @param deathCause The cause of the death to write in the page 
     * @return true if the cause has been set correctly, false otherwise 
     */
    boolean setDeathCause(String deathCause);

    /**
     * Returns the details of the death relating to the person written in this page.
     * 
     * @return The details of the death written in this page
     */
    String getDeathDetails();

    /**
     * Writes the details of the death in the page, only if it's possible.
     * 
     * @param deathDetails The details of the death to write in the page 
     * @return true if the details have been set correctly, false otherwise 
     */
    boolean setDeathDetails(String deathDetails);

}