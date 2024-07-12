package org.mock.persistence.repository;

import org.mock.persistence.entities.Player;

import java.util.List;
import java.util.Optional;

public interface IPlayerRepository {
    List<Player> getAll();
    Optional<Player> getPlayerByName(String name);
    Optional<Player> getPlayerById(Long id);
    Optional<Player> save(Player newPlayer);
    Optional<Player> update(Player player);
    void delete(Long id);
}
