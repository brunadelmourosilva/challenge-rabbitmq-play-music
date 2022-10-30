package br.com.brunadelmouro.songproducer.models;

import java.io.Serializable;

public class SongRequest implements Serializable {

    private String musicName;
    private Integer releaseYear;
    private Band band;

    public SongRequest() {
    }

    public SongRequest(final String musicName, final Integer releaseYear, final Band band) {
        this.musicName = musicName;
        this.releaseYear = releaseYear;
        this.band = band;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(final String musicName) {
        this.musicName = musicName;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(final Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Band getBand() {
        return band;
    }

    public void setBand(final Band band) {
        this.band = band;
    }

    @Override
    public String toString() {
        return "SongRequest{" +
                "musicName='" + musicName + '\'' +
                ", releaseYear=" + releaseYear +
                ", band=" + band +
                '}';
    }
}
