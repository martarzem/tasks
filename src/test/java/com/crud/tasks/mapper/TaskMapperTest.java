package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TaskMapperTest {
    private TaskMapper taskMapper;

    @Before
    public void beforeTest() {
        taskMapper = new TaskMapper();
    }

    @Test
    public void shouldMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "title", "content");
        //When
        Task result = taskMapper.mapToTask(taskDto);
        //Then
        assertEquals(new Long(1), result.getId());
        assertEquals("title", result.getTitle());
        assertEquals("content", result.getContent());
    }

    @Test
    public void shouldMapToTaskDto() {
        //Given
        Task task = new Task(1L, "title", "content");
        //When
        TaskDto result = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals(new Long(1), result.getId());
        assertEquals("title", result.getTitle());
        assertEquals("content", result.getContent());
    }

    @Test
    public void shouldMapToTaskDtoList() {
        //Given
        List<Task> taskList = new ArrayList<>();
        //When
        List<TaskDto> result = taskMapper.mapToTaskDtoList(taskList);
        //Then
        assertEquals(0, result.size());
    }

}
