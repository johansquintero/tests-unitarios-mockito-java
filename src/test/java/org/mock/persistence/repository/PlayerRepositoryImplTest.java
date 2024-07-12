package org.mock.persistence.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mock.DataProvider;
import org.mock.exception.PlayerFoundException;
import org.mock.exception.PlayerNotFoundException;
import org.mock.persistence.entities.Player;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PlayerRepositoryImplTest {
    private PlayerRepositoryImpl playerRepository;
    @BeforeEach
    void setUp(){
        this.playerRepository = new PlayerRepositoryImpl();
    }
    @Test
    void getAll() {
        List<Player> result = this.playerRepository.getAll();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Kevin De Bruyne",result.get(4).getName());
        assertEquals("Manchester City",result.get(4).getTeam());
        assertEquals("Volante",result.get(4).getPosition());
        assertEquals(5L,result.get(4).getId());

    }

    @Test
    void getPlayerByName() {
        //given
        String name = "Kevin De Bruyne";
        //when
        Optional<Player> result = this.playerRepository.getPlayerByName(name);
        //then
        assertFalse(result.isEmpty());
        assertEquals("Kevin De Bruyne",result.get().getName());
        assertEquals("Manchester City",result.get().getTeam());
        assertEquals("Volante",result.get().getPosition());
        assertEquals(5L,result.get().getId());
    }

    @Test
    void getPlayerById() {
        //given
        Long id = 5L;
        //when
        Optional<Player> result = this.playerRepository.getPlayerById(id);
        assertFalse(result.isEmpty());
        assertEquals("Kevin De Bruyne",result.get().getName());
        assertEquals("Manchester City",result.get().getTeam());
        assertEquals("Volante",result.get().getPosition());
        assertEquals(5L,result.get().getId());
    }

    @Test
    void save() {
        //Given
        Player newPlayer = DataProvider.newPlayerMock();
        //when
        Optional<Player> result = this.playerRepository.save(newPlayer);
        //then
        assertFalse(result.isEmpty());
        assertEquals(7L,result.get().getId());
        assertEquals("Lamine Yamal",result.get().getName());
        assertEquals("Barcelona",result.get().getTeam());
        assertEquals("Delantero",result.get().getPosition());
    }
    @Test
    void save_playerFoundException(){
        //give
        Player newPlayer = DataProvider.playerMock();
        //when-then
        assertThrows(PlayerFoundException.class,()->{
           this.playerRepository.save(newPlayer);
        });
    }

    @Test
    void update() {
        //given
        Player player = DataProvider.playerUpdateMock();
        //when
        Optional<Player> result = this.playerRepository.update(player);
        //then
        assertTrue(result.isPresent());
        assertEquals("Kylian Mbappé",result.get().getName());
        assertEquals("Real Madrid",result.get().getTeam());
        assertEquals("Delantero",result.get().getPosition());
        assertEquals(4L,result.get().getId());
    }

    @Test
    void update_playerNotFoundException(){
        //given
        Player player = DataProvider.newPlayerMock();
        //when-then
        assertThrows(PlayerNotFoundException.class,()->{
            this.playerRepository.update(player);
        });
    }

    @Test
    void delete() {
        //given
        Long id = 2L;
        //when
        this.playerRepository.delete(id);
        //then
        assertInstanceOf(Long.class,id);
    }
    @Test
    void delete_playerNotFoundException(){
        //given
        Long id = 20L;
        //when-then
        assertThrows(PlayerNotFoundException.class,()->{
           this.playerRepository.delete(id);
        });
    }
}