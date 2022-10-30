package br.com.brunadelmouro.songproducer.models;

import br.com.brunadelmouro.songproducer.models.enums.Genre;
import br.com.brunadelmouro.songproducer.models.enums.Nationality;

import java.io.Serializable;

public class Band implements Serializable {

    private String name;
    private Genre genre;
    private Nationality nationality;

    public Band() {
    }

    public Band(final String name, final Genre genre, final Nationality nationality) {
        this.name = name;
        this.genre = genre;
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(final Genre genre) {
        this.genre = genre;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(final Nationality nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "Band{" +
                "name='" + name + '\'' +
                ", genre=" + genre +
                ", nationality=" + nationality +
                '}';
    }
}
