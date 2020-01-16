package trungle95.personal.example.PPMT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trungle95.personal.example.PPMT.domain.Project;
import trungle95.personal.example.PPMT.repositories.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {

        //logic

        return projectRepository.save(project);
    }
}
