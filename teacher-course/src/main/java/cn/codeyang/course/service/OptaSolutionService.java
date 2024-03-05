package cn.codeyang.course.service;

import cn.codeyang.course.optaplanner.domain.OptaSolution;

/**
 * @author yangzy
 */
public interface OptaSolutionService {
    OptaSolution findById(Long id);

    void save(OptaSolution lessonPlanSolution);
}
