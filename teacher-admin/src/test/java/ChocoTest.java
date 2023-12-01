import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;
import org.junit.Test;

import java.util.stream.IntStream;

/**
 * @author yangzy
 */
public class ChocoTest {
    @Test
    public void testChoco() {
        Model model = new Model("Class Scheduling");

        // 定义变量
        IntVar x = model.intVar("x", 1, 5);
        IntVar y = model.intVar("y", 1, 5);

        // 添加约束条件
        model.arithm(x, "+", y, "=", 5).post();

        // 创建求解器
        Solver solver = model.getSolver();

        // 求解
        if (solver.solve()) {
            // 输出结果
            System.out.println("x = " + x.getValue() + ", y = " + y.getValue());
        } else {
            System.out.println("No solution found.");
        }

    }

    /**
     * 8皇后问题
     */
    @Test
    public void testScheduling1() {
        int n = 8;
        Model model = new Model(n + "-queens problem");

        IntVar[] vars = new IntVar[n];
        for (int q = 0; q < n; q++) {
            vars[q] = model.intVar("Q_" + q, 1, n);
        }


        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                model.arithm(vars[i], "!=", vars[j]).post();
                model.arithm(vars[i], "!=", vars[j], "-", j - i).post();
                model.arithm(vars[i], "!=", vars[j], "+", j - i).post();
            }
        }
        Solution solution = model.getSolver().findSolution();
        if(solution != null){
            System.out.println(solution.toString());
        }
    }


    /**
     * 8皇后问题
     */
    @Test
    public void testScheduling2() {
        int n = 8;
        Model model = new Model(n + "-queens problem");

        IntVar[] vars = model.intVarArray("Q", n, 1, n, false);


        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                vars[i].ne(vars[j]).extension().post();
                vars[i].ne(vars[j].sub(j - i)).extension().post();
                vars[i].ne(vars[j].add(j - i)).extension().post();
            }
        }
        Solution solution = model.getSolver().findSolution();
        if(solution != null){
            System.out.println(solution.toString());
        }
    }

    /**
     * 8皇后问题
     */
    @Test
    public void testScheduling3() {
        int n = 8;
        Model model = new Model(n + "-queens problem");
        IntVar[] vars = model.intVarArray("Q", n, 1, n, false);
        IntVar[] diag1 = IntStream.range(0, n).mapToObj(i -> vars[i].sub(i).intVar()).toArray(IntVar[]::new);
        IntVar[] diag2 = IntStream.range(0, n).mapToObj(i -> vars[i].add(i).intVar()).toArray(IntVar[]::new);
        model.post(
                model.allDifferent(vars),
                model.allDifferent(diag1),
                model.allDifferent(diag2)
        );
        Solver solver = model.getSolver();
        solver.showStatistics();
        solver.setSearch(Search.domOverWDegSearch(vars));
        Solution solution = solver.findSolution();
        if (solution != null) {
            System.out.println(solution.toString());
        }
    }
}
