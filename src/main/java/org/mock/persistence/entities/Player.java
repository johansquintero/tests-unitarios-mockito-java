package org.mock.persistence.entities;

public class Player {
    private Long id;
    private String name;
    private String team;
    private String position;

    public Player(Long id,String name, String team, String position) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.position = position;
    }

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", team='" + team + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
