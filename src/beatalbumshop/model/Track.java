package beatalbumshop.model;

/**
 * The Track class represents a music track.
 */
public class Track {
    private String trackID;
    private String trackName;
    private long durationMS;

    /**
     * Default constructor for the Track class.
     */
    public Track() {
    }

    /**
     * Constructor for the Track class with track ID, track name, and duration in milliseconds parameters.
     *
     * @param trackID     the ID of the track
     * @param trackName   the name of the track
     * @param durationMS  the duration of the track in milliseconds
     */
    public Track(String trackID, String trackName, long durationMS) {
        this.trackID = trackID;
        this.trackName = trackName;
        this.durationMS = durationMS;
    }

    /**
     * Returns the ID of the track.
     *
     * @return the ID of the track
     */
    public String getTrackID() {
        return trackID;
    }

    /**
     * Sets the ID of the track.
     *
     * @param trackID the ID to set
     */
    public void setTrackID(String trackID) {
        this.trackID = trackID;
    }

    /**
     * Returns the name of the track.
     *
     * @return the name of the track
     */
    public String getTrackName() {
        return trackName;
    }

    /**
     * Sets the name of the track.
     *
     * @param trackName the name to set
     */
    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    /**
     * Returns the duration of the track in milliseconds.
     *
     * @return the duration of the track in milliseconds
     */
    public long getDurationMS() {
        return durationMS;
    }

    /**
     * Sets the duration of the track in milliseconds.
     *
     * @param durationMS the duration to set
     */
    public void setDurationMS(long durationMS) {
        this.durationMS = durationMS;
    }
}
