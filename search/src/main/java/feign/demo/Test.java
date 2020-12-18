package feign.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Java类在Java Hotspot VM中具有内部表示形式，被称为类元数据。
 * 在Java Hotspot VM的早期版本中，类元数据是在所谓的永久生成中分配的。
 * 在JDK 8中，永久代已删除，并且类元数据已分配在本机内存中。
 * 默认情况下，可用于类元数据的本地内存数量是无限的。
 * 使用选项MaxMetaspaceSize对用于类元数据的本机内存量设置上限。
 * <p>
 * Java Hotspot VM显式管理用于元数据的空间。
 * 从操作系统请求空间，然后将其分成多个块。
 * 类加载器从其块分配元数据空间（块绑定到特定的类加载器）。
 * 当为类加载器卸载类时，其块将被回收以重新使用或返回给OS。
 * 元数据使用mmap分配的空间，而不是malloc分配的空间。
 * <p>
 * 如果打开UseCompressedOops并使用UseCompressedClassesPointers，
 * 则将本机内存的两个逻辑上不同的区域用于类元数据。
 * UseCompressedClassPointers与Java对象引用的UseCompressedOops一样，使用32位偏移量表示64位进程中的类指针。
 * 为这些压缩的类指针分配了一个区域（32位偏移量）。
 * 可以使用CompressedClassSpaceSize设置区域的大小，默认情况下为1 GB。
 * 压缩类指针的空间保留为mmap在初始化时分配的空间，并根据需要提交。
 * MaxMetaspaceSize适用于已提交的压缩类空间和其他类元数据的空间的总和。
 * <p>
 * 当相应的Java类卸载时，类元数据将被释放。
 * Java类是作为垃圾回收的结果而卸载的，并且可以引发垃圾回收以便卸载类和取消分配类元数据。
 * 当用于类元数据的空间达到一定级别（高水位线）时，将引发垃圾回收。
 * 垃圾收集之后，高水位线可能会升高或降低，具体取决于类元数据释放的空间量。
 * 高水位线将被抬起，以免过早引起另一次垃圾收集。
 * 高水位标记最初设置为命令行选项MetaspaceSize的值。
 * 根据选项MaxMetaspaceFreeRatio和MinMetaspaceFreeRatio来升高或降低它。
 * 如果可用于类元数据的承诺空间占类元数据的总承诺空间的百分比大于MaxMetaspaceFreeRatio，则高水位线将降低。
 * 如果小于MinMetaspaceFreeRatio，则高水位线将升高。
 * <p>
 * 为选项MetaspaceSize指定一个较高的值，以避免为类元数据引发早期的垃圾回收。
 * 分配给应用程序的类元数据的数量取决于应用程序，并且不存在用于选择MetaspaceSize的通用准则。
 * MetaspaceSize的默认大小取决于平台，范围从12 MB到大约20 MB。
 */
public class Test {
    private static List<String> a = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            Thread.sleep(10);
            a.add(UUID.randomUUID().toString());
        }
    }
}
