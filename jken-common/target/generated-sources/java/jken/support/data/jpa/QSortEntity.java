/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.399+08:00
 */

package jken.support.data.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSortEntity is a Querydsl query type for SortEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QSortEntity extends EntityPathBase<SortEntity<?, ? extends java.io.Serializable>> {

    private static final long serialVersionUID = 997233429L;

    public static final QSortEntity sortEntity = new QSortEntity("sortEntity");

    public final QDataEntity _super = new QDataEntity(this);

    //inherited
    public final SimplePath<Object> createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    //inherited
    public final SimplePath<java.io.Serializable> id = _super.id;

    //inherited
    public final SimplePath<Object> lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Integer> sortNo = createNumber("sortNo", Integer.class);

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QSortEntity(String variable) {
        super((Class) SortEntity.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QSortEntity(Path<? extends SortEntity> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QSortEntity(PathMetadata metadata) {
        super((Class) SortEntity.class, metadata);
    }

}

