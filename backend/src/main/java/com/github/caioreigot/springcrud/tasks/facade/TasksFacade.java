package com.github.caioreigot.springcrud.tasks.facade;

import com.github.caioreigot.springcrud.tasks.dto.TaskDTO;
import com.github.caioreigot.springcrud.tasks.infra.ConnectionFactory;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TasksFacade {

   public static Connection connection = ConnectionFactory.getConnection();

   public TaskDTO create(TaskDTO taskDTO) throws Exception {
      PreparedStatement ps = connection.prepareStatement(
         "INSERT INTO task(title, description) VALUES(?, ?)",
         Statement.RETURN_GENERATED_KEYS
      );
      
      ps.setString(1, taskDTO.getTitle());
      ps.setString(2, taskDTO.getDescription());

      int affectedRows = ps.executeUpdate();
      if (affectedRows == 0) {
         throw new Exception("Creating user failed, no rows affected.");
      }

      ResultSet generatedKeys = ps.getGeneratedKeys();
      generatedKeys.next();
      long id = generatedKeys.getLong(1);

      return new TaskDTO(id, taskDTO.getTitle(), taskDTO.getDescription());
   }

   public TaskDTO update(TaskDTO taskDTO, Long taskId) throws Exception {
      PreparedStatement ps = connection.prepareStatement("UPDATE task SET title = ?, description = ? WHERE id = ?");
      ps.setString(1, taskDTO.getTitle());
      ps.setString(2, taskDTO.getDescription());
      ps.setLong(3, taskId);
      ps.executeUpdate();

      return taskDTO;
   }

   public TaskDTO getById(Long taskId) throws Exception {
      PreparedStatement ps = connection.prepareStatement("SELECT id, title, description FROM task WHERE id = ?");
      ps.setLong(1, taskId);
      
      ResultSet result = ps.executeQuery();
      result.next();

      return new TaskDTO(
         result.getLong("id"),
         result.getString("title"),
         result.getString("description")
      );
   }

   public List<TaskDTO> getAll() throws Exception {
      PreparedStatement ps = connection.prepareStatement("SELECT id, title, description FROM task");
      ResultSet result = ps.executeQuery();

      List<TaskDTO> tasks = new ArrayList<>();

      while (result.next()) {
         TaskDTO task = new TaskDTO(
            result.getLong("id"),
            result.getString("title"),
            result.getString("description")
         );

         tasks.add(task);
      }

      return tasks;
   }

   public Map<String, String> delete(Long taskId) throws Exception {
      HashMap<String, String> response = new HashMap<>();

      PreparedStatement ps = connection.prepareStatement("DELETE FROM task WHERE id = ?");
      ps.setLong(1, taskId);
      
      int affectedRows = ps.executeUpdate();
      if (affectedRows == 0) {
         response.put("message", "Task " + taskId + " not found. Nothing to delete.");
         return response;
      }

      response.put("message", "Task " + taskId + " deleted.");
      return response;
   }
}
