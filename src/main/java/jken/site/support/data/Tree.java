package jken.site.support.data;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public interface Tree<T extends Hierarchical<T>> {

    static void visit(List<? extends Node<?>> nodes, Consumer<Node<?>> consumer) {
        if (nodes != null) {
            nodes.forEach((node) -> {
                consumer.accept(node);
                visit(node.getChildren(), consumer);
            });
        }
    }

    List<Node<T>> getRoots();

    Set<T> getChecked();

    void setChecked(Set<T> checked);

    boolean isCheckable();

    boolean isExpandAll();

    void makeCheckable();

    void makeExpandAll();
}
