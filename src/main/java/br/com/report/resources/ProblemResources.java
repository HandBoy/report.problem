package br.com.report.resources;

import br.com.report.errors.ApiError;
import br.com.report.models.Problem;
import br.com.report.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/problem")
public class ProblemResources {

    @Autowired
    private ProblemRepository problemRepository;

    @GetMapping(produces = "application/json")
    public @ResponseBody
    Iterable<Problem> listProblems() {
        Iterable<Problem> problems = problemRepository.findAll();
        return problems;
    }

    @GetMapping(path = "/{problemId}")
    public ResponseEntity<?> getProblem(@PathVariable(value = "problemId") long problemId) {
        Optional<Problem> problem = problemRepository.findById(problemId);
        if (problem.isPresent())
            return new ResponseEntity<>(problem.get(), HttpStatus.OK);

        return new ResponseEntity<>(new ApiError(404, "Problem not found", new Date()), HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public Problem addProblem(@RequestBody @Valid Problem problem) {
        return problemRepository.save(problem);
    }

    @DeleteMapping()
    public Problem delProblem(@RequestBody Problem problem) {
        problemRepository.delete(problem);
        return problem;
    }

    @PutMapping(path = "/{problemId}", produces = "application/json")
    public ResponseEntity<?> updateTask(@PathVariable("problemId") long problemId, @Valid @RequestBody Problem newProblem) {
        Optional<Problem> oldPessoa = problemRepository.findById(problemId);
        if (oldPessoa.isPresent()) {
            Problem pb = oldPessoa.get();
            pb.setName(newProblem.getName());
            pb.setOrigin(newProblem.getOrigin());
            pb.setDate(newProblem.getDate());
            pb.setHour(newProblem.getHour());
            return new ResponseEntity<>(problemRepository.save(pb), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ApiError(404, "Problem not found", new Date()), HttpStatus.NOT_FOUND);

    }
}
