package com.cydeo.controller;


import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProject(){
        List<ProjectDTO> listAllProjects = projectService.listAllProjects();
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved", listAllProjects, HttpStatus.OK));
    }

    @GetMapping("{code}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjectByCode(@PathVariable("code") String code){

        projectService.getByProjectCode(code);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully retrieved", HttpStatus.CREATED));
    }


    @PostMapping
    @RolesAllowed({"Admin", "Manager"})
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody  ProjectDTO projectDTO){
        projectService.save(projectDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseWrapper("Project is created", HttpStatus.CREATED ));
    }


    @PutMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO projectDTO){
        projectService.update(projectDTO);
        return ResponseEntity.ok(new ResponseWrapper("Project is updated", HttpStatus.OK));
    }

    @DeleteMapping("{code}")
    @RolesAllowed({"Admin", "Manager"})
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable String code){
        projectService.delete(code);
        return ResponseEntity.ok(new ResponseWrapper("Project is deleted", HttpStatus.OK));
    }

    @GetMapping("/manager/project-status")
    @RolesAllowed( "Manager")
    public ResponseEntity<ResponseWrapper> getProjectByManager(){
       List<ProjectDTO> projectDTOList = projectService.listAllProjectDetails();
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved", projectDTOList, HttpStatus.OK));

    }

    @PutMapping ("/manager/complete/{projectCode}")
    @RolesAllowed( "Manager")
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode") String projectCode){

        projectService.complete(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("Project is deleted", HttpStatus.OK));


    }

}
