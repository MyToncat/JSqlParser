/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package net.sf.jsqlparser.util.deparser;

import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.statement.select.Limit;

public class LimitDeparser extends AbstractDeParser<Limit> {
    private ExpressionVisitor<StringBuilder> expressionVisitor;

    public LimitDeparser(ExpressionVisitor<StringBuilder> expressionVisitor, StringBuilder buffer) {
        super(buffer);
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public void deParse(Limit limit) {
        buffer.append(" LIMIT ");
        if (limit.isLimitNull()) {
            buffer.append("NULL");
        } else {
            if (limit.isLimitAll()) {
                buffer.append("ALL");
            } else {
                if (null != limit.getOffset()) {
                    limit.getOffset().accept(expressionVisitor, null);
                    buffer.append(", ");
                }

                if (null != limit.getRowCount()) {
                    limit.getRowCount().accept(expressionVisitor, null);
                }
            }
        }

        if (limit.getByExpressions() != null) {
            buffer.append(" BY ");
            limit.getByExpressions().accept(expressionVisitor, null);
        }
    }

    public ExpressionVisitor<StringBuilder> getExpressionVisitor() {
        return expressionVisitor;
    }

    public void setExpressionVisitor(ExpressionVisitor<StringBuilder> expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }
}
