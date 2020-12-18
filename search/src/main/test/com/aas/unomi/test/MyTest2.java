package com.aas.unomi.test;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import org.junit.Test;

public class MyTest2 extends AbstractBenchmark {
    @BenchmarkOptions(benchmarkRounds = 20, warmupRounds = 0)
    @Test
    public void twentyMillis() throws Exception {
        Thread.sleep(200);
    }
//使用任何JUnit4运行程序运行此测试时，它已经是一个基准，并且结果将被打印到控制台。注意在Eclipse中测试的执行时间，例如：
}
//它比我们在sleep方法中设置的睡眠间隔大得多。之所以如此，是因为多次重复执行基准测试，以便更好地估计所测试方法的实际平均执行时间。
// 打印到控制台的消息包含此特定示例的详细信息：
//因此，该测试重复了15次：丢弃了5个初始预热回合（使JVM有机会优化代码），
// 在随后的10个回合中，测量了执行时间并贡献了平均回合时间，恰好是0.02秒或20毫秒。
// 其他信息包括调用垃圾收集器的次数以及在垃圾收集内部花费的时间。
//通过注释调整基准
//您可以使用测试方法上的附加注释调整上面显示的基本基准。
// 例如，您可以使用BenchmarkOptions批注来调整预热和基准测试的次数，如下所示。
//全面基准测试
//设计测试用例，运行粗略的基准测试
//假设我们的任务是比较三种标准Java列表实现的性能：ArrayList，Vector和LinkedList。首先，
//我们需要设计一个模拟最终应用程序代码的测试用例。
// 假设应用程序将一些元素添加到列表中，然后以随机顺序从列表中删除元素。以下测试就是这样做的：