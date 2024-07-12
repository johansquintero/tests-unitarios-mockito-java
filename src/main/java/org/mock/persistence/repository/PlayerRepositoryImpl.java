package org.mock.persistence.repository;

import org.mock.exception.PlayerFoundException;
import org.mock.exception.PlayerNotFoundException;
import org.mock.persistence.entities.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlayerRepositoryImpl implements IPlayerRepository {

    private List<Player> playerDatabase = new ArrayList<>(List.of(
            new Player(1L, "Lionel Messi", "Inter Miami", "Delantero"),
            new Player(2L, "Cristiano Ronaldo", "Al Nassr", "Delantero"),
            new Player(3L, "Neymar Jr.", "Paris Saint-Germain", "Delantero"),
            new Player(4L, "Kylian Mbappé", "Paris Saint-Germain", "Delantero"),
            new Player(5L, "Kevin De Bruyne", "Manchester City", "Volante"),
            new Player(6L, "Virgil van Dijk", "Liverpool", "Defensa")
    ));
    //private List<Player> playerDatabase= new ArrayList<>();
    @Override
    public List<Player> getAll() {
        return this.playerDatabase;
    }

    @Override
    public Optional<Player> getPlayerByName(String name) {
        System.out.println("-> Metodo findByName real");
        return this.playerDatabase.stream().filter(player -> player.getName().contains(name)).findFirst();
    }

    @Override
    public Optional<Player> getPlayerById(Long id) {
        System.out.println("-> Metodo findById real");
        return this.playerDatabase.stream().filter(player -> player.getId()==id).findFirst();
    }

    @Override
    public Optional<Player> save(Player newPlayer) {
        System.out.println("-> Metodo save real");
        Optional<Player> playerOpt = this.getPlayerByName(newPlayer.getName());
        if (playerOpt.isPresent()){
            throw new PlayerFoundException("Error: El usuario ya se encuentra registrado en la base de datos");
        }
        this.playerDatabase.add(newPlayer);
        return this.playerDatabase.stream().filter(player -> player.getId()==newPlayer.getId()).findFirst();
    }

    @Override
    public Optional<Player> update(Player player) {
        System.out.println("-> Metodo update real");
        Optional<Player> playerOpt = this.getPlayerById(player.getId());
        if (playerOpt.isEmpty()){
            throw new PlayerNotFoundException("Error: El usuario no se encuentra registrado en la base de datos");
        }
        this.playerDatabase=this.playerDatabase.stream().map(p->{
            if (p.getId()==player.getId()){
                p=player;
            }
            return p;
        }).collect(Collectors.toList());
        return this.playerDatabase.stream().filter(p -> player.getId()==p.getId()).findFirst();
    }

    @Override
    public void delete(Long id) {
        System.out.println("-> Metodo delete real");
        Optional<Player> playerOpt = this.getPlayerById(id);
        if (playerOpt.isEmpty()){
            throw new PlayerNotFoundException("Error: El usuario no se encuentra registrado en la base de datos");
        }
        this.playerDatabase=this.playerDatabase.stream().filter(player -> player.getId()!=id).collect(Collectors.toList());
    }
}
