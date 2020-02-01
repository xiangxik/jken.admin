/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.396+08:00
 */

package jken.support.data.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCorpableEntity is a Querydsl query type for CorpableEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QCorpableEntity extends EntityPathBase<CorpableEntity<?, ? extends java.io.Serializable>> {

    private static final long serialVersionUID = 205520635L;

    public static final QCorpableEntity corpableEntity = new QCorpableEntity("corpableEntity");

    public final QDataEntity _super = new QDataEntity(this);

    public final StringPath corpCode = createString("corpCode");

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

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QCorpableEntity(String variable) {
        super((Class) CorpableEntity.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QCorpableEntity(Path<? extends CorpableEntity> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QCorpableEntity(PathMetadata metadata) {
        super((Class) CorpableEntity.class, metadata);
    }

}

