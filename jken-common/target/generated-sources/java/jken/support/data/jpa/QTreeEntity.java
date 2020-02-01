/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.401+08:00
 */

package jken.support.data.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTreeEntity is a Querydsl query type for TreeEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QTreeEntity extends EntityPathBase<TreeEntity<?, ?, ? extends java.io.Serializable>> {

    private static final long serialVersionUID = -1936027371L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTreeEntity treeEntity = new QTreeEntity("treeEntity");

    public final QSortEntity _super = new QSortEntity(this);

    public final ListPath<TreeEntity<?, ?, ? extends java.io.Serializable>, QTreeEntity> children = this.<TreeEntity<?, ?, ? extends java.io.Serializable>, QTreeEntity>createList("children", TreeEntity.class, QTreeEntity.class, PathInits.DIRECT2);

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

    public final QTreeEntity parent;

    //inherited
    public final NumberPath<Integer> sortNo = _super.sortNo;

    public final StringPath treePath = createString("treePath");

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QTreeEntity(String variable) {
        this((Class) TreeEntity.class, forVariable(variable), INITS);
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QTreeEntity(Path<? extends TreeEntity> path) {
        this((Class) path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTreeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QTreeEntity(PathMetadata metadata, PathInits inits) {
        this((Class) TreeEntity.class, metadata, inits);
    }

    public QTreeEntity(Class<? extends TreeEntity<?, ?, ? extends java.io.Serializable>> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QTreeEntity(forProperty("parent"), inits.get("parent")) : null;
    }

}

