package br.com.brunadelmouro.songproducer.models.enums;

public enum Nationality {
    NATIONAL("national"), INTERNATIONAL("international");

    private String nationality;

    Nationality(final String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }
}
