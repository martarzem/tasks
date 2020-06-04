package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TrelloMapperTest {
    private TrelloMapper trelloMapper;

    @Before
    public void beforeTest() {
        trelloMapper = new TrelloMapper();
    }

    @Test
    public void shouldMapToBoards() {
        //Given
        List<TrelloListDto> list = new ArrayList<>();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1", "trelloBoardDto", list);
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardDto);
        //When
        List<TrelloBoard> result = trelloMapper.mapToBoards(trelloBoardDtos);
        //Then
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("trelloBoardDto", result.get(0).getName());
        assertEquals(0, result.get(0).getLists().size());
    }

    @Test
    public void shouldMapToBoardsDto() {
        //Given
        List<TrelloList> list = new ArrayList<>();
        TrelloBoard trelloBoard = new TrelloBoard("1", "trelloBoard", list);
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard);
        //When
        List<TrelloBoardDto> result = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("trelloBoard", result.get(0).getName());
        assertEquals(0, result.get(0).getLists().size());
    }

    @Test
    public void shouldMapToList() {
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        //When
        List<TrelloList> result = trelloMapper.mapToList(trelloListDto);
        //Then
        assertEquals(0, result.size());
    }

    @Test
    public void shouldMapToListDto() {
        //Given
        List<TrelloList> trelloList = new ArrayList<>();
        //When
        List<TrelloListDto> result = trelloMapper.mapToListDto(trelloList);
        //Then
        assertEquals(0, result.size());
    }

    @Test
    public void shouldMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("name", "description", "pos", "1");
        //When
        TrelloCardDto result = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals("name", result.getName());
        assertEquals("description", result.getDescription());
        assertEquals("pos", result.getPos());
        assertEquals("1", result.getListId());
    }

    @Test
    public void shouldMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("name", "description", "pos", "1");
        //When
        TrelloCard result = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals("name", result.getName());
        assertEquals("description", result.getDescription());
        assertEquals("pos", result.getPos());
        assertEquals("1", result.getListId());
    }
}
