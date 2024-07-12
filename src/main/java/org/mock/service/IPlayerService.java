package org.mock.service;

import org.mock.persistence.entities.Player;

import java.util.List;

public interface IPlayerService {
    List<Player> getAll();
    Player getPlayerByName(String name);
    Player getPlayerById(Long id);
    Player save(Player newPlayer);
    Player update(Player player);
    void delete(Long id);
}
