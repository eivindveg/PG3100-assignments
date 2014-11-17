package no.westerdals.student.vegeiv13.innlevering3.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import no.westerdals.student.vegeiv13.innlevering3.utils.YearTranslator;

import java.time.Year;

@DatabaseTable(tableName = "albums")
public class Album {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(canBeNull = false)
    private String title;

    @DatabaseField(canBeNull = false)
    private String artist;

    @DatabaseField(canBeNull = false)
    private String genre;
    @DatabaseField(canBeNull = false)
    private int tracks;
    @DatabaseField(canBeNull = false, persisterClass = YearTranslator.class)
    private Year releasedYear;

    /**
     * Declared no-arg constructor required by ORMLite. Needlessly declared to assure we still have this constructor
     * available if another constructor is declared.
     */
    public Album() {
        // no-arg for ormlite
    }

    public Year getReleasedYear() {
        return releasedYear;
    }

    public void setReleasedYear(final Year releasedYear) {
        this.releasedYear = releasedYear;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(final int tracks) {
        this.tracks = tracks;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(final String genre) {
        this.genre = genre;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(final String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String toString() {
        return new StringBuilder().append(getArtist()).append(" (").append(getTitle()).append(") | ").append(tracks).append(" tracks | released in ").append(getReleasedYear()).toString();
    }
}
