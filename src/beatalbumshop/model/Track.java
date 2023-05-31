package beatalbumshop.model;

public class Track {
    private String trackID;
    private String trackName;
    private long durationMS;

    public Track() {
    }

    public Track(String trackID, String trackName, long durationMS) {
        this.trackID = trackID;
        this.trackName = trackName;
        this.durationMS = durationMS;
    }

    public String getTrackID() {
        return trackID;
    }

    public void setTrackID(String trackID) {
        this.trackID = trackID;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public long getDurationMS() {
        return durationMS;
    }

    public void setDurationMS(long durationMS) {
        this.durationMS = durationMS;
    }
}
