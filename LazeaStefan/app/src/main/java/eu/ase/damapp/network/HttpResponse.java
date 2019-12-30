package eu.ase.damapp.network;

import java.util.List;

public class HttpResponse {
    private List<Item> mechanics;
    private List<Item> signs;
    private List<Item> tickets;

    public HttpResponse(List<Item> mechanics, List<Item> signs, List<Item> tickets) {
        this.mechanics = mechanics;
        this.signs = signs;
        this.tickets = tickets;
    }

    public List<Item> getMechanics() {
        return mechanics;
    }

    public void setMechanics(List<Item> mechanics) {
        this.mechanics = mechanics;
    }

    public List<Item> getSigns() {
        return signs;
    }

    public void setSigns(List<Item> signs) {
        this.signs = signs;
    }

    public List<Item> getTickets() {
        return tickets;
    }

    public void setTickets(List<Item> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "mechanics=" + mechanics +
                ", signs=" + signs +
                ", tickets=" + tickets +
                '}';
    }
}
