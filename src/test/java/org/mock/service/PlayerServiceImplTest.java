package org.mock.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mock.DataProvider;
import org.mock.exception.PlayerFoundException;
import org.mock.exception.PlayerNotFoundException;
import org.mock.persistence.entities.Player;
import org.mock.persistence.repository.PlayerRepositoryImpl;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    @Mock
    private PlayerRepositoryImpl playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

//    Alternativa si no se quiere utilizar la anotacion @ExtendWith
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void testGetAll() {
        //WHEN
        when(this.playerRepository.getAll()).thenReturn(DataProvider.playerListMock());
        List<Player> result = this.playerService.getAll();
        //THEN
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Lionel Messi", result.get(0).getName());
        assertEquals("Inter Miami", result.get(0).getTeam());

        verify(this.playerRepository).getAll();
    }

    @Test
    void testFindById() {
        //GIVEN
        Long id = 2L;
        //WHEN
        when(this.playerRepository.getPlayerById(anyLong())).thenReturn(
                Optional.of(DataProvider.playerMock())
        );
        Player result = this.playerService.getPlayerById(id);
        //THEN
        assertNotNull(result);
        assertEquals("Cristiano Ronaldo", result.getName());
        assertEquals("Al Nassr", result.getTeam());
        assertEquals("Delantero", result.getPosition());

        verify(this.playerRepository,times(1)).getPlayerById(anyLong());
    }

    @Test
    void testFindByName() {
        //GIVEN
        String name = "Cristiano Ronaldo";
        //WHEN
        when(this.playerRepository.getPlayerByName(anyString())).thenReturn(
                Optional.of(DataProvider.playerMock())
        );
        Player result = this.playerService.getPlayerByName(name);
        //THEN
        assertNotNull(result);
        assertEquals("Cristiano Ronaldo", result.getName());
        assertEquals("Al Nassr", result.getTeam());
        assertEquals("Delantero", result.getPosition());
        verify(this.playerRepository).getPlayerByName(anyString());
    }

    @Test
    void testSave() {
        //GIVEN
        Player newPlayer = DataProvider.newPlayerMock();
        //WHEN
        when(this.playerRepository.save(any(Player.class))).thenReturn(
                Optional.of(DataProvider.newPlayerMock())
        );
        Player result = this.playerService.save(newPlayer);
        //THEN
        assertNotNull(result);
        assertEquals("Lamine Yamal", result.getName());
        assertEquals("Barcelona", result.getTeam());
        assertEquals("Delantero", result.getPosition());

        //evaluador de argumentos
        ArgumentCaptor<Player> playerArgumentCaptor = ArgumentCaptor.forClass(Player.class);
        verify(this.playerRepository).save(playerArgumentCaptor.capture());
        assertEquals(7L,playerArgumentCaptor.getValue().getId());
        assertEquals("Lamine Yamal",playerArgumentCaptor.getValue().getName());
        assertEquals("Barcelona",playerArgumentCaptor.getValue().getTeam());
        assertEquals("Delantero",playerArgumentCaptor.getValue().getPosition());
    }

    @Test
    void testSaveError() {
        //Given
        Player newPlayer = DataProvider.playerMock();
        //WHEN-THEN
        when(this.playerRepository.save(any(Player.class))).thenThrow(
                PlayerFoundException.class
        );
        assertThrows(PlayerFoundException.class, () -> {
            this.playerService.save(newPlayer);
        });
        verify(this.playerRepository).save(any(Player.class));
    }

    @Test
    void testUpdateError(){
        //Given
        Player player = DataProvider.playerUpdateMock();
        when(this.playerRepository.update(any(Player.class))).thenThrow(PlayerNotFoundException.class);
        assertThrows(PlayerNotFoundException.class,()->{
           this.playerService.update(player);
        });
        verify(this.playerRepository).update(any(Player.class));
    }

    @Test
    void testUpdate(){
        Player player = DataProvider.playerUpdateMock();
        when(this.playerRepository.update(any(Player.class))).thenReturn(Optional.of(player));
        Player result = this.playerService.update(player);
        assertNotNull(result);
        assertEquals("Kylian Mbappé", result.getName());
        assertEquals("Real Madrid", result.getTeam());
        assertEquals("Delantero", result.getPosition());

        //evaluador de argumentos
        ArgumentCaptor<Player> playerArgumentCaptor = ArgumentCaptor.forClass(Player.class);
        verify(this.playerRepository).update(playerArgumentCaptor.capture());
        assertEquals(4L,playerArgumentCaptor.getValue().getId());
        assertEquals("Kylian Mbappé",playerArgumentCaptor.getValue().getName());
        assertEquals("Real Madrid",playerArgumentCaptor.getValue().getTeam());
        assertEquals("Delantero",playerArgumentCaptor.getValue().getPosition());
        verify(this.playerRepository).update(any(Player.class));
    }


    @Test
    void testDelete(){
        Long id = 2L;
        this.playerService.delete(id);
        verify(this.playerRepository).delete(anyLong());
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.playerRepository).delete(longArgumentCaptor.capture());
        assertEquals(2L,longArgumentCaptor.getValue());
    }
}
