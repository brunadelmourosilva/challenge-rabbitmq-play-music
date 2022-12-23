package br.com.brunadelmouro.songproducer.models;

import java.io.Serializable;

public class SongRequest implements Serializable {

    private String musicName;
    private Integer releaseYear;
    private BandRequest band;

    public SongRequest() {
    }

    public SongRequest( String musicName,  Integer releaseYear,  BandRequest band) {
        this.musicName = musicName;
        this.releaseYear = releaseYear;
        this.band = band;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName( String musicName) {
        this.musicName = musicName;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear( Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public BandRequest getBand() {
        return band;
    }

    public void setBand( BandRequest bandRequest) {
        this.band = bandRequest;
    }

    @Override
    public String toString() {
        return "SongRequest{" +
                "musicName='" + musicName + '\'' +
                ", releaseYear=" + releaseYear +
                ", bandRequest=" + band +
                '}';
    }
}
