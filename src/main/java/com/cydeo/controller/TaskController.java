package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
@RequestMapping("/api/v1/task")

public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getTasks(){
        List<TaskDTO>taskDTOList =  taskService.listAllTasks();
        return ResponseEntity.ok(new ResponseWrapper("Task list is retrieved", taskDTOList, HttpStatus.OK));
    }


    @GetMapping("{id}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable Long id){
        taskService.findById(id);
        return ResponseEntity.ok(new ResponseWrapper("Task list is retrieved", HttpStatus.OK));
    }


    @PostMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO task){
        taskService.save(task);
        return ResponseEntity.ok(new ResponseWrapper("Task list is created", HttpStatus.OK));
    }



    @DeleteMapping("{id}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> deleteTask(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.ok(new ResponseWrapper("Task deleted", HttpStatus.OK));
    }


    @PutMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO task){
        taskService.update(task);
        return ResponseEntity.ok(new ResponseWrapper("Task is Updated", HttpStatus.OK));
    }



    @GetMapping("/employee/pending-tasks")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeePendingTasks(){
        List<TaskDTO>taskDTOList= taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved", taskDTOList, HttpStatus.OK));
    }


    @PutMapping("/employee/update")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeeUpdateTasks(@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully updated", HttpStatus.OK));

    }

    @GetMapping("/employee/archive")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeeArchiveTasks(){
        List<TaskDTO> taskDTOList = taskService.listAllTasksByStatus(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved", taskDTOList, HttpStatus.OK));

    }

}
