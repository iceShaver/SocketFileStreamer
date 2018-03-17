package krolikowski.pg.pt.lab1_files_list;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public final class DiskDirectory extends DiskElement {
    private final TreeSet<DiskElement> children;

    public DiskDirectory(File file) {
        super(file);
        children = new TreeSet<>();
        Arrays.stream(file.listFiles()) // TODO: Check behaviour under Windows if dir is empty
                .forEach(x -> children.add(x.isDirectory() ? new DiskDirectory(x) : new DiskFile(x)));
    }

    @Override
    public final void print(int level) {
        super.print(level);
        children.stream().forEach(child -> child.print(level + 1));
    }

    @Override
    protected final void print(int level, Comparator<DiskElement> comparator) {
        super.print(level);
        children.stream().sorted(comparator).forEach(child -> child.print(level + 1, comparator));
    }

    @Override
    protected final String getSizeString() {
        return String.format("%-6d E", getSize());
    }

    @Override
    protected final long getSize() {
        return file.listFiles().length;
    }

    public final void print() {
        super.print();
    }
}
