package trungle95.personal.example.PPMT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trungle95.personal.example.PPMT.domain.BackLog;
import trungle95.personal.example.PPMT.domain.ProjectTask;
import trungle95.personal.example.PPMT.repositories.BackLogRepository;
import trungle95.personal.example.PPMT.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

    @Autowired
    private BackLogRepository backLogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        // PTs to be added to a specific project, project != null => Backlog exists
        // set the bl to pt
        BackLog backlog = backLogRepository.findByProjectIdentifier(projectIdentifier);

        projectTask.setBacklog(backlog);

        // we want our project sequence to be like this: IDPRO-1 IDPRO-2 ... 100
        Integer BackLogSequence = backlog.getPTSequence();
        BackLogSequence++;

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

}
