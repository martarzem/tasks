package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {
    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository repository;

    @Test
    public void shouldGetAllTasks() {
        List<Task> taskList = new ArrayList<>();
        when(repository.findAll()).thenReturn(taskList);
        List<Task> result = repository.findAll();

        assertEquals(0, result.size());
    }

    @Test
    public void shouldSaveTask() {
        Task task = new Task(1L, "title", "content");
        when(repository.save(task)).thenReturn(task);
        Task result = repository.save(task);

        assertEquals(new Long(1), result.getId());
        assertEquals("title", result.getTitle());
        assertEquals("content", result.getContent());
    }
}
