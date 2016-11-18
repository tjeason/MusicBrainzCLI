**Description:**

A Java command-line program that uses the musicbrainz.org web service API to query all songs an artist has released.

Built using Java 1.8.0_40

 **Dependencies:**

 MusicBrainCLI/lib/*.jar [Apache HTTPClient API]

 **To Compile:**

 `javac -classpath "../MusicBrainzCLI/lib/*" com/tjeason/mbz/*.java`

To Run on Linux/Mac OS X (Windows add semicolon instead of colon):

`java -classpath ".:../MusicBrainzCLI/lib/*" com.tjeason.mbz.Main <option> <name>`

**Options:**

`--artist <name of artist>` Queries released songs from artist. (Ex. --artist "the beatles")

`--help` Displays available options and tips.
