package com.example.freeforplay.Modelos;

public class Videojuego {
    //Atributos
    private String id;
    private String title;
    private String thumbnail;
    private String descripcion;
    private String genero;
    private String plataforma;
    private String publisher;
    private String desarrollador;
    private String fechaSalida;

    //Constructor
    public Videojuego(String id, String title, String thumbnail, String descripcion, String genero, String plataforma, String publisher, String desarrollador, String fechaSalida) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.descripcion = descripcion;
        this.genero = genero;
        this.plataforma = plataforma;
        this.publisher = publisher;
        this.desarrollador = desarrollador;
        this.fechaSalida = fechaSalida;
    }

    //Getter y setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
}
