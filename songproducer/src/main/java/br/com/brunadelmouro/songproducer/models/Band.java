package br.com.brunadelmouro.songproducer.models;

import br.com.brunadelmouro.songproducer.models.enums.Genre;
import br.com.brunadelmouro.songproducer.models.enums.Nationality;

import java.io.Serializable;

public class Band implements Serializable {

    private String name;
    private Genre bandGenre;
    private Nationality bandNationality;

    public Band() {
    }

    public Band(final String name, final Genre genre, final Nationality nationality) {
        this.name = name;
        this.bandGenre = genre;
        this.bandNationality = nationality;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Genre getBandGenre() {
        return bandGenre;
    }

    public void setBandGenre(final Genre bandGenre) {
        this.bandGenre = bandGenre;
    }

    public Nationality getBandNationality() {
        return bandNationality;
    }

    public void setBandNationality(final Nationality bandNationality) {
        this.bandNationality = bandNationality;
    }

    @Override
    public String toString() {
        return "Band{" +
                "name='" + name + '\'' +
                ", genre=" + bandGenre +
                ", nationality=" + bandNationality +
                '}';
    }
}
