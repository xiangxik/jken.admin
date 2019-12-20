/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.883+08:00
 *
 */

package jken.site.support.data;

public interface Lockedable {
    boolean isLocked();

    void setLocked(boolean locked);
}
