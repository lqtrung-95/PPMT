package trungle95.personal.example.PPMT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trungle95.personal.example.PPMT.domain.BackLog;
import trungle95.personal.example.PPMT.domain.Project;
import trungle95.personal.example.PPMT.domain.ProjectTask;
import trungle95.personal.example.PPMT.exceptions.ProjectNotFoundException;
import trungle95.personal.example.PPMT.repositories.BackLogRepository;
import trungle95.personal.example.PPMT.repositories.ProjectRepository;
import trungle95.personal.example.PPMT.repositories.ProjectTaskRepository;

import java.util.List;

@Service
public class  ProjectTaskService {

    @Autowired
    private BackLogRepository backLogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        try{
            // PTs to be added to a specific project, project != null => Backlog exists
            // set the bl to pt
            BackLog backlog = backLogRepository.findByProjectIdentifier(projectIdentifier);

            projectTask.setBacklog(backlog);

            // we want our project sequence to be like this: IDPRO-1 IDPRO-2 ... 100
            Integer BackLogSequence = backlog.getPTSequence();
            BackLogSequence++;

            backlog.setPTSequence(BackLogSequence);

            // add sequence to pt
            projectTask.setProjectSequence(projectIdentifier+"-"+BackLogSequence);

            projectTask.setProjectIdentifier(projectIdentifier);

            if(projectTask.getPriority()==null){
                projectTask.setPriority(3);
            }

            if(projectTask.getStatus()==null||projectTask.getStatus()==""){
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        }

        catch(Exception e){
            throw new ProjectNotFoundException("Project not Found");
        }
    }

    public Iterable<ProjectTask> findBacklogById(String id){

        Project project = projectRepository.findByProjectIdentifier(id);

        if(project==null){
            throw new ProjectNotFoundException("Project with ID: '" +id+ "' does not exist");
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findByProjectSequence(String backlog_id, String pt_id) {

        //make sure we are searching on an existing backlog
        BackLog backLog = backLogRepository.findByProjectIdentifier(backlog_id);
        if (backLog==null){
            throw new ProjectNotFoundException("Project with ID: '" +backlog_id+ "' does not exist");
        }

        //make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        if(projectTask == null) {
            throw new ProjectNotFoundException("Project Task '" +pt_id+ "' not found");
        }

        //make sure the backlog/project id in the path corresponds to the right project
        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task '" +pt_id+ "' does not exist in project: '" +backlog_id);
        }

        //make sure we are searching on the right backlog

        return projectTask;
    }
}
