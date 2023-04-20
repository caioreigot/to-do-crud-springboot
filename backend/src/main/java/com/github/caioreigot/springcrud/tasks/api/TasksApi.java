package com.github.caioreigot.springcrud.tasks.api;

import com.github.caioreigot.springcrud.tasks.dto.TaskDTO;
import com.github.caioreigot.springcrud.tasks.facade.TasksFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@RequestMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
public class TasksApi {

   @Autowired
   private TasksFacade tasksFacade;

   @PostMapping
   @ResponseBody
   public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO taskDTO) {
      try {
         TaskDTO taskCreated = tasksFacade.create(taskDTO);
         return new ResponseEntity<>(taskCreated, HttpStatus.CREATED);
      } catch(Exception error) {
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
   }

   @PutMapping("/{taskId}")
   @ResponseBody
   public ResponseEntity<TaskDTO> update(
       @PathVariable("taskId") Long taskId,
       @RequestBody TaskDTO taskDTO
   ) {
      try {
         TaskDTO taskUpdated = tasksFacade.update(taskDTO, taskId);
         return new ResponseEntity<>(taskUpdated, HttpStatus.OK);
      } catch(Exception error) {
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
   }

   @GetMapping("/{taskId}")
   @ResponseBody
   public ResponseEntity<?> getById(
       @PathVariable("taskId") Long taskId
   ) {
      try {
         TaskDTO taskFound = tasksFacade.getById(taskId);
         return new ResponseEntity<>(taskFound, HttpStatus.OK);
      } catch(Exception error) {
         return new ResponseEntity<>("Task not found.", HttpStatus.NOT_FOUND);
      }
   }

   @GetMapping
   @ResponseBody
   public ResponseEntity<List<TaskDTO>> getAll() {
      try {
         List<TaskDTO> tasks = tasksFacade.getAll();
         return new ResponseEntity<>(tasks, HttpStatus.OK);
      } catch(Exception error) {
         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }

   @DeleteMapping("/{taskId}")
   @ResponseBody
   public ResponseEntity<?> delete(@PathVariable("taskId") Long taskId) {
      try {
         Map<String, String> response = tasksFacade.delete(taskId);
         return new ResponseEntity<>(response, HttpStatus.OK);
      } catch(Exception error) {
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
   }
}