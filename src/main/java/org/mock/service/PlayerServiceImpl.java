package org.mock.service;

import org.mock.exception.PlayerFoundException;
import org.mock.exception.PlayerNotFoundException;
import org.mock.persistence.entities.Player;
import org.mock.persistence.repository.PlayerRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class PlayerServiceImpl implements IPlayerService{
    private final PlayerRepositoryImpl playerRepository;
    public PlayerServiceImpl(PlayerRepositoryImpl playerRepository){
        this.playerRepository=playerRepository;
    }

    @Override
    public List<Player> getAll() {
        return this.playerRepository.getAll();
    }

    @Override
    public Player getPlayerByName(String name) {
        return this.playerRepository.getPlayerByName(name).orElse(new Player());
    }

    @Override
    public Player getPlayerById(Long id) {
        return this.playerRepository.getPlayerById(id).orElse(new Player());
    }

    @Override
    public Player save(Player newPlayer) throws PlayerFoundException{
        return this.playerRepository.save(newPlayer).orElse(new Player());
    }

    @Override
    public Player update(Player player) throws PlayerNotFoundException{
        return this.playerRepository.update(player).orElse(new Player());
    }

    @Override
    public void delete(Long id) throws PlayerNotFoundException{
        this.playerRepository.delete(id);
    }
}
