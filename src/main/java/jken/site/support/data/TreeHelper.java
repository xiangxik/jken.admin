package jken.site.support.data;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TreeHelper {

    public static <T extends Hierarchical<T>> List<T> toTree(T root, List<T> all) {
        return toTree(all);
    }

    public static <T extends Hierarchical<T>> List<T> toTree(List<T> all) {
        int[] roots = new int[all.size()];
        Arrays.fill(roots, 0);
        for (int i = 0; i < all.size(); i++) {
            List<T> children = new ArrayList<>();
            T ei = all.get(i);
            for (int j = 0; j < all.size(); j++) {
                if (i != j) {
                    T ej = all.get(j);
                    if (Objects.equal(ej.getParent(), ei)) {
                        children.add(ej);
                        roots[j] = 1;
                    }
                }
            }
            if (children.size() > 0) {
                Collections.sort(children, SortNoComparator.COMPARATOR);
            }
            ei.setChildren(children);
        }

        List<T> result = new ArrayList<>();
        for (int i = 0; i < roots.length; i++) {
            if (roots[i] == 0) {
                result.add(all.get(i));
            }
        }
        if (result.size() > 0) {
            Collections.sort(result, SortNoComparator.COMPARATOR);
        }
        return result;
    }


}
