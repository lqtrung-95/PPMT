package trungle95.personal.example.PPMT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trungle95.personal.example.PPMT.domain.BackLog;
import trungle95.personal.example.PPMT.domain.Project;
import trungle95.personal.example.PPMT.exceptions.ProjectIdException;
import trungle95.personal.example.PPMT.repositories.BackLogRepository;
import trungle95.personal.example.PPMT.repositories.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BackLogRepository backLogRepository;

    public Project saveOrUpdateProject(Project project) {
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId() == null){
                BackLog backLog = new BackLog();
                project.setBackLog(backLog);
                backLog.setProject(project);
                backLog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId() !=null ) {
                project.setBackLog(backLogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);
        }
        catch(Exception e) {
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase() + "' already exist.");
        }

    }

    public Project findProjectByIdentifier(String projectId) {

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Project ID '"+ projectId + "' does not exist.");
        }
        return project;
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Cannot delete Project with Id '"+projectId + "'. This project does not exist");
        }

        projectRepository.delete(project);
    }
}
