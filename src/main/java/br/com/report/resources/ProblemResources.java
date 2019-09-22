package br.com.report.resources;

import br.com.report.models.Problem;
import br.com.report.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/problem")
public class ProblemResources {

    @Autowired
    private ProblemRepository er;

    @GetMapping(produces="application/json")
    public @ResponseBody
    Iterable<Problem> listProblems(){
        Iterable<Problem> problems = er.findAll();
        return problems;
    }

    @PostMapping()
    public Problem addProblem(@RequestBody @Valid Problem problem){
        return er.save(problem);
    }

    @DeleteMapping()
    public Problem delProblem(@RequestBody Problem problem){
        er.delete(problem);
        return problem;
    }

}
