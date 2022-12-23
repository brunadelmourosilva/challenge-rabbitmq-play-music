package br.com.brunadelmouro.songproducer.models.enums;

public enum Genre {
    ROCK("rock"), POP("pop"), INDIE("indie"), ANY("");

    private String genre;

    Genre( String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }
}
