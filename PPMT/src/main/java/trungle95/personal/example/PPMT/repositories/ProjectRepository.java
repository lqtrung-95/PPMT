package trungle95.personal.example.PPMT.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import trungle95.personal.example.PPMT.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

}
