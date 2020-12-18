package com.aas.unomi.test;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.*;

@AxisRange(min = 0, max = 1)
@BenchmarkMethodChart(filePrefix = "benchmark-lists")
@BenchmarkOptions(callgc = false, benchmarkRounds = 20, warmupRounds = 3)
public class TestList {
    @Rule
    public TestRule benchmarkRule = new BenchmarkRule();

    private static Object singleton = new Object();
    private static int COUNT = 5000;
    private static int[] rnd;

    /**
     * Prepare random numbers for tests.
     */
    @BeforeClass
    public static void prepare() {
        rnd = new int[COUNT];

        final Random random = new Random();
        for (int i = 0; i < COUNT; i++) {
            rnd[i] = Math.abs(random.nextInt());
        }
    }

    @Test
    public void arrayList() throws Exception {
        runTest(new ArrayList<Object>());
    }

    @Test
    public void linkedList() throws Exception {
        runTest(new LinkedList<Object>());
    }

    @Test
    public void vector() throws Exception {
        runTest(new Vector<Object>());
    }

    private void runTest(List<Object> list) {
        assert list.isEmpty();

        // First, add a number of objects to the list.
        for (int i = 0; i < COUNT; i++)
            list.add(singleton);

        // Randomly delete objects from the list.
        for (int i = 0; i < rnd.length; i++)
            list.remove(rnd[i] % list.size());
    }
}
//随机访问列表（Vector和ArrayList）与LinkedList之间的区别可以在循环时间内清楚地看到。
//另一个区别是，由于GC必须对LinkedList中的内部节点结构执行更多的工作，因此GC时间更长（并且GC调用的次数）（尽管这只是一个疯狂的猜测）。
//线程安全的Vector和未同步的ArrayList之间没有可观察到的差异。
// 实际上，Vector内部的锁是无可争辩的，因此JVM最有可能将其完全删除。
// 现在，只需将JVM升级到较新版本（从1.5.0_18到1.6.0_18），结果就会发生很大变化，请比较：
// 随机访问列表的速度几乎是以前的三倍，而链接列表的速度甚至比以前慢，但是没有（全局）GC活动。
// 再次强调一下：这是相同的代码，同一台机器，只是运行中的不同虚拟机。为什么会这样，我们将作为练习留给读者。

//如果您希望更改基准测试环境的默认回合数或其他方面，请使用BenchmarkOptions批注。
//它可以应用于方法或类。基准测试方法按以下顺序继承选项：
//JUnit基准会在运行每种方法之前提示完整的GC，以确保每次调用都具有相似的测试条件。
//在实践中，将不会使用清理后的堆来执行方法，因此禁用内存的GC处理并仅从多个测试运行中取平均值可能是明智的。因此，我们在类中添加了以下声明（所有方法都继承了该声明）：

//在进行代码和实验时，保持每个基准测试的结果很有用，以便以后进行比较。
// 或者，为此，您可能希望设置一个自动构建，该构建在不同的虚拟机上运行基准测试，将结果存储在同一数据库中，然后绘制结果的图形比较。
// 为此，JUnitBenchmarks提供了一个选项，可以将基准测试结果保存在基于文件的关系数据库H2中。


//在Eclipse中，您可以在VM arguments区域中键入这些属性，如下图所示。

//数字会告诉您很多，但是一张图片价值一千位数...等等。仍然在Eclipse中，让我们向测试类添加以下注释：

//此注释使junit-benchmark的基于H2的使用者绘制一张图表，比较测试类中所有方法的结果。
//JUnit运行完成后，将在项目的默认文件夹中创建一个新文件：Benchmark-lists.html和Benchmark-lists.json。
//HTML文件是使用Google图表编写的，并且需要互联网连接。打开后，图表如下所示：


//不同类型的图形化可视化显示了给定类（及其所有测试方法）的基准测试运行的历史记录。
// 让我们使用此图表比较不同的JVM。
// 每次添加自定义运行键时，我们将使用不同的JVM从Eclipse运行相同的JUnit测试，以便我们知道哪个运行对应于哪个JVM。
// 自定义键是一个全局系统属性，与运行数据一起存储在H2数据库中。
// 我们将其设置为每个JVM的名称。我们执行Eclipse JUnit测试四次，每次更改用于测试的JRE并修改jub.customkey属性。


































