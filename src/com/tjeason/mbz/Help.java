package com.tjeason.mbz;

/**
 * Provides help tips for the command line arguments.
 * @author T.J. Eason <tjeason@gmail.com>
 * @version 1.0
 */
public class Help {

    /**
     * Constructor.
     */
    Help() {}

    /**
     * Display user guide to learn how to use the --artist <name> options.
     */
    public void showArtistOptions() {
        System.out.println("usage: mbz --artist <name>");
        System.out.println("Tip 1: Enter the artist's name with a space after the option (i.e. nirvana).");
        System.out.println("Tip 2: For artists with more than one word in their names, use quotes (i.e. \"taylor swift\"");
    }

    /**
     * Display user guide of available options with descriptions.
     */
    public void showOptions() {
        System.out.println("usage: mbz");
        System.out.println("--artist <name> \t search for tracks by artist name.");
        System.out.println("--help \t\t\t prints this message.");
    }
}
