package cn.codeyang.web.controller.course;

import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.course.optaplanner.domain.OptaSolution;
import cn.codeyang.course.service.OptaSolutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolutionManager;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangzy
 */
@Slf4j
@RestController
@RequestMapping("/course/solution")
@RequiredArgsConstructor
public class SolutionController extends BaseController {
    // private final SolverManager<OptaSolution, Long> solverManager;
    // private final SolutionManager<OptaSolution, HardSoftScore> solutionManager;
    // private final OptaSolutionService optaSolutionService;
    //
    // @GetMapping
    // public OptaSolution getSolution() {
    //     SolverStatus solverStatus = getSolverStatus();
    //     OptaSolution lessonPlanSolution = optaSolutionService.findById(1L);
    //     solutionManager.update(lessonPlanSolution);
    //     lessonPlanSolution.setSolverStatus(solverStatus);
    //     return lessonPlanSolution;
    // }
    //
    // @PostMapping("/solve")
    // public void solve() {
    //     solverManager.solveAndListen(1L,
    //             optaSolutionService::findById,
    //             optaSolutionService::save);
    // }
    //
    // @PostMapping("/stopSolving")
    // public void stopSolving() {
    //     solverManager.terminateEarly(1L);
    // }
    //
    //
    // public SolverStatus getSolverStatus() {
    //     return solverManager.getSolverStatus(1L);
    // }
}
